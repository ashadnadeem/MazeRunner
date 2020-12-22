/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazerunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class player {
    RandomAccessFile file = new RandomAccessFile("leaderboard.dat","rw");
    String name;
    int played, wins;
    Double successRate;

    public player(String name) throws FileNotFoundException {
      
        this.name = name;
    }

    player()  throws FileNotFoundException {
       
    }

    public player(String name, int played, int wins, Double successRate) throws FileNotFoundException {
        this.name = name;
        this.played = played;
        this.wins = wins;
        this.successRate = successRate;
    }

    
    
    public void updateStatistics(player p) throws IOException {
       file.seek(0);
       boolean found = false;
       int i = 0;
       int n = (int) (file.length()/size());
       while(i < n) {
           read();
           if(this.name.equals(p.name)) {
               found = true;
               edit(p);
               break;
           }
           i++;
       }
       if(!found) {
           writeInEnd(p);
       }
    }
    
    public void writeInEnd(player p) throws IOException {
        file.seek(file.length());
        StringBuffer sb;
        sb = new StringBuffer(p.name);
        sb.setLength(20);
        file.writeChars(sb.toString());
        
        file.writeInt(p.played);
        
        file.writeInt(p.wins);
        
        file.writeDouble(p.successRate);     
    }
    
     public void edit(player p) throws IOException {
        file.seek(file.getFilePointer() - size());
        StringBuffer sb;
        sb = new StringBuffer(p.name);
        sb.setLength(20);
        file.writeChars(sb.toString());
        
        file.writeInt(p.played);
        
        file.writeInt(p.wins);
        
        file.writeDouble(p.successRate);     
    }
    
    public void read() throws IOException {
       
        char[] temp = new char[20];
        for (int i = 0; i < temp.length; i++)
            temp[i] = file.readChar();
        name = new String(temp);
        name = name.trim();
        
        played = file.readInt();
        
        wins = file.readInt();
        
        successRate = file.readDouble();
    }
    
    public void read(int n) throws IOException {
       file.seek(n*size());
        char[] temp = new char[20];
        for (int i = 0; i < temp.length; i++)
            temp[i] = file.readChar();
        name = new String(temp);
        name = name.trim();
        
        played = file.readInt();
        
        wins = file.readInt();
        
        successRate = file.readDouble();
    }
    
    
    public void setPlayer(player p) throws IOException {
        file.seek(0);
       boolean found = false;
       int i = 0;
       int n = (int) (file.length()/size());
       while(i < n) {
           read();
           if(this.name.equals(p.name)) {
               found = true;
               p.played = this.played;
               p.wins = this.wins;
               p.successRate = this.successRate;
               break;
           }
           i++;
       }
       if(!found) {
               p.played = 0;
               p.wins = 0;
               p.successRate = 0.0;
       }
    }
    
    
    public int size() {
        return 40 + 4 + 4 + 8;
    }

    @Override
    public String toString() {
        return "player{" + "name=" + name + ", played=" + played + ", wins=" + wins + ", successRate=" + successRate + '}';
    }
    
    
        
}
