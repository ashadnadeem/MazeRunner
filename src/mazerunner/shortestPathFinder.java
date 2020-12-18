/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell
 * 
 * 
 */

package mazerunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class shortestPathFinder {
        
        public void populateNeighbours (Node[][] array) {
            for(int i = 0; i < array.length; i++) {
                for(int j =0; j < array.length; j++) {
                    
                    if(i == 0 && j == 0) {
                        if(!array[i+1][j].isWall) {
                            array[i][j].neighbour.add(array[i+1][j]);
                        }
                        if(!array[i][j+1].isWall) {
                            array[i][j].neighbour.add(array[i][j+1]);
                        }
                    }
                    
                    else if (i  == array.length - 1 && j == array[0].length - 1) {
                         if(!array[i-1][j].isWall) {
                            array[i][j].neighbour.add(array[i-1][j]);
                        }
                        if(!array[i][j-1].isWall) {
                            array[i][j].neighbour.add(array[i][j-1]);
                        }
                    }
                    
                    else if (i  == 0 && j == array[0].length - 1) {
                        if(!array[i+1][j].isWall) {
                            array[i][j].neighbour.add(array[i+1][j]);
                        }
                        if(!array[i][j-1].isWall) {
                            array[i][j].neighbour.add(array[i][j-1]);
                        }
                    }
                    
                    else if (i  == array.length - 1 && j == 0) {
                        if(!array[i-1][j].isWall) {
                            array[i][j].neighbour.add(array[i-1][j]);
                        }
                        if(!array[i][j+1].isWall) {
                            array[i][j].neighbour.add(array[i][j+1]);
                        }
                    }
                    
                    else if (i  == 0) {
                         if(!array[i+1][j].isWall) {
                            array[i][j].neighbour.add(array[i+1][j]);
                        }
                        if(!array[i][j+1].isWall) {
                            array[i][j].neighbour.add(array[i][j+1]);
                        }
                         if(!array[i][j-1].isWall) {
                            array[i][j].neighbour.add(array[i][j-1]);
                        }
                    }
                    
                    else if (i  == array.length-1) {
                         if(!array[i-1][j].isWall) {
                            array[i][j].neighbour.add(array[i-1][j]);
                        }
                        if(!array[i][j+1].isWall) {
                            array[i][j].neighbour.add(array[i][j+1]);
                        }
                         if(!array[i][j-1].isWall) {
                            array[i][j].neighbour.add(array[i][j-1]);
                        }
                    }
                    
                    else if (j  == 0) {
                         if(!array[i-1][j].isWall) {
                            array[i][j].neighbour.add(array[i-1][j]);
                        }
                        if(!array[i+1][j].isWall) {
                            array[i][j].neighbour.add(array[i+1][j]);
                        }
                         if(!array[i][j+1].isWall) {
                            array[i][j].neighbour.add(array[i][j+1]);
                        }
                    }
                    
                    else if (j  == array[0].length - 1) {
                         if(!array[i-1][j].isWall) {
                            array[i][j].neighbour.add(array[i-1][j]);
                        }
                        if(!array[i+1][j].isWall) {
                            array[i][j].neighbour.add(array[i+1][j]);
                        }
                         if(!array[i][j-1].isWall) {
                            array[i][j].neighbour.add(array[i][j-1]);
                        }
                    }
                    
                    else {
                         if(!array[i-1][j].isWall) {
                            array[i][j].neighbour.add(array[i-1][j]);
                        }
                        if(!array[i+1][j].isWall) {
                            array[i][j].neighbour.add(array[i+1][j]);
                        }
                         if(!array[i][j+1].isWall) {
                            array[i][j].neighbour.add(array[i][j+1]);
                        }
                          if(!array[i][j-1].isWall) {
                            array[i][j].neighbour.add(array[i][j-1]);
                        }
                    }
                }
            }
        }

        
        public boolean pathExists(Node[][] array) {
            
            populateNeighbours(array);
            Node n = array[0][0];
            
            
            Stack<Node> stack=new  Stack<Node>();
            stack.add(n);
            while (stack.isEmpty() == false) {
                    
	Node top = stack.pop();
                    if(top.isEnd) {
                            isVisitReset(array);
                        return true;
                    }
	if(top.isVisited == false) {
                        //System.out.print(element.data + " ");
                        top.isVisited=true;
	}
	ArrayList<Node> neighbours = top.neighbour;
	for (int i = 0; i < neighbours.size(); i++) {
                              Node next = neighbours.get(i);
                              if(next.isVisited == false) {
                                    stack.add(next);
                              }
	}
            }
            isVisitReset(array);
            return false;
        }
        private void isVisitReset(Node[][] array){
                for (int i = 0; i < array.length; i++) 
                        for (int j = 0; j < array[0].length; j++)
                                array[i][j].isVisited = false;
        }
        
        int[][] array;
        public void createMazeMatrix(Node[][] nodeArr) {
            array = new int[nodeArr.length][nodeArr.length];
            for(int i = 0; i < array.length; i++) {
                for(int j = 0; j < array.length; j++) {
                    if(nodeArr[i][j].isWall) {
                        array[i][j] = 0;
                    }
                    else {
                        array[i][j] = 1;
                    }
                }
            }
        }
        
        
        
        
        
        
static class queueNode {
    int x,y; // The cordinates of a cell
    int dist; // cell's distance of from the source
 
    public queueNode(int x, int y, int dist)
    {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }
}
 

 

 
// function to find the shortest path between
// a given source cell to a destination cell.
public int[][] BFS(Node[][] nodeArr) {

    
    createMazeMatrix(nodeArr);
    int row = array.length; 
    int col = array.length;
 
    boolean[][] visited = new boolean[row][col];
     
 
    visited[0][0] = true;
 

    Queue<queueNode> q = new LinkedList<>();
    int[][] distances = new int[row][col];
  
    queueNode start = new queueNode(0, 0, 0);
    for(int i = 0; i < row; i++) {
        for(int j = 0; j < col; j++) {
            distances[i][j] = 0;
        }
    }
   
    q.add(start); 
  
 

    while (!q.isEmpty())
    {
        queueNode curr = q.peek();
        int curr_x = curr.x;
        int curr_y = curr.y;
       

        if (curr_x == row - 1 && curr_y == col - 1) {
            ArrayList<Integer> list = new ArrayList<>();
            //list.add(curr_x);
            //list.add(curr_y);
            int dist = curr.dist - 1;
            
            /*for(int i = 0; i < curr.dist; i++) {
                 if( curr_x + 1 >= 0 && curr_x + 1 < row && curr_y >= 0 && curr_y < col) {
                     if(queueNodeArr[curr_x + 1][curr_y].dist == dist) {
                          list.add(curr_x+1);
                          list.add(curr_y);
                          curr_x++;
                     }
                 }
                 else if( curr_x - 1 >= 0 && curr_x - 1 < row && curr_y >= 0 && curr_y < col) {
                      if(queueNodeArr[curr_x - 1][curr_y].dist == dist) {
                          list.add(curr_x-1);
                          list.add(curr_y);
                          curr_x--;
                     }
                 }
                 else if( curr_x  >= 0 && curr_x  < row && curr_y  + 1 >= 0 && curr_y + 1 < col) {
                      if(queueNodeArr[curr_x ][curr_y + 1].dist == dist) {
                          list.add(curr_x);
                          list.add(curr_y+1);
                          curr_y++;
                     }
                 }
                 else if( curr_x  >= 0 && curr_x  < row && curr_y - 1 >= 0 && curr_y - 1 < col) {
                      if(queueNodeArr[curr_x ][curr_y - 1].dist == dist) {
                          list.add(curr_x);
                          list.add(curr_y-1);
                          curr_y--;
                     }
                 }
                 dist--;
            }*/
          
           
            
            //int r = row ;
            //int c = col ;
            //list.add(c);
            //list.add(r);
            
            for(int i = 0; i < row; i++) {
                for(int j = 0; j < row; j++) {
                    System.out.print(distances[i][j] + " ");
                }
                System.out.println();
            }
            
            
          /*  for(int i = curr.dist; i >= 0; i--) {
                System.out.println(i);
                    if(r - 1 >= 0 && r - 1 < row && c >=0 && c < col) {
                        if(queueNodeArr[r-1][c].dist == i) {
                            r--;
                            list.add(c);
                            list.add(r);
                        }
                    }
                    else if(r >= 0 && r < row && c - 1 >=0 && c - 1 < col) {
                        if(queueNodeArr[r][c-1].dist == i) {
                            c--;
                            list.add(c);
                            list.add(r);
                        }
                    }
                    else if(r + 1 >= 0 && r + 1 < row && c >=0 && c < col) {
                        if(queueNodeArr[r+1][c].dist == i) {
                            r++;
                            list.add(c);
                            list.add(r);
                        }
                    }
                    else if(r >= 0 && r < row && c + 1 >=0 && c + 1 < col) {
                        if(queueNodeArr[r][c+1].dist == i) {
                            c++;
                            list.add(c);
                            list.add(r);
                        }
                    }*/
                        

            //}
             //System.out.println(list);
            
            return distances;
        }
 
     
        q.remove();
 
        

            
            if( curr_x + 1 >= 0 && curr_x + 1 < row && curr_y >= 0 && curr_y < col) {
                 if(array[curr_x + 1][curr_y] == 1 && !visited[curr_x + 1][curr_y]) {
                     visited[curr_x + 1][curr_y] = true;
                     queueNode next = new queueNode(curr_x + 1, curr_y, curr.dist + 1);
                     q.add(next);
                     distances[curr_x + 1][curr_y] = curr.dist+1;
                 }
            }
            if( curr_x - 1 >= 0 && curr_x - 1 < row && curr_y >= 0 && curr_y < col) {
                 if(array[curr_x - 1][curr_y] == 1 && !visited[curr_x - 1][curr_y]) {
                     visited[curr_x - 1][curr_y] = true;
                     queueNode next = new queueNode(curr_x - 1, curr_y, curr.dist + 1);
                     q.add(next);
                     distances[curr_x - 1][curr_y] = curr.dist+1;
                 }
            }
            if( curr_x  >= 0 && curr_x  < row && curr_y + 1 >= 0 && curr_y + 1 < col) {
                 if(array[curr_x ][curr_y + 1] == 1 && !visited[curr_x ][curr_y + 1]) {
                     visited[curr_x ][curr_y + 1] = true;
                     queueNode next = new queueNode(curr_x, curr_y + 1, curr.dist + 1);
                     q.add(next);
                     distances[curr_x][curr_y + 1] = curr.dist+1;
                 }
            }
            if( curr_x  >= 0 && curr_x  < row && curr_y - 1 >= 0 && curr_y - 1 < col) {
                 if(array[curr_x][curr_y - 1] == 1 && !visited[curr_x ][curr_y - 1]) {
                     visited[curr_x ][curr_y - 1] = true;
                     queueNode next = new queueNode(curr_x , curr_y - 1, curr.dist + 1);
                     q.add(next);
                     distances[curr_x ][curr_y - 1] = curr.dist+1;
                 }
            }
           
            
             

            }

    return null;
}
}
 
        

        
