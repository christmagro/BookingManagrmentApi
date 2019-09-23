package com.chris.booking.api;

import com.chris.booking.api.config.DefaultProfileUtil;
import com.chris.booking.api.config.ConfigDetailsProperties;
import com.chris.booking.api.constants.ApplicationConstants;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties({ConfigDetailsProperties.class, LiquibaseProperties.class})
public class BookingManagementApplication implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(BookingManagementApplication.class);

    private final Environment env;

    private static String HEALTH_URL;

    @Value("${management.endpoints.web.base-path}")
    private String propertiesHealthUrl;


    public BookingManagementApplication(Environment env) {
        this.env = env;
    }


    /**
     * Initializes BookingManagementApplication.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_STAGING)) {
            log.error("You have misconfigured your application! It should not " +
                    "run with both the 'dev' and 'staging' profiles at the same time.");
        }

        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION) && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_STAGING)) {
            log.error("You have misconfigured your application! It should not " +
                    "run with both the 'dev' and 'staging' profiles at the same time.");
        }

        HEALTH_URL = propertiesHealthUrl;


    }


    private static void logApplicationStartup(Environment env) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        String protocol = "http";
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Swagger: \t{}\n\t" +
                        "Health:  \t{}://{}:{}{}/health\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_SWAGGER) ? protocol + "://" + hostAddress + ":" + serverPort + contextPath + "swagger-ui.html" : "No Swagger Deployed",
                protocol,
                hostAddress,
                serverPort,
                HEALTH_URL,
                env.getActiveProfiles());
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BookingManagementApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

}
