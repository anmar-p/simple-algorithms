package queues;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        
    // UncommentedEmptyMethodBody
    }
    
    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (size == 0) {
            head = new Node();
            head.item = item;
            tail = head;
        } else {
            Node oldFirst = head;
            head = new Node();
            head.item = item;
            head.next = oldFirst;
            head.previous = null;
            oldFirst.previous = head;
        }
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (size == 0) {
            tail = new Node();
            tail.item = item;
            head = tail;
        } else {
            Node oldLast = tail;
            tail = new Node();
            tail.item = item;
            tail.next = null;
            tail.previous = oldLast;
            oldLast.next = tail;
        }
        size++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item first = head.item;
        if (size > 1) {
            head = head.next;
            head.previous = null;
        }
        size--;
        return first;
        
    }
    
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        } 
        Item last = tail.item;
        if (size > 1) {
            tail = tail.previous;
            tail.next = null;
        }
        size--;
        return last;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {

        private Node current = head;
        
        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            } else {
                throw new java.util.NoSuchElementException();
            }
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
    }
    
    public static void main(String[] args) {
        
    }

}
