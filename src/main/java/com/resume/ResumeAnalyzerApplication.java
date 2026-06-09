package com.resume;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResumeAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                ResumeAnalyzerApplication.class,
                args
        );
    }

    @Bean
    ApplicationRunner openBrowser() {

        return args -> {

            try {

                Runtime.getRuntime().exec(
                    "cmd /c start http://localhost:8080"
                );

            } catch (Exception e) {

                e.printStackTrace();
            }
        };
    }
}