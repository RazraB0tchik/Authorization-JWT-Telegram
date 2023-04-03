package flowix.main.flowixlabfinall.telegramBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class Buttons {

    private final InlineKeyboardButton registration_button = new InlineKeyboardButton("/registration");
    private final InlineKeyboardButton help_button = new InlineKeyboardButton("/help");
    private final InlineKeyboardButton yes_button = new InlineKeyboardButton("/yes");
    private final InlineKeyboardButton no_button = new InlineKeyboardButton("/no");

    public InlineKeyboardMarkup customBoardMarkup(String type){
        registration_button.setCallbackData("/registration");
        help_button.setCallbackData("/help");
        yes_button.setCallbackData("/yes");
        no_button.setCallbackData("/no");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        switch (type){
            case "menu":
                List<InlineKeyboardButton> inLineMenu = List.of(registration_button, help_button);
                List<List<InlineKeyboardButton>> inColumnMenu = List.of(inLineMenu);
                inlineKeyboardMarkup.setKeyboard(inColumnMenu);
                break;

            case "reg":
                List<InlineKeyboardButton> inLineRegistrate = List.of(help_button);
                List<List<InlineKeyboardButton>> inColumnRegistrate = List.of(inLineRegistrate);
                inlineKeyboardMarkup.setKeyboard(inColumnRegistrate);
                break;

            case "help":
                List<InlineKeyboardButton> inLineHelp = List.of(registration_button);
                List<List<InlineKeyboardButton>> inColumnHelp = List.of(inLineHelp);
                inlineKeyboardMarkup.setKeyboard(inColumnHelp);
                break;

            case "answer":
                List<InlineKeyboardButton> inLineAnswer = List.of(yes_button, no_button);
                List<List<InlineKeyboardButton>> inColumnAnswer = List.of(inLineAnswer);
                inlineKeyboardMarkup.setKeyboard(inColumnAnswer);
                break;
        }


        return inlineKeyboardMarkup;
    }

}
