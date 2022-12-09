package puzzle;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private MinPQ<Node> pq;
    private MinPQ<Node> pqTwin;
    private Node finalNode;
    private boolean solvable;

    private class Node{
        private Board board;
        private Node predecessor;
        private int priority;

        public Node(Board board, Node predecessor) {
            this.board = board;
            this.predecessor = predecessor;
            this.priority = board.manhattan();
        }
    }

    public Solver(Board initial) {

        Node root = new Node(initial, null);
        Node rootTwin = new Node(initial.twin(), null);

        solvable = false;
        Comparator<Node> priorityComparator = new Comparator<Node>() {

            @Override
            public int compare(Node node1, Node node2) {
                return node1.priority + numOfMoves(node1) - node2.priority - numOfMoves(node2) ;
            }

            private int numOfMoves(Node node) {
                int num = 0;
                Node current = node;
                while (current.predecessor != null) {
                    num++;
                    current = current.predecessor;
                }
                return num;
            }

        };
        pq = new MinPQ<Node>(priorityComparator);
        pqTwin = new MinPQ<Node>(priorityComparator);
        pq.insert(root);
        pqTwin.insert(rootTwin);

        root = pq.delMin();
        rootTwin = pqTwin.delMin();

        while (!root.board.isGoal() && !rootTwin.board.isGoal()) {
            Iterable<Board> neighbors = new Queue<Board>();
            neighbors = root.board.neighbors();
            for (Board b : neighbors) {
                if (root.predecessor == null || !b.equals(root.predecessor.board)) {
                    Node newNeighbor = new Node(b, root);
                    pq.insert(newNeighbor);
                }
            }
            root = pq.delMin();
           
            Iterable<Board> neighborsTwin = new Queue<Board>();
            neighborsTwin = rootTwin.board.neighbors();
            for (Board b : neighborsTwin) {
                if (rootTwin.predecessor == null || !b.equals(rootTwin.predecessor.board)) {
                    Node newNeighbor = new Node(b, rootTwin);
                    pqTwin.insert(newNeighbor);
                }
            }
            rootTwin = pqTwin.delMin();
        }

        if (root.board.isGoal()) {
            solvable = true;
            finalNode = root;
        }
    }


    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        int minMoves = 0;
        Node last = finalNode;
        while (last.predecessor != null) {
            minMoves++;
            last = last.predecessor;
        }
        return minMoves;

    }

    public Iterable<Board> solution(){
        if (!isSolvable()) {
            return null;
        }
        Node last = finalNode;
        Stack<Board> solution = new Stack<Board>();
        solution.push(last.board);
        while (last.predecessor != null) {
            last = last.predecessor;
            solution.push(last.board);
        }
        return solution;

    }

    public static void main(String[] args) {
//        In in = new In("C://Users//amrpa//Downloads//8puzzle//puzzle14.txt");
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
