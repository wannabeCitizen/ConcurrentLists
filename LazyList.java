// Michael Skirpan
// Concurrent Programming
// Homework 2
// Lazy List

import java.util.concurrent.locks.ReentrantLock;


public class LazyList {

	private Node head;

	public LazyList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
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

}

