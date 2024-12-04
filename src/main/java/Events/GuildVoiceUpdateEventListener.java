package Events;

import Queue.runQueue;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import Queue.CupidShuffler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GuildVoiceUpdateEventListener extends ListenerAdapter {

    CupidShuffler cupidShuffler;
    ScheduledExecutorService scheduledExecutorService;

    public GuildVoiceUpdateEventListener(CupidShuffler cupidShuffler, ScheduledExecutorService scheduledExecutorService) {
        this.cupidShuffler = cupidShuffler;
        this.scheduledExecutorService = scheduledExecutorService;
        System.out.println("GuildVoiceUpdateEventLister Enabled");
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        super.onGuildVoiceUpdate(event);
        if(!(event.getChannelJoined() == null)) {
            if( (event instanceof GuildVoiceMoveEvent || event instanceof GuildVoiceJoinEvent) && event.getChannelJoined().getName().contains(" vs ")) {
                if(event.getChannelLeft() == null) {
                    return;
                }
                if(event.getChannelLeft().getName().contains(" vs ")) {
                    if(event.getChannelLeft().getMembers().size() == 0) {
                        event.getChannelLeft().delete().queue();
                    }
                }
                return;
            }
            if( (event instanceof GuildVoiceMoveEvent || event instanceof GuildVoiceJoinEvent) && event.getChannelJoined().getId().equals("1313760241028169779")) {
                cupidShuffler.addMember(event.getMember());
                if(cupidShuffler.getQueueSize() == 2) {
                    scheduledExecutorService.schedule(new runQueue(cupidShuffler, scheduledExecutorService), 5, TimeUnit.SECONDS);
                }
            }
        }
        if(!(event instanceof GuildVoiceLeaveEvent || event instanceof GuildVoiceMoveEvent)) {
            return;
        }
        if(event.getChannelLeft() == null) {
            return;
        }
        if(event.getChannelLeft().getId().equals("1313760241028169779")) {
            cupidShuffler.removeMember(event.getMember());
        }
        if(!event.getChannelLeft().getName().contains(" vs ")) {
            return;
        }
        if(event.getChannelLeft().getMembers().isEmpty()) {
            event.getGuild().getTextChannelById("1088949028118593576").sendMessage("DISCONNECT: " + event.getChannelLeft().getName()).queue();
            event.getChannelLeft().delete().queue();
        }
        if(event.getChannelLeft().getMembers().size() == 1) {
            cupidShuffler.addMember(event.getChannelLeft().getMembers().get(0));
            event.getGuild().moveVoiceMember(event.getChannelLeft().getMembers().get(0), event.getGuild().getVoiceChannelById("1313760241028169779")).queue();
        }

    }

}
