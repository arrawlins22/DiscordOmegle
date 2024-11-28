package Commands;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;


public class MenuCommandInteraction extends ListenerAdapter {

    public MenuCommandInteraction() {
        System.out.println("MenuCommandInteraction Enabled");
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        if(!event.getName().equals("menu") || !(event.getMember().getEffectiveName().equals("Frost_Shadow_22"))) {
            event.reply("You Do Not Have Rights").setEphemeral(true) // reply or acknowledge
                    .flatMap(v ->
                            event.getHook().editOriginalFormat("You Do Not Have Rights") // then edit original
                    ).queue(); // Queue both reply and edit
            return;
        }
        Message message0 = new MessageBuilder()
                .append("What age group are you in?")
                .setActionRows(
                        ActionRow.of(Button.primary("me-Kid", "13-17"), Button.primary("me-Adult", "18-24"), Button.primary("me-Old", "25+")))
                .build();
        Message message1 = new MessageBuilder()
                .append("What are you?")
                .setActionRows(
                        ActionRow.of(Button.primary("me-Male", "Male"), Button.primary("me-Female", "Female"), Button.primary("me-Other","Other")))
                .build();
        Message message2 = new MessageBuilder()
                .append("What do you want to be matched with?")
                .setActionRows(
                        ActionRow.of(Button.primary("you-Male", "Male"), Button.primary("you-Female", "Female"), Button.primary("you-Any", "Any")))
                .build();
        Message message3 = new MessageBuilder()
                .append("Click this button to toggle matching with only users 18+")
                .setActionRows(
                        ActionRow.of(Button.primary("you-Age", "18+")))
                .build();
        event.getChannel().sendMessage(message0).queue();
        event.getChannel().sendMessage(message1).queue();
        event.getChannel().sendMessage(message2).queue();
        event.getChannel().sendMessage(message3).queue();
        event.reply("As you wish").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("As you wish") // then edit original
                ).queue(); // Queue both reply and edit
    }
}
