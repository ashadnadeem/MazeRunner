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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ashad Nadeem
 */
public class Generator extends JFrame implements ActionListener {

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
        int[][] distances;
        final double[] speeds = {0.5, 0.2, 0.1, 0.05, 0.04};
        Timer timer;
        private Stack<Integer> complist;
        private double compSpeed;
        private player p;
        private player player;
        private stopwatch s;
        boolean started = false;
        private double bestTime;
        private int index;
        private JLabel loading;

        Generator(int level) throws IOException {
                //Stop Watch Settings
                s = new stopwatch();
                
                
                //System Settings
                width = (int) (getDefaultToolkit().getScreenSize().getHeight() - 30);
                hieght = (int) (getDefaultToolkit().getScreenSize().getHeight() - 30);
                
                //Loading Gif
                loading = new JLabel();
                int x1;
                int y1;
                loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mazerunner/LoadingGear.gif"))); // Gear
                loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mazerunner/LoadingGear2.gif"))); x1 = 350; y1 = 172; // Gear
                //loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mazerunner/LoadingBlock.gif"))); x1 = 400; y1 = 133;  // Block 
                //loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mazerunner/LoadingSlime.gif"))); x1 = 256; y1 = 256; // Slime
                //loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mazerunner/LoadingBounce.gif"))); x1 = 292; y1 = 247; // Bounce
                loading.setSize(30,30);
                int x0 = (width/2) - x1 + 100;
                int y0 = (hieght/2)-y1;
                loading.setBounds(x0, y0,x1,y1);
                getContentPane().add(loading);
                loading.setEnabled(false);
                loading.setVisible(false);

                //Initaialise Level
                this.rows = level;
                this.cols = level;
                this.level = level;

                //Initialise CompSpeed
                switch (level) {
                        case 10:
                                compSpeed = speeds[0];
                                break;
                        case 30:
                                compSpeed = speeds[1];
                                break;
                        case 50:
                                compSpeed = speeds[2];
                                break;
                        case 90:
                                compSpeed = speeds[3];
                                break;
                        case 110:
                                compSpeed = speeds[4];
                                break;
                }
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
                button.add(exitJB);
                button.add(reMazeJB);

                Container pane = getContentPane();
                pane.setLayout(new BorderLayout());
                pane.add(grid, BorderLayout.CENTER);
                pane.add(button, BorderLayout.SOUTH);
                
                setSize(width-100, hieght-100);
                this.setLocation((int) (getDefaultToolkit().getScreenSize().width /2 - (width -100)/2 ), 100);
                s.setVisible(true);
                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        Generator(player p, int level) throws IOException {
                this(level);
                this.p = p;
                System.out.println("Player:"+p.name +", lvl:" + level);
        }

        Generator(player p, int level, double bestTime) throws IOException {
                this(p, level);
                this.bestTime = bestTime;
                System.out.println("Player:"+p.name +", BT:" + bestTime);
        }

        Generator(player p, int level, double bestTime, int index) throws IOException{
                this(p, level, bestTime);
                this.index = index;
                System.out.println("Player:"+p.name +", BT:" + bestTime+", Index: "+ index);
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
                                try {
                                        reset();
                                } catch (IOException ex) {
                                        Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                        checkMazeValid();
                                } catch (IOException ex) {
                                        Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                                }
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

        private void reset() throws IOException {
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

        private void markVisited(int pX, int pY) throws IOException {
                int j = currx;
                int i = curry;
                if (!(currx == 0 && curry == 0)) {
                        reMazeJB.setEnabled(false);
                        //stopwatch s = new stopwatch();
                        //System.out.println("hi");
                        if (!started) {
                                s.start();
                                started = true;
                        }
                }
                if(node[i][j].isVisited){
                        currx = pX;
                        curry = pY;
                        JOptionPane.showMessageDialog(null, "No Turning Back", "Rule-2 Violation", JOptionPane.ERROR_MESSAGE);
                        return;
                }
                if (node[i][j].isWall) {
                        currx = pX;
                        curry = pY;
                        return;
                } else {
                        cell[i][j].setBackground(Color.YELLOW);
                        node[i][j].isVisited = true;
                        //System.out.println("[" + pX + "," + pY + "]");
                        cell[pY][pX].setIcon(null);
                        cell[i][j].setIcon(mouse);
                        user.add(node[i][j]);
                }
                if (node[i][j].isEnd) {
                        //Win
                        shortestPathFinder p = new shortestPathFinder();
                        System.out.println("User:" + user.size());
                        if (user.size()-1 == distances[rows - 1][cols - 1]) {
                                GameOver(true);
                        } else {
                                GameOver(false);
                        }
                }
                //Ignore start and End Nodes
                if (node[i][j].isStart || node[i][j].isEnd) {
                        return;
                }
                //All Three Side Walls
                try {
                        if ((node[i - 1][j].isVisited || node[i - 1][j].isWall)
                                && (node[i + 1][j].isVisited || node[i + 1][j].isWall)
                                && (node[i][j - 1].isVisited || node[i][j - 1].isWall)
                                && (node[i][j + 1].isVisited || node[i][j + 1].isWall)) {
                                GameOver(false);
                        }
                } catch (Exception e) {
                }
                if (i == 0) {
                        if ((node[i][j - 1].isVisited || node[i][j - 1].isWall) && (node[i][j + 1].isVisited || node[i][j + 1].isWall) && (node[i + 1][j].isVisited || node[i + 1][j].isWall)) {
                                GameOver(false);
                        }
                }
                if (i == rows - 1) {
                        if ((node[i][j - 1].isVisited || node[i][j - 1].isWall) && (node[i][j + 1].isVisited || node[i][j + 1].isWall) && (node[i - 1][j].isVisited || node[i - 1][j].isWall)) {
                                GameOver(false);
                        }
                }
                if (j == 0) {
                        if ((node[i - 1][j].isVisited || node[i - 1][j].isWall) && (node[i + 1][j].isVisited || node[i + 1][j].isWall) && (node[i][j + 1].isVisited || node[i][j + 1].isWall)) {
                                GameOver(false);
                        }
                }
                if (j == cols - 1) {
                        if ((node[i - 1][j].isVisited || node[i - 1][j].isWall) && (node[i + 1][j].isVisited || node[i + 1][j].isWall) && (node[i][j - 1].isVisited || node[i][j - 1].isWall)) {
                                GameOver(false);
                        }
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
                                        if (currx == 0 && curry == 0) {
                                                System.out.println("Randomize");
                                                try {
                                                        reset();
                                                } catch (IOException ex) {
                                                        Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                try {
                                                        checkMazeValid();
                                                } catch (IOException ex) {
                                                        Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                        } else {
                                                JOptionPane.showMessageDialog(null, "Once Started Can't ReMaze", "Rule Violation", JOptionPane.ERROR_MESSAGE);
                                                return;
                                        }
                                        break;
                                case KeyEvent.VK_EQUALS:
                                        System.out.println("Increase Level");
                                        if (level > 100) {
                                                JOptionPane.showMessageDialog(null, "Max Level Reached", "Error!", JOptionPane.ERROR_MESSAGE);
                                                break;
                                        }
                                         {
                                                try {
                                                        Generator k = new Generator(level + 20);
                                                } catch (IOException ex) {
                                                        Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                        }
                                        setVisible(false);
                                        break;
                                case KeyEvent.VK_MINUS:
                                        System.out.println("Decrease Level");
                                        if (level < 30) {
                                                JOptionPane.showMessageDialog(null, "Min Level Reached", "Error!", JOptionPane.ERROR_MESSAGE);
                                                break;
                                        }
                                         {
                                                try {
                                                        Generator l = new Generator(level - 20);
                                                } catch (IOException ex) {
                                                        Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                        }
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
                        try {
                                markVisited(prevX, prevY);
                        } catch (IOException ex) {
                                Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }

                @Override
                public void keyTyped(KeyEvent ke) {
                }

                @Override
                public void keyReleased(KeyEvent ke) {
                }
        }

        private void checkMazeValid() throws IOException {
                shortestPathFinder p = new shortestPathFinder();
                //System.out.println(p.pathExists(node));
                while (p.pathExists(node) == false) {
                        //System.out.println(p.pathExists(node));
                        reset();
                }
                distances = p.BFS(node);
                System.out.println(distances[rows - 1][cols - 1]);
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

        public void computerPath() {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(cols - 1);
                list.add(rows - 1);
                int currx = rows - 1;
                int curry = cols - 1;
                for (int i = 0; i < distances[rows - 1][cols - 1]; i++) {
                        if (currx - 1 >= 0 && currx - 1 < rows && curry >= 0 && curry < cols) {
                                if (distances[currx][curry] - 1 == distances[currx - 1][curry]) {
                                        currx = currx - 1;
                                        list.add(curry);
                                        list.add(currx);
                                        continue;
                                }
                        }
                        if (currx >= 0 && currx < rows && curry - 1 >= 0 && curry - 1 < cols) {
                                if (distances[currx][curry] - 1 == distances[currx][curry - 1]) {
                                        curry = curry - 1;
                                        list.add(curry);
                                        list.add(currx);
                                        continue;
                                }
                        }
                        if (currx + 1 >= 0 && currx + 1 < rows && curry >= 0 && curry < cols) {
                                if (distances[currx][curry] - 1 == distances[currx + 1][curry]) {
                                        currx = currx + 1;
                                        list.add(curry);
                                        list.add(currx);
                                        continue;
                                }
                        }
                        if (currx >= 0 && currx < rows && curry + 1 >= 0 && curry + 1 < cols) {
                                if (distances[currx][curry] - 1 == distances[currx][curry + 1]) {
                                        curry = curry + 1;
                                        list.add(curry);
                                        list.add(currx);
                                        continue;
                                }
                        }
                }

                System.out.println(list);
                complist = new Stack<Integer>();
                for (int i = 0; i < list.size(); i++) {
                        complist.add(list.get(i));
                }

                highlightComputerPath();
        }

        public void highlightComputerPath() {
                try {
                        delay(compSpeed);
                        //paint(I-th Componect, J-th Component, Color.GREEN);
                        paint(complist.pop(), complist.pop(), Color.GREEN);
                } catch (EmptyStackException s) {
                        return;
                }
        }

        public void delay(double d) {
                // delay of one second here
                timer = new Timer(0, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                                //What action Should it Perform afterDelay Time?
                                highlightComputerPath();
                        }
                });
                timer.setInitialDelay((int) (d * 1000)); //wait one second * d
                timer.setRepeats(false); //only once
                timer.start();
        }

        private void GameOver(boolean win) throws IOException {
                System.out.println("WIn?" + win);
                s.stop();
                JOptionPane.showMessageDialog(null, "Now Let's See What Path Computer Chooses!", "A.I.", JOptionPane.INFORMATION_MESSAGE);
                loading.setEnabled(true);
                loading.setVisible(true);
                computerPath();
                player = new player();
                if (win) {
                        p.played++;
                        p.wins++;
                        p.successRate = (100.0 * p.wins) / p.played;
                        player.updateStatistics(p);
                        victoryUI v = new victoryUI(this, compSpeed * distances[rows - 1][cols - 1] + 3, s, bestTime, index, p);
                } else {
                        p.played++;
                        p.successRate = (100.0 * p.wins) / p.played;
                        player.updateStatistics(p);
                        if (node[currx][curry].isEnd) {
                                // JOptionPane.showMessageDialog(null, "lol jeet ker bhi haar gaye", "Error", JOptionPane.ERROR_MESSAGE);
                                new hardLuckUI(this, compSpeed * distances[rows - 1][cols - 1] + 2, s);
                        } else {
                                //JOptionPane.showMessageDialog(null, "GAME OVER!!", "Error", JOptionPane.ERROR_MESSAGE);
                                new gameoverUI(this, compSpeed * distances[rows - 1][cols - 1] + 2, s);

                        }
                }
                return;
        }
}
