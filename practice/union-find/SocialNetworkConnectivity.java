import java.util.List;

import algos.UnionFind.WeightedQuickUnion;

/**
 * Problem: Social network connectivity. Given a social network containing n members and a log file containing m
 * timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at
 * which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that
 * the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your
 * algorithm should be mlogn or better and use extra space proportional to n.
 */
public class SocialNetworkConnectivity {

    public long getEarliestTimeAllConnected(int members[], List<Friendship> log) {
        WeightedQuickUnion uf = new WeightedQuickUnion(members.length);
        int totalConnections = 0;
        long instant = 0;
        for (Friendship f : log) {
            if (!uf.isConnected(f.from, f.to)) {
                uf.union(f.from, f.to);
                totalConnections++;
                if (totalConnections >= (members.length - 1)) {
                    instant = f.instant;
                    break;
                }
            }
        }
        return instant;
    }

    public static class Friendship {
        private final int from;
        private final int to;
        private final long instant;

        public Friendship(int from, int getMemberIdTo, long instant) {
            this.from = from;
            this.to = getMemberIdTo;
            this.instant = instant;
        }

        public long getInstant() {
            return instant;
        }
    }
}
