package Events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ButtonInteractionEventListener extends ListenerAdapter {

    Map<String, Role> guildRoles;
    public ButtonInteractionEventListener() {
        guildRoles = new HashMap<String, Role>();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        Guild guild = event.getGuild();
        Member member = event.getMember();
        if(guildRoles.isEmpty()) {
            initRoles(guild);
        }

        if (event.getButton().getId().equals("me-Kid")) {
            if(member.getRoles().contains(guildRoles.get("18-24"))) {
                guild.removeRoleFromMember(member, guildRoles.get("18-24")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("25+"))) {
                guild.removeRoleFromMember(member, guildRoles.get("25+")).queue();
            }
            event.reply("Selected 13-17").queue();
            if(member.getRoles().contains(guildRoles.get("13-17"))) {

            }
            guild.addRoleToMember(member, guildRoles.get("13-17")).queue();

        } else if (event.getButton().getId().equals("me-Adult")) {

            if(member.getRoles().contains(guildRoles.get("13-17"))) {
                guild.removeRoleFromMember(member, guildRoles.get("13-17")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("25+"))) {
                guild.removeRoleFromMember(member, guildRoles.get("25+")).queue();
            }
            event.reply("Selected 18-24").queue();
            if(member.getRoles().contains(guildRoles.get("18-24"))) {

            }
            guild.addRoleToMember(member, guildRoles.get("18-24")).queue();

        } else if (event.getButton().getId().equals("me-Old")) {

            if(member.getRoles().contains(guildRoles.get("13-17"))) {
                guild.removeRoleFromMember(member, guildRoles.get("13-17")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("18-24"))) {
                guild.removeRoleFromMember(member, guildRoles.get("18-24")).queue();
            }
            event.reply("Selected 25+").queue();
            if(member.getRoles().contains(guildRoles.get("25+"))) {

            }
            guild.addRoleToMember(member, guildRoles.get("25+")).queue();


        } else if (event.getButton().getId().equals("me-Male")) {

            if(member.getRoles().contains(guildRoles.get("Female"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Female")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("Other"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Other")).queue();
            }
            event.reply("Selected Male").queue();
            if(member.getRoles().contains(guildRoles.get("Male"))) {
                return;
            }
            guild.addRoleToMember(member, guildRoles.get("Male")).queue();

        } else if (event.getButton().getId().equals("me-Female")) {

            if(member.getRoles().contains(guildRoles.get("Male"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Male")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("Other"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Other")).queue();
            }
            event.reply("Selected Female").queue();
            if(member.getRoles().contains(guildRoles.get("Female"))) {
                return;
            }
            guild.addRoleToMember(member, guildRoles.get("Female")).queue();

        } else if (event.getButton().getId().equals("me-Other")) {

            if(member.getRoles().contains(guildRoles.get("Male"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Male")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("Female"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Female")).queue();
            }
            event.reply("Selected Other").queue();
            if(member.getRoles().contains(guildRoles.get("Other"))) {
                return;
            }
            guild.addRoleToMember(member, guildRoles.get("Other")).queue();


        } else if (event.getButton().getId().equals("you-Male")) {

            if(member.getRoles().contains(guildRoles.get("Seeking Female"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Seeking Female")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("Seeking Any"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Seeking Any")).queue();
            }
            event.reply("Selected Male").queue();
            if(member.getRoles().contains(guildRoles.get("Seeking Male"))) {
                return;
            }
            guild.addRoleToMember(member, guildRoles.get("Seeking Male")).queue();

        } else if (event.getButton().getId().equals("you-Female")) {

            if(member.getRoles().contains(guildRoles.get("Seeking Male"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Seeking Male")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("Seeking Any"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Seeking Any")).queue();
            }
            event.reply("Selected Female").queue();
            if(member.getRoles().contains(guildRoles.get("Selected Female"))) {
                return;
            }
            guild.addRoleToMember(member, guildRoles.get("Seeking Female")).queue();

        } else if (event.getButton().getId().equals("you-Any")) {

            if(member.getRoles().contains(guildRoles.get("Seeking Male"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Seeking Male")).queue();
            }
            if(member.getRoles().contains(guildRoles.get("Seeking Female"))) {
                guild.removeRoleFromMember(member, guildRoles.get("Seeking Female")).queue();
            }
            event.reply("Selected Any").queue();
            if(member.getRoles().contains(guildRoles.get("Seeking Any"))) {
                return;
            }
            guild.addRoleToMember(member, guildRoles.get("Seeking Any")).queue();

        } else if (event.getButton().getId().equals("you-Age")) {

            if(member.getRoles().contains(guildRoles.get("18+ Only"))) {
                guild.removeRoleFromMember(member, guildRoles.get("18+ Only")).queue();
                event.reply("Selected 18+ Toggled Off").queue();
            } else {
                guild.addRoleToMember(member, guildRoles.get("18+ Only")).queue();
                event.reply("Selected 18+ Toggled On").queue();
            }

        }
    }

    private void initRoles(Guild guild) {
        guildRoles.put("13-17",guild.getRoleById("1092269892692099173"));
        guildRoles.put("18-24",guild.getRoleById("1092269996077486242"));
        guildRoles.put("25+", guild.getRoleById("1092270128059650088"));

        guildRoles.put("Male",guild.getRoleById("1092272351556685896"));
        guildRoles.put("Female",guild.getRoleById("1092272459140575314"));
        guildRoles.put("Other", guild.getRoleById("1092272487955439698"));

        guildRoles.put("Seeking Male",guild.getRoleById("1092272512458571786"));
        guildRoles.put("Seeking Female",guild.getRoleById("1092272565524901889"));
        guildRoles.put("Seeking Any", guild.getRoleById("1092272596994752641"));

        guildRoles.put("18+ Only", guild.getRoleById("1092272837009608734"));

    }
}
