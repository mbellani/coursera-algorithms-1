import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] blocks;

    public Board(int blocks[][]) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
        this.blocks = blocks;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }


    public int hamming() {
        int hamming = 0;
        for (int r = 0; r < blocks.length; r++) {
            int[] row = blocks[r];
            for (int c = 0; c < row.length; c++) {
                int block = row[c];
                // Check block is in correct position e.g. 1 is in 1st place and 2 in 2nd and so.. forth.
                if (!isEmptyBlock(block) && block != (1 + (r * row.length) + c)) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        int numRows = blocks.length;
        int currentRow = 0;
        for (int[] row : blocks) {
            int numCols = row.length;
            int currentCol = 0;
            for (int block : row) {
                if (!isEmptyBlock(block)) {
                    int targetRow = (int) Math.ceil(block / (double) numRows) - 1;
                    int targetCol = (block - 1) % numCols;
                    manhattan += Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
                }
                currentCol++;
            }
            currentRow++;
        }
        return manhattan;
    }


    public int dimension() {
        return blocks.length;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (isEmptyBlock(blocks[i][j])) {
                    // Move up
                    addNeighbor(neighbors, move(i, j, i - 1, j));
                    // Move down
                    addNeighbor(neighbors, move(i, j, i + 1, j));
                    // Move left
                    addNeighbor(neighbors, move(i, j, i, j - 1));
                    // Move right
                    addNeighbor(neighbors, move(i, j, i, j + 1));
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board) {
            Arrays.deepEquals(((Board) obj).blocks, this.blocks);
            return false;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(blocks.length);
        output.append("\n");
        for (int[] row : blocks) {
            for (int col : row) {
                output.append(col).append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }

    private void addNeighbor(List<Board> boards, Board board) {
        if (board != null) {
            boards.add(board);
        }
    }

    private Board move(int row, int col, int targetRow, int targetCol) {
        if (!isValid(row, col) && isValid(targetRow, targetCol)) {
            return null;
        } else {
            int[][] result = copy(blocks);
            int tmp = result[row][col];
            result[row][col] = result[targetRow][targetCol];
            result[targetRow][targetCol] = tmp;
            return new Board(result);
        }
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][];
        for (int i = 0; i < blocks.length; i++) {
            copy[i] = Arrays.copyOf(blocks[i], blocks[i].length);
        }
        return copy;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row <= blocks.length // check if row is valid
                && col >= 0 && col <= blocks[row].length; // check if column is valid
    }


    private boolean isEmptyBlock(int block) {
        return block == 0;
    }


    public static void main(String[] args) {
        int[][] board = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(board);
        System.out.println(b);
        for (Board neighbor : b.neighbors()) {
            System.out.println(neighbor);
        }
    }
}
