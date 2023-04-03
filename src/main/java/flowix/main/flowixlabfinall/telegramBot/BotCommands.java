package flowix.main.flowixlabfinall.telegramBot;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

public interface BotCommands {

    String help_text = "This bot allows you to go through authorization and registration in the FlowixLab admin panel. \n" +
            " If you want to register, click the /registration button";

    String start_registration = "Specify the secret word for registration:";

    List<BotCommand> botCommands= List.of(
            new BotCommand("/start", "start work!:)")

    );


}
