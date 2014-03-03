// Michael Skirpan
// Concurrent Programming
// Homework 2
// CoarseGrain List

import java.util.concurrent.locks.ReentrantLock;


public class CoarseGrain {

	private Node head;
	private Lock lock = new ReentrantLock();

	public CoarseGrain() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	private class Node {
		Integer item;
		int key;
		Node next;

		public Node(Integer myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
		}
		
		public lock(){
			lock.lock();
		}

		public unlock(){
			lock.unlock();
		}
	}

	public boolean add(Integer item){
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

	public boolean remove(Integer item){
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

	public boolean contains(Integer item){
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

