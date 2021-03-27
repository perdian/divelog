package de.perdian.divelog;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DivelogApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DivelogApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
