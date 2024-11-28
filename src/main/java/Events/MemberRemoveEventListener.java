package Events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MemberRemoveEventListener extends ListenerAdapter {

    private final String JoinLeaveChannelID = "1090001312365682869";

    public MemberRemoveEventListener() {
        System.out.println("MemberJoinEventListener Enabled");
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        super.onGuildMemberRemove(event);
        Guild guild = event.getGuild();
        User user = event.getUser();

        guild.getTextChannelById(JoinLeaveChannelID).sendMessage(user.getName() + " has Left Discord Omegle!").queue();
    }
}
