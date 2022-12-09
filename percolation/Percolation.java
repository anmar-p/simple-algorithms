package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private boolean[][] status;  // 0 for closed and 1 for open sites
    private final int upperVirtualSite, lowerVirtualSite;
    private final int size;  // n - dimension of array
    private int openSites;
    private final WeightedQuickUnionUF uf;
    
    public Percolation(int n) {
        size = n;
        if (size <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(size*size+2); // holds 2 extra indices for the virtual sites  
        openSites = 0;
        upperVirtualSite = size*size;
        lowerVirtualSite = size*size+1; 
        status = new boolean[size][size];  // creates n by n grid, all sites blocked (false)
    }
    
    public void open(int row, int col) {
        validateIndices(row, col);
        if (!status[row-1][col-1]) {
            status[row-1][col-1] = true;
            openSites++;
            if (row == 1) { // Connect to upperVirtualSite if on top row
                uf.union(xyTo1D(row, col), upperVirtualSite);
            }
            if (row == size) { // Connect to lowerVirtualSite if on final row   && isFull(row, col) may stop backwash
                uf.union(xyTo1D(row, col), lowerVirtualSite); 
            }
            if (!(col % size == 0) && size > 1) { // compare with next column
                if (status[row-1][col]) {
                    uf.union(xyTo1D(row, col), xyTo1D(row, col)+1);       
                }
            }
            if (!(col % size == 1) && size > 1) { // compare with previous column
                if (status[row-1][col-2]) {
                    uf.union(xyTo1D(row, col), xyTo1D(row, col)-1); 
                }
            }
            if (row > 1 && size > 1) { // compare with previous row
                if (status[row-2][col-1]) {
                    uf.union(xyTo1D(row, col), xyTo1D(row, col)-size);    
                }
            }
            if (row < size && size > 1) { // compare with next row
                if (status[row][col-1]) {
                    uf.union(xyTo1D(row, col), xyTo1D(row, col)+size); 
                }
            }
        }
        return;
    }
    
    public boolean isOpen(int row, int col) { // checks if a site is open 
        validateIndices(row, col);
        return (status[row-1][col-1]);
    }
    
    // checks if a site is full (connected to a site in the top row)
    public boolean isFull(int row, int col) {  
        validateIndices(row, col);
        int currentId = xyTo1D(row, col);
        return uf.connected(currentId, upperVirtualSite);
    }
    
    // returns number of open sites
    public int numberOfOpenSites() { 
        return openSites;
    }
    
    // checks if the grid percolates (at least one site of the top row is 
    // connected to at least one site of the final row
    public boolean percolates() {  
        return uf.connected(upperVirtualSite, lowerVirtualSite);
    }
    
    private void validateIndices(int row, int col) {
        if (row <= 0 || row > size) {
            throw new java.lang.IllegalArgumentException();
        }
        if (col <= 0 || col > size) {
            throw new java.lang.IllegalArgumentException();
        } 
        return;
    }
    
    // turns row, column indices to a one dimension array
    private int xyTo1D(int row, int col) { 
        int newId = (row-1)*size+(col-1);
        return newId;
    }
}
