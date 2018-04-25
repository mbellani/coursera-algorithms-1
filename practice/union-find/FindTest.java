import org.junit.Assert;
import org.junit.Test;

import algos.UnionFind;

import static org.hamcrest.CoreMatchers.equalTo;

public class FindTest {

    @Test
    public void findLargest() {
        UnionFind.WeightedQuickUnion uf = new UnionFind.WeightedQuickUnion(10);
        uf.union(0, 1);
        uf.union(1, 2);
        Assert.assertThat(uf.find(1), equalTo(2));
        uf.union(0, 1);
        uf.union(3, 4);
        Assert.assertThat(uf.find(1), equalTo(2));
        Assert.assertThat(uf.find(4), equalTo(4));

    }
}
