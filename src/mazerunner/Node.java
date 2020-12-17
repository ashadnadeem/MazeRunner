/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazerunner;

import java.util.ArrayList;

/**
 *
 * @author Ashad
 */
public class Node {
        boolean isWall, isVisited,isStart,isEnd;
        ArrayList<Node> neighbour;
        public Node(){
                isVisited = false;
                isWall = false;
                this.neighbour = new ArrayList<Node>();
        }        
}
