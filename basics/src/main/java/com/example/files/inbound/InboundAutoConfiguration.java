package com.example.files.inbound;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;
import org.springframework.util.PropertyPlaceholderHelper;

import java.net.URI;

/**
 * initialize the inbound directory
 */
@Log4j2
@Configuration
class InboundAutoConfiguration {

    static final String IN_DIR = "${user.home}/Desktop/in/";
    public static final String IN = "file://" + IN_DIR;
    private final PropertyPlaceholderHelper propertyPlaceholderHelper
            = new PropertyPlaceholderHelper("${", "}");

    @Bean
    ApplicationRunner initializeTheInboundDirectory() {
        return args -> {
            var uriOfEvaluatedPath = URI.create(
                    this.propertyPlaceholderHelper.replacePlaceholders(IN, System.getProperties()));
            var path = uriOfEvaluatedPath.getPath();
            var cp = new FileSystemResource(path);
            Assert.state(cp.exists() || cp.getFile().mkdirs(), () -> "the resource, \"" + path + ",\" must exist.");
        };
    }
}
