/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazerunner;

/**
 *
 * @author Ashad
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ashad Nadeem
 */
class Generator extends JFrame implements ActionListener {

        private static int width;
        private static int hieght;
        private static int rows;
        private static int cols;
        private static ImageIcon mouse, cheese, noIMG;
        private static int level = 30;
        private int currx, curry;
        private Node node[][];
        private JButton exitJB, reMazeJB;
        private JButton cell[][];
        private ButtonHandler ebHandler, rmHandler;
        private LinkedList<Node> user;
        private keyboardHandler kbh;

        public Generator(int level) {
                //System Settings
                width = (int) (getDefaultToolkit().getScreenSize().getHeight() - 30);
                hieght = (int) (getDefaultToolkit().getScreenSize().getHeight() - 30);

                //Initaialise Level
                this.rows = level;
                this.cols = level;
                this.level = level;

                //ImageIcon
                switch (level) {
                        case 30:
                                mouse = new ImageIcon(getClass().getResource("/mazerunner/mouse-24.png"));
                                cheese = new ImageIcon(getClass().getResource("/mazerunner/cheese-24.png"));
                                break;
                        case 10:
                                mouse = new ImageIcon(getClass().getResource("/mazerunner/mouse-64.png"));
                                cheese = new ImageIcon(getClass().getResource("/mazerunner/cheese-64.png"));
                                break;
                        default:
                                mouse = new ImageIcon(getClass().getResource("/mazerunner/mouse-16.png"));
                                cheese = new ImageIcon(getClass().getResource("/mazerunner/cheese-16.png"));
                }
                //Users Path
                user = new LinkedList<Node>();

                //Keyboard Handler
                kbh = new keyboardHandler();

                //node initialisor
                this.node = new Node[rows][cols];
                //cell initialisor
                cell = new JButton[rows][cols];
                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                node[i][j] = new Node();
                                //cell[i][j] = new JButton(""+((j+1)));
                                cell[i][j] = new JButton();
                                cell[i][j].setBorder(null);
                                cell[i][j].setPreferredSize(new Dimension(32, 32));
                                cell[i][j].setMaximumSize(new Dimension(32, 32));
                                cell[i][j].setMinimumSize(new Dimension(32, 32));
                                cell[i][j].setOpaque(true);
                                //cell[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                                cell[i][j].addKeyListener(kbh);
                        }
                }
                reset();
                checkMazeValid();

                // EXit button
                exitJB = new JButton("Exit");
                exitJB.setBackground(Color.RED);
                ebHandler = new ButtonHandler();
                exitJB.addActionListener(ebHandler);
                reMazeJB = new JButton("ReMaze");
                reMazeJB.setBackground(Color.CYAN);
                rmHandler = new ButtonHandler();
                reMazeJB.addActionListener(rmHandler);

                JPanel grid = new JPanel(new GridLayout(rows, cols));
                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                grid.add(cell[i][j]);
                        }
                }
                grid.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                grid.setSize(width, hieght);

                JPanel button = new JPanel(new GridLayout(1, 2));
