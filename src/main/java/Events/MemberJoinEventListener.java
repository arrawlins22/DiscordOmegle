package Events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MemberJoinEventListener extends ListenerAdapter {


    private final String JoinLeaveChannelID = "1090001312365682869";

    public MemberJoinEventListener() {
        System.out.println("MemberJoinEventListener Enabled");
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        Guild guild = event.getGuild();
        User user = event.getUser();

        guild.getTextChannelById(JoinLeaveChannelID).sendMessage(user.getName() + " has joined Discord Omegle!").queue();
    }
}
