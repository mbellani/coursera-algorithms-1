package quiz;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import quiz.SocialNetworkConnectivity.Friendship;

import static org.hamcrest.CoreMatchers.equalTo;

public class SocialNetworkConnectivityTest {

    private SocialNetworkConnectivity connectivity;

    @Test
    public void threeMembersAndThreeConnections() {
        List<Friendship> log = new ArrayList<>();
        log.add(new Friendship(0, 1, System.currentTimeMillis()));
        log.add(new Friendship(1, 2, System.currentTimeMillis()));
        log.add(new Friendship(0, 2, System.currentTimeMillis()));
        int[] members = new int[3];
        IntStream.range(0, 1).forEach(i -> members[i] = i);
        connectivity = new SocialNetworkConnectivity();
        long earliestTime = connectivity.getEarliestTimeAllConnected(members, log);
        Assert.assertThat(earliestTime, equalTo(log.get(1).getInstant()));
    }

    @Test
    public void fiveMembersAndFiveConnections() {
        List<Friendship> log = new ArrayList<>();
        log.add(new Friendship(0, 1, System.currentTimeMillis()));
        log.add(new Friendship(1, 3, System.currentTimeMillis()));
        log.add(new Friendship(2, 4, System.currentTimeMillis()));
        log.add(new Friendship(1, 2, System.currentTimeMillis()));//every one is connected here.
        log.add(new Friendship(3, 4, System.currentTimeMillis()));
        log.add(new Friendship(1, 4, System.currentTimeMillis()));
        log.add(new Friendship(1, 4, System.currentTimeMillis()));
        log.add(new Friendship(0, 3, System.currentTimeMillis()));

        int[] members = new int[5];
        IntStream.range(0, 1).forEach(i -> members[i] = i);
        connectivity = new SocialNetworkConnectivity();
        long earliestTime = connectivity.getEarliestTimeAllConnected(members, log);
        Assert.assertThat(earliestTime, equalTo(log.get(3).getInstant()));
    }

}
