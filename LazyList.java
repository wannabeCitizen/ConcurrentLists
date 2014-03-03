// Michael Skirpan
// Concurrent Programming
// Homework 2
// Lazy List

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;



public class LazyList<T> implements Lists<T>{

	private Node head;

	public LazyList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	private class Node {
		T item;
		int key;
		Node next;
		private Lock lock;
		boolean marked;

		public Node(T myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
			lock = new ReentrantLock();
			marked = false;
		}

		public Node(Integer myInt){
			key = myInt;
			next = null;
			lock = new ReentrantLock();
		}

		public void lock(){
			lock.lock();
		}

		public void unlock(){
			lock.unlock();
		}
	}

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

	public boolean contains(T item){
		int key = item.hashCode();
		Node curr = head;
		while (curr.key < key){
			curr = curr.next;
		}
		return curr.key == key && !curr.marked;
	}

	private boolean validate(Node pred, Node curr){
		return !pred.marked && !curr.marked && pred.next == curr;
	}

}

