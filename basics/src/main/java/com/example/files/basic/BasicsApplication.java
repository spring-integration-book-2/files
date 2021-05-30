package com.example.files.basic;

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

/**
 * this demonstrates a basic, no-frills inbound file adapter
 */
@Log4j2
@SpringBootApplication
public class BasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicsApplication.class, args);
    }

    @Bean
    IntegrationFlow fileFlow(@Value("file://${user.home}/Desktop/in/") File inDir) {
        return IntegrationFlows
                .from(Files.inboundAdapter(inDir).autoCreateDirectory(true))
                .handle((GenericHandler<File>) (file, messageHeaders) -> {
                    log.info("new file: " + file.getAbsolutePath());
                    return null;
                })
                .get();

    }
}
