import Events.*;
import Commands.*;
import Queue.CupidShuffler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DiscordBot {

    public static void main(String[] args) throws LoginException {
        Dotenv dotenv = Dotenv.load();
        String DiscordKey = dotenv.get("DISCORD_TOKEN");

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        CupidShuffler cupidShuffler = new CupidShuffler();
        JDABuilder jdaBuilder = JDABuilder.createDefault(DiscordKey);

        jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_REACTIONS);

        jdaBuilder.addEventListeners(new ReadyEventListener(),
                                     new MessageEventListener(),
                                     new GuildVoiceUpdateEventListener(cupidShuffler, scheduledExecutorService),
                                     new TestCommandInteraction(cupidShuffler),
                                     new MemberRemoveEventListener(),
                                     new MemberJoinEventListener(),
                                     new ReactionEventListener(),
                                     new ButtonInteractionEventListener(),
                                     new MenuCommandInteraction());
                                     //new JoinCommandInteraction(cupidShuffler, scheduledExecutorService),
                                     //new NextCommandInteraction(cupidShuffler, scheduledExecutorService));
        JDA client = jdaBuilder.build();

        client.upsertCommand("test", "Use this command to check on the Bot").setGuildOnly(true).queue();
        client.upsertCommand("menu", "This command is for the smart man").setGuildOnly(true).queue();
        //client.upsertCommand("next", "Use this command to rejoin the Queue").setGuildOnly(true).queue();
    }

}
