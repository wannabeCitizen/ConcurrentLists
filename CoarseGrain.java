// Michael Skirpan
// Concurrent Programming
// Homework 2
// CoarseGrain List

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Class for Coarse Grain Lock - which can take any type
public class CoarseGrain<T> implements Lists<T>{

	private Node head;
	private Lock lock = new ReentrantLock();

	//Constructor for List
	public CoarseGrain() {
		// Initializes list head and tail
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	// Internal Node class for list
	private class Node {
		T item;
		int key;
		Node next;

		// Node constructor 
		public Node(T myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
		}

		// Second constructor needed for initialization when Integers 
		// are fed explicitly 
		public Node(Integer myInt){
			key = myInt;
			next = null;
			lock = new ReentrantLock();
		}

		// Simplified locking method
		public void lock(){
			lock.lock();
		}

		// Simplified unlocking method
		public void unlock(){
			lock.unlock();
		}
	}

	// Add that locks the whole list down before adding
	public boolean add(T item){
		Node pred, curr;
		int key = item.hashCode();
		lock.lock();
		try {
			pred = head;
			curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			if (key == curr.key){
				return false;
			} else {
				Node node = new Node(item);
				node.next = curr;
				pred.next = node;
				return true;
			}
		} finally {
			lock.unlock();
		}
	}

	// Remove that locks the whole list down before removing
	public boolean remove(T item){
		Node pred, curr;
		int key = item.hashCode();
		lock.lock();
		try {
			pred = head;
			curr = pred.next;
			while (curr.key < key){
				pred = curr;
				curr = curr.next;
			}
			if (key == curr.key) {
				pred.next = curr.next;
				return true;
			} else {
				return false;
			}
		} finally {
			lock.unlock();
		}
	}

	// Contains that locks the whole list down before check
	public boolean contains(T item){
		Node pred, curr;
		int key = item.hashCode();
		lock.lock();
		try {
			pred = head;
			curr = pred.next;
			while (curr.key <= key){
				pred = curr;
				curr = curr.next;
			}
			if (key == curr.key) {
				return true;
			} else {
				return false;
			}
		} finally {
			lock.unlock();
		}
	}

}

