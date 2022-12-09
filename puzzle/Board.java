package puzzle;
import edu.princeton.cs.algs4.Queue;


public class Board {

    private int[][] blocks;
    private final int dimension;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new java.lang.IllegalArgumentException();
        }
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int hamming = 0;
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                counter++;
                if (blocks[i][j] == 0) continue;
                if (blocks[i][j] != counter) {
                    hamming++; 
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;   
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                counter++;
                if (blocks[i][j] == 0) {
                    continue;
                }
                if (blocks[i][j] != counter) {
                    int line = (blocks[i][j]-1)/dimension;
                    int column = (blocks[i][j]-1) % dimension ;
                    int pr = Math.abs(i-line) + Math.abs(j-column);
                    manhattan = manhattan + pr; 
                }
            }
        }
        return manhattan;
    }


    public boolean isGoal() {
        if (blocks[dimension-1][dimension-1] != 0) {
            return false;
        }
        return hamming() == 0;
    }

    public Board twin() {
        Board twin = new Board(blocks);
        if (twin.blocks[0][0] == 0 || twin.blocks[0][1] == 0) {
            swap(twin.blocks, 1, 1, 0, 1);
        } else {
            swap(twin.blocks, 0, 0, 0, 1);
        }
        return twin;
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;

        if (this.dimension != that.dimension) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != (that.blocks)[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors(){
        Queue<Board> neighbors = new Queue<Board>();
        int line = 0;
        int column = 0;
        int[][] temp = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                temp[i][j] = this.blocks[i][j];
                if (this.blocks[i][j] == 0) {
                    line = i;
                    column = j;
                }
            }
        }
        if (line < dimension-1) {
            swap(temp, line, line+1, column, column);
            neighbors.enqueue(new Board(temp));
            swap(temp, line+1, line, column, column);
        }
        if (line > 0) {
            swap(temp, line, line-1, column, column);
            neighbors.enqueue(new Board(temp));
            swap(temp, line-1, line, column, column);
        }
        if (column < dimension-1) {
            swap(temp, line, line, column, column+1);
            neighbors.enqueue(new Board(temp));
            swap(temp, line, line, column+1, column);
        }
        if (column > 0) {
            swap(temp, line, line, column, column-1);
            neighbors.enqueue(new Board(temp));
            swap(temp, line, line, column-1, column);
        }
        return neighbors;
    }

    private void swap(int[][] tiles, int lineFrom, int lineTo, int columnFrom, int columnTo ) {
        int temp = tiles[lineFrom][columnFrom];
        tiles[lineFrom][columnFrom] = tiles[lineTo][columnTo];
        tiles[lineTo][columnTo] = temp;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        int[][] blocks = null;
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
    }
}
