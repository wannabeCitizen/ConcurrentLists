// Michael Skirpan
// Concurrent Programming
// Homework 2
// Lazy List

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;


// Class for Lazy List, which can take any type

public class LazyList<T> implements Lists<T>{

	private Node head;

	//Constructor for List
	public LazyList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	// Internal Node class for list
	private class Node {
		T item;
		int key;
		Node next;
		private Lock lock;
		boolean marked;

		// Node constructor 
		public Node(T myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
			lock = new ReentrantLock();
			marked = false;
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

	// Add that only locks on nodes being modified
	public boolean add(T item){
		int key = item.hashCode();
		while (true){
			Node pred = head;
			Node curr = head.next;
			while (curr.key < key){
				pred = curr; 
				curr = curr.next;
			}
			pred.lock();
			try{
				curr.lock();
				try{
					if (validate(pred,curr)) {
						if (curr.key == key){
							return false;
						} else {
							Node node = new Node(item);
							node.next = curr;
							pred.next = node;
							return true;
						}
					}
				} finally {
					curr.unlock();
				}
			} finally {
				pred.unlock();
			}

		}
	}

	// Remove that locks only nodes being marked and removed
	public boolean remove(T item){
		int key = item.hashCode();
		while (true) {
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < key){
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			try{
				curr.lock();
				try{
					if (validate(pred, curr)){					
						if (curr.key != key) {
							return false;
						} else {
							curr.marked = true;
							pred.next = curr.next;
							return true;
						}
					}
				} finally{
					curr.unlock();
				}
			} finally {
				pred.unlock();
			}
		}
	}

	//Lock-free contains
	public boolean contains(T item){
		int key = item.hashCode();
		Node curr = head;
		while (curr.key < key){
			curr = curr.next;
		}
		return curr.key == key && !curr.marked;
	}

	// Validates that both you're in the list and that neither of your nodes
	// are marked so you don't accidentally get taken out of the list.
	private boolean validate(Node pred, Node curr){
		return !pred.marked && !curr.marked && pred.next == curr;
	}

}

