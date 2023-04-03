package flowix.main.flowixlabfinall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlowixLabFinallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowixLabFinallApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://tangy-plums-juggle-217-150-73-67.loca.lt/")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("*");

            }
        };
    }

}
