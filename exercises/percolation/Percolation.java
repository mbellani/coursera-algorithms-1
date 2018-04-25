import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;
    private final int size;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        // By adding two extra rows to the grid we are accommodating a row for virtual to and virtual bottom, this
        // also allows us to use 1 based index without worrying too much about converting input row,col values.
        this.size = n + 2;
        this.grid = new boolean[size][size];
        this.uf = new WeightedQuickUnionUF(size * size);
        this.n = n;
        this.virtualTopIndex = xyto1D(n, n) + 1;
        this.virtualBottomIndex = xyto1D(n, n) + 2;
    }


    public void open(int row, int col) {
        validate(row, col);
        grid[row][col] = true;
        openSites++;
        // next column
        connect(row, col, row, col + 1);
        // previous column
        connect(row, col, row, col - 1);
        // top column
        connect(row, col, row - 1, col);
        // bottom column
        connect(row, col, row + 1, col);
        // connect to virtualTop if current row is first row.
        if (row == 1) {
            uf.union(virtualTopIndex, xyto1D(row, col));
        }
        if (row == n) {
            uf.union(virtualBottomIndex, xyto1D(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        // flatten row,col and check if it is connected to virtual top
        int nodeIndex = xyto1D(row, col);
        return uf.connected(nodeIndex, virtualTopIndex);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(virtualTopIndex, virtualBottomIndex);
    }

    private void connect(int row, int col, int nextRow, int nextCol) {
        if (isValidCell(nextRow, nextCol) && isOpen(nextRow, nextCol)) {
            int currNodeIndex = xyto1D(row, col);
            int nextNodeIndex = xyto1D(nextRow, nextCol);
            if (!uf.connected(currNodeIndex, nextNodeIndex)) {
                uf.union(currNodeIndex, nextNodeIndex);
            }
        }
    }

    private void validate(int row, int col) {
        if (!(isValidIndex(row) && isValidIndex(col))) {
            throw new IllegalArgumentException("invalid row or column row: " + row + " column:" + col);
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 1 && index <= n;
    }

    private boolean isValidCell(int row, int col) {
        try {
            validate(row, col);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private int xyto1D(int row, int col) {
        // Subtract 1 to get correct index since we have 1 base indexing here.
        return (row * (size - 1) + col) - 1;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(3, 3);
        System.out.println(percolation.numberOfOpenSites());
    }
}
