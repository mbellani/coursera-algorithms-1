package algos;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuickFindTest {

    private UnionFind.QuickUnion quickFind;

    @Before
    public void setup() {
        quickFind = new UnionFind.WeightedQuickUnionWithPathCompression(10);
        quickFind.union(0, 1);
        quickFind.union(0, 2);
        quickFind.union(1, 3);
        quickFind.union(3, 6);
        quickFind.union(1, 7);
    }

    @Test
    public void isConnected() {
        assertThat(quickFind.isConnected(0, 1), is(true));
        assertThat(quickFind.isConnected(0, 2), is(true));
        assertThat(quickFind.isConnected(1, 2), is(true));
        assertThat(quickFind.isConnected(1, 7), is(true));
    }
}
