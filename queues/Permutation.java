package queues;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    private final int k;
     
    private Permutation(int k) {
        this.k = k;
    }
    
    public static void main(String[] args) {
        
        Permutation perm = new Permutation(Integer.parseInt(args[0]));
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }
        
        Iterator<String> it = rq.iterator();
        while (it.hasNext()) {
            for (int i = 0; i < perm.k; i++) {
                StdOut.print(it.next()); 
                StdOut.print("\n"); 
            }
            break;
        }
    }
}
