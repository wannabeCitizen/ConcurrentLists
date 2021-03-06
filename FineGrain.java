// Michael Skirpan
// Concurrent Programming
// Homework 2
// Fine Grain List

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;


// Class for Fine Grain Lock - which can take any type
public class FineGrain<T> implements Lists<T>{

	private Node head;

	//Constructor for List
	public FineGrain(){
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	// Internal Node class for list
	private class Node{
		T item;
		int key;
		Node next;
		private Lock lock;

		// Node constructor 
		public Node(T myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
			lock = new ReentrantLock();
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

	// Add that locks the list hand-over-hand before up to add point
	public boolean add(T item){
		int key = item.hashCode();
		head.lock();
		Node pred = head;
		try {
			Node curr = pred.next;
			curr.lock();
			try{
				while (curr.key < key) {
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
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
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	// Remove that locks the list hand-over-hand before up to removal point
	public boolean remove(T item){
		Node pred = null , curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock();
			try{
				while (curr.key < key){
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
			
				if (key == curr.key) {
					pred.next = curr.next;
					return true;
				} else {
					return false;
				}
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	// Contains that locks the list hand-over-hand until node is found (or not)
	public boolean contains(T item){
		Node pred = null, curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock();
			try {
				while (curr.key <= key){
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
				if (key == curr.key) {
					return true;
				} else {
					return false;
				}
			} finally {
				pred.unlock();
			}
		} finally {
			curr.unlock();
		}
	}

}

