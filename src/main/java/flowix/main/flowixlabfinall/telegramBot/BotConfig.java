package flowix.main.flowixlabfinall.telegramBot;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@Data
public class BotConfig {

    @Value("${telegram.bot.name}")
    String botName;

    @Value("${telegram.bot.token}")
    String botToken;

    @Value("${telegram.bot.secretWord}")
    String secretWord;

    @PostConstruct
    public void init(){
        this.secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }
}
