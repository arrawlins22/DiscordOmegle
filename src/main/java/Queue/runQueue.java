package Queue;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class runQueue implements Runnable{

    CupidShuffler cupidShuffler;
    private Map<Member, Instant> queue;
    private ArrayList<String> recentMatches;
    ScheduledExecutorService theMasterScheduler;

    public runQueue(CupidShuffler cupidShuffler, ScheduledExecutorService scheduledExecutorService) {
        this.cupidShuffler = cupidShuffler;
        this.queue = cupidShuffler.getQueue();
        this.recentMatches = cupidShuffler.getRecentMatches();
        theMasterScheduler = scheduledExecutorService;
    }

    public boolean hasRecentlyMatched(Member member1, Member member2) {
        if(recentMatches.isEmpty()) {
            return false;
        }
        if(recentMatches.contains(member1.getUser().getName() + " vs " + member2.getUser().getName())) {
            return true;
        }
        if(recentMatches.contains(member2.getUser().getName() + " vs " + member1.getUser().getName())) {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("Queue Running... Current Size: " + cupidShuffler.getQueueSize());

        if(queue.size() < 2) {
            System.out.println("Queue Stopping...");
            return;
        }
        Member character1 = null;
        Member character2 = null;
        int i = 0;
        int j = 0;
        //MATCHING TIME
        for(i = 0; i < queue.size(); i++) {
            //pick guy one to compare
            character1 = (Member) queue.keySet().toArray()[i];
            for(j = 0; j < queue.size(); j++) {
                //pick guy two to compare
                character2 = (Member) queue.keySet().toArray()[j];
                if(character1 == character2) {

                } else if(hasRecentlyMatched(character1, character2)) {

                } else {
                    break;
                }
            }
        }
        if(i == queue.size() && j == queue.size()) {
            scheduledExecutorService.schedule(new runQueue(cupidShuffler, scheduledExecutorService), 5, TimeUnit.SECONDS);
            return;
        }
        cupidShuffler.removeMember(character1);
        cupidShuffler.removeMember(character2);
        String message = character1.getUser().getName() + " vs " + character2.getUser().getName();
        System.out.println(message);
        Guild guild = character1.getGuild();

        guild.getTextChannelById("1088949028118593576").sendMessage("CONNECTED: " + message).queue();

        ChannelAction<VoiceChannel> channelAction = guild.getCategoriesByName("VS", true).get(0).createVoiceChannel(character1.getUser().getName() + " vs " + character2.getUser().getName());
        //ChannelAction<VoiceChannel> channelAction = guild.createVoiceChannel(character1.getUser().getName() + " vs " + character2.getUser().getName());

        recentMatches.add(message);
        // Schedule the task (in this case, the 'myFunction' method) to run 5 minutes from now
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                recentMatches.remove(message);
                System.out.println("REMOVED Match From Recents");
            }
        }, 1, TimeUnit.MINUTES);

        Member finalCharacter1 = character1;
        Member finalCharacter2 = character2;

        channelAction.addPermissionOverride(finalCharacter1,
                EnumSet.of(Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY, Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT),
                EnumSet.of(Permission.VOICE_STREAM));

        channelAction.addPermissionOverride(finalCharacter2,
                EnumSet.of(Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY, Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT),
                EnumSet.of(Permission.VOICE_STREAM));

        channelAction.queue(newChannel -> {
            // Move the members to the newly created voice channel
            newChannel.getManager().setUserLimit(1).queue();
            guild.moveVoiceMember(finalCharacter1, newChannel).queue();
            guild.moveVoiceMember(finalCharacter2, newChannel).queue();

        });
        //channelAction.removePermissionOverride(finalCharacter1).queue();
        scheduledExecutorService.schedule(new runQueue(cupidShuffler, scheduledExecutorService), 5, TimeUnit.SECONDS);
    }
}
