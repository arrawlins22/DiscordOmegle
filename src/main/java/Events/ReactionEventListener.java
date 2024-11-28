package Events;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReactionEventListener extends ListenerAdapter {

    public ReactionEventListener(){
        System.out.println("ReactionEventListener Enabled");
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        super.onMessageReactionAdd(event);
        //BEE 🐝
        //BIRD 🐦
        //UNICORN 🦄
        /*
        if(event.getChannel().getName().equals("test")) {
            if(event.getMessageId().equals("1092255641621504040")) {
                System.out.println(event.getEmoji().getName());
                if(event.getEmoji().getName().equals("\uD83D\uDC1D")){  //BEE
                    System.out.println("Wahoo");
                }
            }
        }
        */
    }

}
