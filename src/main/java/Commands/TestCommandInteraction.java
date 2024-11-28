package Commands;

import Queue.CupidShuffler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class TestCommandInteraction extends ListenerAdapter {

    CupidShuffler cupidShuffler;
    public TestCommandInteraction(CupidShuffler cupidShuffler) {
        this.cupidShuffler = cupidShuffler;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        long time = System.currentTimeMillis();
        if(!event.getName().equals("test")) {
            return;
        }
        event.deferReply();
        System.out.println("Test Command Called");
        if(event.getUser().getName().equals("Frost_Shadow_22") || event.getUser().getName().equals("Internet Douchebag")) {
            event.reply("Doing readout").setEphemeral(true) // reply or acknowledge
                    .flatMap(v ->
                            event.getHook().editOriginalFormat("Current Queue size: %d", cupidShuffler.getQueueSize()) // then edit original
                    ).queue(); // Queue both reply and edit
        }
    }
}
