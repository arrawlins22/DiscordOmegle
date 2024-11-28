package Queue;

import net.dv8tion.jda.api.entities.Member;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CupidShuffler {

    private Map<Member, Instant> queue;
    private ArrayList<String> recentMatches;

    public CupidShuffler() {
        queue = new HashMap<>();
        recentMatches = new ArrayList<String>();
    }

    public int getQueueSize() {
        return queue.size();
    }
    public Map<Member, Instant> getQueue() {
        return queue;
    }
    public ArrayList<String> getRecentMatches() {
        return recentMatches;
    }

    public void addMember(Member member) {
        queue.put(member, Instant.now());
    }

    public void removeMember(Member member) {
        queue.remove(member);
    }

}
