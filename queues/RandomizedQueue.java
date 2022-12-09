package queues;
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array = (Item[]) new Object[1];
    private int size = 0;
    
    public RandomizedQueue() {
     // UncommentedEmptyMethodBody
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (size == array.length) {
            resize(2*array.length);
        }
        array[size++] = item;
    }
    
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomNumber = StdRandom.uniform(0, size);
        Item toRemove = array[randomNumber];
        if (randomNumber < size-1 && size > 1) {
            for (int i = randomNumber; i < size-2; i++) {
                array[randomNumber] = array[randomNumber+1];
            }
            array[size-1] = null;
        } else {
            array[randomNumber] = null;
        }
        size--;
        if (size > 0 && size == array.length/4) resize(array.length/2);
        return toRemove;
    }
    
    private void resize(int newArraySize) {
        Item[] newArray = (Item[]) new Object[newArraySize];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
    
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int peek = StdRandom.uniform(0, size);
        return array[peek];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffled = Arrays.copyOfRange(array, 0, size);
        private int n = 0;
        
        private RandomizedQueueIterator() {
            StdRandom.shuffle(shuffled);
        }
        
        public boolean hasNext() {
            return n < size;
        }

        public Item next() {
            if (hasNext()) {
                return shuffled[n++];
            } else {
                throw new java.util.NoSuchElementException();
            }
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(220);
        System.out.println(rq.dequeue());
        System.out.println(rq.size());
        System.out.println(rq.isEmpty());
        rq.enqueue(84);
        rq.enqueue(62);
        System.out.println(rq.size());
        System.out.println(rq.dequeue());
        System.out.println(rq.size());
        System.out.println(rq.dequeue());
        
    }
}
