// Michael Skirpan
// Concurrent Programming
// Homework 2
// CoarseGrain List

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CoarseGrain<T> implements Lists<T>{

	private Node head;
	private Lock lock = new ReentrantLock();

	public CoarseGrain() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	private class Node {
		T item;
		int key;
		Node next;

		public Node(T myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
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

