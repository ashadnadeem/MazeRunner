/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazerunner;

import java.io.FileNotFoundException;

/**
 *
 * @author Dell
 */
public class priorityQueue {
        
    player[] list;
    int maxQueueSize, queueFront, queueRear, count;
    
    public priorityQueue() throws FileNotFoundException{
        maxQueueSize = 10;
        queueFront = 0;
        queueRear = 0;
        count = 0;
        list = new player[maxQueueSize];
        for(int i = queueFront; i < maxQueueSize - 1; i = (i+1) % maxQueueSize)
            list[i] = new player();
        list[maxQueueSize - 1] = new player();
        //for(int i = 0; i < maxQueueSize; i++)
            //System.out.println(list[i]);
    }
    
    public priorityQueue(int size) throws FileNotFoundException{
        maxQueueSize = size;
        queueFront = 0;
        queueRear = 0;
        count = 0;
        list = new player[maxQueueSize];
        for(int i = queueFront; i < maxQueueSize - 1; i = (i+1) % maxQueueSize)
            list[i] = new player();
        list[maxQueueSize] = new player();
    }
    
    public boolean isFull(){
        return (count == maxQueueSize);
    }
    
    public boolean isEmpty(){
        return (count == 0);
    }
    
    public void enqueue(player p){
        
        if(isFull())
            System.out.println();
        else{ 
            list[queueRear] = p;
            queueRear = (queueRear + 1) % maxQueueSize;
            count++;
            
            //Sorting the Queue according to priority using Merge Sort Code
            sort(list, 0, queueRear - 1);
            
            
        }
            
    }
    
    public void sort(player arr[], int l, int r){
	
	if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
	}
    }
    
    
    public void merge(player arr[], int l, int m, int r){
	
	// Find sizes of two subarrays to be merged
	int n1 = m - l + 1;
	int n2 = r - m;

	/* Create temp arrays */
	player L[] = new player[n1];
	player R[] = new player[n2];

	/*Copy data to temp arrays*/
	for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

	/* Merge the temp arrays */

	// Initial indexes of first and second subarrays
	int i = 0, j = 0;

	// Initial index of merged subarry array
	int k = l;
	while (i < n1 && j < n2) {
            if (L[i].successRate <= R[j].successRate) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
	}

	/* Copy remaining elements of L[] if any */
	while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
	}

	/* Copy remaining elements of R[] if any */
	while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
	}
        
    }
    
    public player dequeue() throws FileNotFoundException{
        
        player DeletedValue = new player();
        
        if(isEmpty()){
            System.out.println("The Queue is empty");
            return DeletedValue;
        }
            
        else{
            DeletedValue =  list[queueFront];
            list[queueFront] = new player();
            queueFront = (queueFront + 1) % maxQueueSize;
            count--;
            //System.out.println("The element deleted is: " + DeletedValue);
            return DeletedValue;
        }
    }
    
    public player peek_front() throws FileNotFoundException{
        
        if(isEmpty()){
            System.out.println("The Queue is empty");
            return new player();
        }
            
        else{
            System.out.println("The element at the front is: " + list[queueFront]);
            return list[queueFront];
        }
           
            
        
    }
    
    public void Display(){
        for(int i = queueFront; i < queueRear; i++)
            System.out.println(list[i].name + " " + list[i].played + " " + list[i].wins);
    }
}