//                button.add(new JLabel());
//                button.add(new JLabel());
                button.add(exitJB);
                button.add(reMazeJB);

                Container pane = getContentPane();
                pane.setLayout(new BorderLayout());
                pane.add(grid, BorderLayout.CENTER);
                pane.add(button, BorderLayout.SOUTH);

                setSize(width, hieght);
                setLocationRelativeTo(null);
                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                if (ae.getSource() == cell[i][j]) {
                                        cell[i][j].setBackground(Color.YELLOW);
                                        node[i][j].isVisited = true;
                                }
                        }
                }
        }

        private class ButtonHandler implements ActionListener {

                public void actionPerformed(ActionEvent press) {
                        if (press.getSource() == exitJB) {
                                System.exit(0);
                        }
                        if (press.getSource() == reMazeJB) {
                                reset();
                                checkMazeValid();
                        }
                }
        }

        private void paint(int i, int j) {
                if (node[i][j].isWall) {
                        cell[i][j].setBackground(Color.BLACK);
                } else {
                        cell[i][j].setBackground(Color.white);
                }
        }
        public void paint(int i, int j, Color col) {
                if (!node[i][j].isWall) {
                        cell[i][j].setBackground(col);
                }
        }

        private void randomise() {
                for (int i = rows - 1; i > 0; i = i - 2) {
                        for (int j = cols - 1; j > 0; j = j - 2) {
                                //2 options goNorth, goWest
                                //Speacial Case Go both ways
                                try {
                                        if (Math.random() > 0.5) {
                                                //Going North
                                                node[i][j].isVisited = true;
                                                node[i - 1][j].isVisited = true;
                                                node[i - 2][j].isVisited = true;
                                                if (Math.random() > 0.9) {
                                                        //going West Aswell For More Randomness
                                                        node[i][j].isVisited = true;
                                                        node[i][j - 1].isVisited = true;
                                                        node[i][j - 2].isVisited = true;
                                                }
                                        } else {
                                                //going West
                                                node[i][j].isVisited = true;
                                                node[i][j - 1].isVisited = true;
                                                node[i][j - 2].isVisited = true;
                                                if (Math.random() > 0.9) {
                                                        //Going North Aswell For More Randomness
                                                        node[i][j].isVisited = true;
                                                        node[i - 1][j].isVisited = true;
                                                        node[i - 2][j].isVisited = true;
                                                }
                                        }
                                } catch (Exception e) {
                                }
                        }
                }
                //mark invisited to walls
                //Now mark all isvisited an unvisited
                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                if (i == 0 && j == 0) {
                                        node[i][j].isVisited = false;
                                        continue;
                                }
                                if (!node[i][j].isVisited) {
                                        node[i][j].isWall = true;
                                        paint(i, j);
                                }
                                node[i][j].isVisited = false;
                        }
                }
        }

        private void reset() {
                this.setCurrx(0);
                this.setCurry(0);
                this.node = new Node[rows][cols];
                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                node[i][j] = new Node();
                                cell[i][j].setBackground(Color.WHITE);
                                paint(i, j);
                        }
                }
                user = new LinkedList<Node>();
                node[0][0].isStart = true;
                cell[0][0].setIcon(mouse);
                cell[rows - 1][cols - 1].setIcon(cheese);
                node[rows - 1][cols - 1].isEnd = true;
                randomise();
                markVisited(0, 0);
        }

        public Node getNode(int i, int j) {
                return this.node[i][j];
        }

        private void markVisited(int pX, int pY) {
                int j = currx;
                int i = curry;
                if (node[i][j].isWall || node[i][j].isVisited) {
                        currx = pX;
                        curry = pY;
                } else {
                        cell[i][j].setBackground(Color.YELLOW);
                        node[i][j].isVisited = true;
                        //System.out.println("[" + pX + "," + pY + "]");
                        cell[pY][pX].setIcon(null);
                        cell[i][j].setIcon(mouse);
                        user.add(node[i][j]);
                }
                if(node[i][j].isEnd){
                        //Win
                        this.setVisible(false);
                        new victoryUI();
                }
                try {
                        if ((node[i - 1][j].isVisited || node[i - 1][j].isWall)
                                && (node[i + 1][j].isVisited || node[i + 1][j].isWall)
                                && (node[i][j - 1].isVisited || node[i][j - 1].isWall)
                                && (node[i][j + 1].isVisited || node[i][j + 1].isWall)) {
                                System.out.println("Collission Detected");
                                JOptionPane.showMessageDialog(null, "GAME OVER!!", "Error", JOptionPane.ERROR_MESSAGE);
                                
                                System.exit(0);
                        }
                } catch (Exception e) {
                }
        }

        private class keyboardHandler implements KeyListener {

                @Override
                public void keyPressed(KeyEvent e) {
                        int prevX = getCurrx();
                        int prevY = getCurry();
                        // If user press key
                        switch (e.getKeyCode()) {
                                // Start placing
                                case KeyEvent.VK_UP:
                                        //System.out.println("UP");
                                        //Go North
                                        curry--;
                                        break;
                                case KeyEvent.VK_DOWN:
                                        //System.out.println("DOWN");
                                        //Go South
                                        curry++;
                                        break;
                                case KeyEvent.VK_LEFT:
                                        //System.out.println("LEFT");
                                        //Go West
                                        currx--;
                                        break;
                                case KeyEvent.VK_RIGHT:
                                        //System.out.println("RIGHT");
                                        //Go North
                                        currx++;
                                        break;
                                case KeyEvent.VK_R:
                                        System.out.println("Randomize");
                                        reset();
                                        checkMazeValid();
                                        break;
                                case KeyEvent.VK_EQUALS:
                                        System.out.println("Increase Level");
                                        if (level > 100) {
                                                JOptionPane.showMessageDialog(null, "Max Level Reached", "Error!", JOptionPane.ERROR_MESSAGE);
                                                break;
                                        }
                                        Generator k = new Generator(level + 20);
                                        setVisible(false);
                                        break;
                                case KeyEvent.VK_MINUS:
                                        System.out.println("Decrease Level");
                                        if (level < 30) {
                                                JOptionPane.showMessageDialog(null, "Min Level Reached", "Error!", JOptionPane.ERROR_MESSAGE);
                                                break;
                                        }
                                        Generator l = new Generator(level - 20);
                                        setVisible(false);
                                        break;
                                // End placing
                        }
                        if (currx < 0 || curry < 0) {
                                currx = prevX;
                                curry = prevY;
                                return;
                        }
                        if (currx > cols - 1 || curry > rows - 1) {
                                currx = prevX;
                                curry = prevY;
                                return;
                        }
                        markVisited(prevX, prevY);
                }

                @Override
                public void keyTyped(KeyEvent ke) {
                }

                @Override
                public void keyReleased(KeyEvent ke) {
                }
        }

        private void checkMazeValid() {
                shortestPathFinder p = new shortestPathFinder();
                System.out.println(p.pathExists(node));
                while (p.pathExists(node) == false) {
                        //System.out.println(p.pathExists(node));
                        reset();
                }
        }

        public int getCurrx() {
                return currx;
        }

        public void setCurrx(int currx) {
                this.currx = currx;
        }

        public int getCurry() {
                return curry;
        }

        public void setCurry(int curry) {
                this.curry = curry;
        }

}
