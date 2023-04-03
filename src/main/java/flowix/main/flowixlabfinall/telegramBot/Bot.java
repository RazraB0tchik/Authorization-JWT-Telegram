package flowix.main.flowixlabfinall.telegramBot;

import flowix.main.flowixlabfinall.entity.AdminCandidateTelegram;
import flowix.main.flowixlabfinall.exceptions.AdminRegisteredException;
import flowix.main.flowixlabfinall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot implements BotCommands{

    @Autowired
    Buttons buttons;

    @Autowired
    UserService userService;

    Boolean waitAnswer=false;
    Long chatIdWait;

    @Autowired
    AdminCandidateTelegram adminCandidateTelegram;

    BotConfig config;
    public Bot(BotConfig config){
        this.config = config;
        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null)); //отправляем изменения сразу в api бота
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId;
        String username;
        String message;

        if(update.hasMessage()) {
            username = update.getMessage().getFrom().getUserName();
            message = update.getMessage().getText();
            chatId = update.getMessage().getChatId();

            if(!waitAnswer){
                answerUtil(chatId,message,username);
            }
            else if(waitAnswer){
                System.out.println(update.getMessage().getText());
                waitAnswer = false;
                SendMessage sendMessage = new SendMessage();
                if(!(new String(java.util.Base64.getDecoder().decode(config.secretWord)).equals(update.getMessage().getText()))){
                    sendMessage.setText("Oh no, secret word is incorrect :(");
                    sendMessage.setChatId(chatId);
                    sendMessage.setReplyMarkup(buttons.customBoardMarkup("menu"));
                }
                else {
                    chatIdWait = update.getMessage().getChatId();
                    sendMessage.setText("Success! Please, wait for admin to add you.");
                    sendMessage.setChatId(chatId);
                    sendMessage.setReplyMarkup(buttons.customBoardMarkup("reg"));
                    adminCandidateTelegram.setTelegramId(String.valueOf(update.getMessage().getFrom().getId()));
                    adminCandidateTelegram.setChatId(String.valueOf(chatId));
                    adminCandidateTelegram.setUsername(username);

                    SendMessage adminMessage = new SendMessage();
                    adminMessage.setChatId(Long.valueOf(1451501509));
                    adminMessage.setText("Add user with username: " + update.getMessage().getFrom().getUserName() + " to admin panel?");
                    adminMessage.setReplyMarkup(buttons.customBoardMarkup("answer"));

                    try {
                        execute(adminMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        else if (update.hasCallbackQuery()){
            chatId = update.getCallbackQuery().getMessage().getChatId();
            username = update.getCallbackQuery().getFrom().getUserName();
            message = update.getCallbackQuery().getData();

            if(!((update.getCallbackQuery().getData()).equals("/registration")) & waitAnswer) {
                waitAnswer = false;
                answerUtil(chatId,message,username);
            }
            else if ((update.getCallbackQuery().getData()).equals("/registration") & !waitAnswer) {
                waitAnswer = true;
                answerUtil(chatId,message,username);
            }
            else{
                answerUtil(chatId,message,username);
            }
    }
    }

    private void answerUtil(Long chatId, String message, String username){
        SendMessage sendMessage = new SendMessage();
        switch (message){
            case  "/start":
                sendMessage.setChatId(chatId);
                sendMessage.setText("Welcome to the admin panel bot!");
                sendMessage.setReplyMarkup(buttons.customBoardMarkup("menu"));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/help":
                sendMessage.setChatId(chatId);
                sendMessage.setText(help_text);
                sendMessage.setReplyMarkup(buttons.customBoardMarkup("help"));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/registration":
                sendMessage.setChatId(chatId);
                sendMessage.setText(start_registration);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "/yes":
                System.out.println(message);
                try {
                    userService.saveAdmin(adminCandidateTelegram);
                } catch (AdminRegisteredException e) {
                    e.printStackTrace();
                }
                sendMessage.setText("You added in adminPanel!");
                    sendMessage.setChatId(chatIdWait);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "/no":
                sendMessage.setText("Admin denied registration.");
                sendMessage.setChatId(chatIdWait);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            default: break;
        }
    }

    @Override
    public String getBotUsername() {
        return config.botName;
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

}
