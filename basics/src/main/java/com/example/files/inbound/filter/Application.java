package com.example.files.inbound.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.handler.GenericHandler;

import java.io.File;

@Log4j2
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    IntegrationFlow fileFlow(@Value("file://${user.home}/Desktop/in/") File inDir) {
        var messageSourceSpec = Files.inboundAdapter(inDir)
                .autoCreateDirectory(true)
                .useWatchService(true);

        return IntegrationFlows
                .from(messageSourceSpec, p -> p.poller(ps -> ps.fixedRate(1000 * 1)))
                .handle((GenericHandler<File>) (file, messageHeaders) -> {
                    System.out.println("new file: " + file.getAbsolutePath() + '.');
                    return null;
                })
                .get();

    }
}
