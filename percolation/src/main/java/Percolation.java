/**
 * Created by kgp on 10/02/2014.
 */
public class Percolation {

    private WeightedQuickUnionUF weightedQuickUnionUF;

    // the matrix is not needed, we can calculate with index is
    private boolean[] openedSites;
    private int n;
    private int topVirtualNode;
    private boolean percolates;
    private int bottomVirtualNode;

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        this.n = n;
        openedSites = new boolean[n * n];
        topVirtualNode = n * n;
        bottomVirtualNode = topVirtualNode + 1;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(bottomVirtualNode + 1);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validateIndexes(i, j);
        int realI = i - 1;
        int realJ = j - 1;
        int element = getMatrixIndex(realI, realJ);
        int leftAdjacent = getLeftAdjacent(realI, realJ);
        int rightAdjacent = getRightAdjacent(realI, realJ);
        int bottomAdjacent = getBottomAdjacent(realI, realJ);
        int topAdjacent = getTopAdjacent(realI, realJ);
        if (leftAdjacent != -1 && openedSites[leftAdjacent]) {
            weightedQuickUnionUF.union(element, leftAdjacent);
        }
        if (rightAdjacent != -1 && openedSites[rightAdjacent]) {
            weightedQuickUnionUF.union(element, rightAdjacent);
        }
        if (bottomAdjacent != -1 && openedSites[bottomAdjacent]) {
            weightedQuickUnionUF.union(element, bottomAdjacent);
        }
        if (topAdjacent != -1 && openedSites[topAdjacent]) {
            weightedQuickUnionUF.union(element, topAdjacent);
        }
        if (realI == 0) {
            weightedQuickUnionUF.union(element, topVirtualNode);
        }
        openedSites[element] = true;
    }

    private void validateIndexes(int i, int j) {
        if (i <= 0 || i > n) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j <= 0 || j > n) {
            throw new IndexOutOfBoundsException("column index j out of bounds");
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateIndexes(i, j);
        int element = getMatrixIndex(i - 1, j - 1);
        return openedSites[element];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateIndexes(i, j);
        int element = getMatrixIndex(i - 1, j - 1);
        return weightedQuickUnionUF.connected(topVirtualNode, element);
    }

    // does the system percolate?
    public boolean percolates() {
        if (!percolates){
            for (int j = 0; j < n; j++) {
                int element = getMatrixIndex(n - 1, j);
                if (openedSites[element] && weightedQuickUnionUF.connected(topVirtualNode, element)) {
                    percolates = true;
                    break;
                }
            }
        }
        return percolates;
    }

    private int getMatrixIndex(int i, int j) {
        return (i * n) + j;
    }

    private int getLeftAdjacent(int i, int j) {
        if (j > 0) {
            return getMatrixIndex(i, j - 1);
        }
        return -1;
    }

    private int getRightAdjacent(int i, int j) {
        if (j < n - 1) {
            return getMatrixIndex(i, j + 1);
        }
        return -1;
    }

    private int getBottomAdjacent(int i, int j) {
        if (i < n - 1) {
            return getMatrixIndex(i + 1, j);
        }
        return -1;
    }

    private int getTopAdjacent(int i, int j) {
        if (i > 0) {
            return getMatrixIndex(i - 1, j);
        }
        return -1;
    }

}
