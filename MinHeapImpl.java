/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 13523
 */

import java.io.IOException;
import java.util.ArrayList;

public class MinHeapImpl<T extends Comparable<T>> {
	private ArrayList<T> arr;

	public MinHeapImpl() {
		arr = new ArrayList<T>();
	}

	public T extractMin() {
		if (arr.size() <= 0)
			return null;
		else {
			T min = arr.get(0);
			arr.set(0, arr.get(arr.size() - 1)); // Move last to position 0
			arr.remove(arr.size() - 1);
			minHeapify(arr, 0);
			return min;
		}
	}
	public void print(){
		
		for(int i =0; i < arr.size();i++){
			System.out.println(arr.get(i));
		}
		if(arr.size()>0)
		System.out.println("Min element is" + arr.get(0));
	}


	public void insert(T element) {
		arr.add(element); 
		int pos= arr.size() - 1; // get its location

		// Swapping
		while (pos > 0 && arr.get(pos).compareTo(arr.get(parent(pos))) < 0) {
			swap(arr, pos, parent(pos));
			pos = parent(pos);
		}
	}

	
	public boolean isEmpty() {
		return arr.isEmpty();
	}

	public T minimum() {
		if (arr.size() <= 0)
			return null;
		else
			return arr.get(0);
	}

	
	private static <T extends Comparable<T>> void minHeapify(ArrayList<T> a,int i) {
		int left = leftChild(i); // index of node i's left child
		int right = rightChild(i); // index of node i's right child
		int small; // will hold the index of the node with the smallest
						

		
		if (left <= a.size() - 1 && a.get(left).compareTo(a.get(i)) < 0)
			small = left; 
		else
			small = i; // no, so node i is the smallest so far

		
		if (right <= a.size() - 1
				&& a.get(right).compareTo(a.get(small)) < 0)
			small = right; // yes, so the right child is the largest

		if (small != i) {
			swap(a, i, small);
			minHeapify(a, small);
		}
	}


	/*
	 * Swap two positions i and j in ArrayList a.
	 * a -->  the arrayList
	 * i --> first positio
	 * j --> second position           
	 */
	private static <T> void swap(ArrayList<T> a, int i, int j) {
		T t = a.get(i);
		a.set(i, a.get(j));
		a.set(j, t);
	}
    
	// * i-->index of the parent node	
	
	private static int parent(int i) {
		return (i - 1) / 2;
	}
	/**
	 * Return the index of the left child of node i.
	 * 
	 * i-->index of the parent node
	 */
	private static int leftChild(int i) {
		return (2 * i) + 1;
	}

	/*
	 * Return the index of the right child of node i.
	 
	 * i-->index of the parent node	 
	 */
	private static int rightChild(int i) {
		return (2 * i) + 2;
	}

	
}

