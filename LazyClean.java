// Michael Skirpan
// Concurrent Programming
// Homework 2
// Lazy List with cleanup

import java.util.concurrent.locks.ReentrantLock;
import javax.swing.Timer;


public class LazyList {

	private Node head;

	public LazyList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		new Timer(500, cleanUp).start();
	}

	private class Node {
		Integer item;
		int key;
		Node next;
		private Lock lock;
		boolean marked;

		public Node(Integer myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
			lock = new ReentrantLock()
			marked = false;
		}

		public lock(){
			lock.lock();
		}

		public unlock(){
			lock.unlock();
		}
	}

	public boolean add(Integer item){
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
				curr.lock()
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

	public boolean remove(Integer item){
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
					if validate(pred, curr){					
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

	public boolean contains(Integer item){
		int key = item.hashCode();
		Node curr = head;
		while (curr.key < key){
			curr = curr.next;
		return curr.key == key && !curr.marked;
		}
	}

	private boolean validate(Node pred, Node curr){
		return !pred.marked && !curr.marked && pred.next == curr;
	}

	private void cleanUp(){
		Node pred = head;
		Node curr = pred.next;
		while(curr.key != Integer.MAX_VALUE){
			pred = curr;
			curr = curr.next;
			if (curr.marked == true){
				try{
					pred.lock();
					curr.lock();
					pred.next = curr.next;
				} finally {
					pred.unlock();
					curr.unlock();
				}
			}		
		}
	}

}

