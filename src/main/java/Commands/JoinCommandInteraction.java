package Commands;

import Queue.CupidShuffler;
import Queue.runQueue;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JoinCommandInteraction extends ListenerAdapter {

    CupidShuffler cupidShuffler;
    ScheduledExecutorService scheduledExecutorService;
    public JoinCommandInteraction(CupidShuffler cupidShuffler, ScheduledExecutorService scheduledExecutorService) {
        this.cupidShuffler = cupidShuffler;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        if(!event.getName().equals("join")) {
            return;
        }
        System.out.println("Join");
        if(cupidShuffler.getQueueSize() != 0) {
            if (cupidShuffler.getQueue().containsKey(event.getMember())) {
                event.reply("You are already in the queue").setEphemeral(true) // reply or acknowledge
                        .flatMap(v ->
                                event.getHook().editOriginalFormat("You are already in the queue") // then edit original
                        ).queue(); // Queue both reply and edit
                return;
            }
        }
        if(event.getMember() == null) {
            return;
        }
        if (event.getMember().getVoiceState() == null) {
            event.reply("You must be in the waiting room").setEphemeral(true) // reply or acknowledge
                    .flatMap(v ->
                            event.getHook().editOriginalFormat("You must be in the waiting room") // then edit original
                    ).queue(); // Queue both reply and edit
            return;
        }
        if(event.getMember().getVoiceState().getChannel() == null) {
            if(event.getMember().getVoiceState().getChannel().getName().contains(" vs ")) {
                event.reply("You must be in the waiting room").setEphemeral(true) // reply or acknowledge
                        .flatMap(v ->
                                event.getHook().editOriginalFormat("You must be in the waiting room") // then edit original
                        ).queue(); // Queue both reply and edit
                return;
            }
            return;
        }
        if(event.getMember().getVoiceState().getChannel().getName().contains(" vs ")) {
            event.reply("You are already in a Chat").setEphemeral(true) // reply or acknowledge
                    .flatMap(v ->
                            event.getHook().editOriginalFormat("You are already in a Chat") // then edit original
                    ).queue(); // Queue both reply and edit
            return;
        }

        cupidShuffler.addMember(event.getMember());
        event.reply("You have been Added to the Queueue").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("You have been Added to the Queueue") // then edit original
                ).queue(); // Queue both reply and edit
        if(cupidShuffler.getQueueSize() == 1) {
            return;
        }

        System.out.println("runQueue1");
        scheduledExecutorService.schedule(new runQueue(cupidShuffler, scheduledExecutorService), 5, TimeUnit.SECONDS);

    }

}
