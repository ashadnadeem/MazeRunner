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
        
}
        
