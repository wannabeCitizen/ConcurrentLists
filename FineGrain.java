// Michael Skirpan
// Concurrent Programming
// Homework 2
// Fine Grain List

import java.util.concurrent.locks.ReentrantLock;


public class FineGrain {

	private Node head;

	public FineGrain() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	private class Node {
		Integer item;
		int key;
		Node next;
		private Lock lock;

		public Node(Integer myItem){
			item = myItem;
			key = myItem.hashCode();
			next = null;
			lock = new ReentrantLock()
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

	public boolean remove(Integer item){
		Node pred = null , curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock();
			try{
				while (curr.key < key){
					pred.unlock()
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

	public boolean contains(Integer item){
		Node pred = null, curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock()
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

