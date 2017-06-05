import com.sk89q.worldguard.internal.flywaydb.core.internal.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestVotesTop {

    @Test
    public static void main() {
        List<Pair<UUID, Integer>> dummyList = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i <= 10; i++) {
            dummyList.add(Pair.of(UUID.randomUUID(), Integer.valueOf(random.nextInt())));
        }

        System.out.println(dummyList.toString());

        dummyList.sort((playerAVotes, playerBVotes) -> playerAVotes.getRight().intValue() < playerBVotes.getRight().intValue() ? -1 :
                playerAVotes.getRight().intValue() == playerBVotes.getRight().intValue() ? 0 : 1);

        System.out.println(dummyList.toString());
    }
}
