package Events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;

public class MessageEventListener extends ListenerAdapter {

    private final String rulesChannelID = "1088921581432090654";
    private final String playerRoleID = "1088944530394468352";

    ArrayList<Member> camReady = new ArrayList<Member>();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if((event.getChannel().equals(event.getGuild().getChannelById(TextChannel.class,"1092285263960612916")))){

            if(!event.getMember().getRoles().get(0).getName().equals("Queueueue")) {
                event.getMessage().delete().queue();
                return;
            }

            if(event.getMessage().getContentRaw().contains("Selected")) {
                event.getMessage().delete().queue();
                return;
            }
        }

        if((event.getChannel().equals(event.getGuild().getChannelById(TextChannel.class, rulesChannelID)))){
            if(event.getMember().getEffectiveName().equals("Internet Douchebag")) {
                return;
            }
            event.getMessage().delete().queue();
            if(event.getMessage().getContentRaw().equalsIgnoreCase("accept") && !event.getMember().getRoles().contains(event.getGuild().getRoleById(playerRoleID))) {
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(playerRoleID)).queue();
                event.getGuild().getTextChannelById("1088949028118593576").sendMessage(event.getMessage().getMember() + " Accepted The Rules (Maybe Isn't A Bot)");
            }
        }
        //NO CHANNEL WITH THIS NAME?
        if(event.getChannel().getName().equals("join-queue")) {
            event.getMessage().delete().queue();
        }
        //MESSAGE SENT IN Matchup
        if(event.getChannel().getName().contains(" vs ")) {
            //STREAM/CAMERA COMMAND
            if ( event.getMessage().getContentRaw().equals("!cam")) {
                camReady.add(event.getMember());
                String channelName = event.getChannel().getName();
                VoiceChannel vc = event.getGuild().getVoiceChannelsByName(channelName, false).get(0);
                Member member1 = vc.getMembers().get(0);
                Member member2 = vc.getMembers().get(1);
                //IF BOTH CONSENT -> CREATE NEW PERMISSION CHANNEL
                if(camReady.contains(member1) && camReady.contains(member2)) {
                    camReady.remove(member1);
                    camReady.remove(member2);
                    ChannelAction<VoiceChannel> channelAction = event.getGuild().getCategoriesByName("VS", true).get(0).createVoiceChannel(member1.getEffectiveName() + " vs " + member2.getEffectiveName());
                    channelAction.addPermissionOverride(member1,
                            EnumSet.of(Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY, Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT),
                            null);

                    channelAction.addPermissionOverride(member2,
                            EnumSet.of(Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY, Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT),
                            null);

                    channelAction.queue(newChannel -> {
                        // Move the members to the newly created voice channel
                        newChannel.getManager().setUserLimit(1).queue();
                        event.getGuild().moveVoiceMember(member1, newChannel).queue();
                        event.getGuild().moveVoiceMember(member2, newChannel).queue();

                    });
                }
            }
        }
    }

}
