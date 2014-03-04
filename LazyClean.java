// Michael Skirpan
// Concurrent Programming
// Homework 2
// Lazy List with cleanup

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.Timer;
import java.util.TimerTask;

// Class for Lazy List with cleanUp method - which can take any type
public class LazyClean<T> implements Lists<T>{

	private Node head;
	// Uses a timer to call cleanUp from time to time.
	private Timer timer = new Timer();
	private TaskClean myClean = new TaskClean();

	//Constructor for List
	public LazyClean() {
		// Initializes head and tail, and starts timer for cleanUp
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		timer.schedule(myClean, 6, 6);

	}

	// Ends the timer after the test finishes
	public void endTime(){
		timer.cancel();
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
					if(validate(pred,curr)) {
						if(curr.key == key){
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

	// Remove that locks only nodes being marked
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

	// More basic validate for checking that you're still in the list,
	// but no need for checking for marks since cleanUp takes care of that.
	private boolean validate(Node pred, Node curr){
		return pred.next == curr;
	}

	
	// Timer Task class
	public class TaskClean extends TimerTask{

		// Task that cleans up all marked nodes, validating that you're still
		// in the list before removing so you don't accidentally skip over an added
		// node.
		@Override
		public void run(){
			Node pred = head;
			Node curr = pred.next;
			while(curr.key != Integer.MAX_VALUE){
				if (curr.marked == true){
					try{
						pred.lock();
						curr.lock();
						if (validate(pred, curr)){
							pred.next = curr.next;
						}
					} finally {
						pred.unlock();
						curr.unlock();
					}
					curr = pred.next;
				} else{
					pred = curr;
					curr = curr.next;
				}		
			}
		}
	}

}

