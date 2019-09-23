package com.chris.booking.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Booking Management Application.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ConfigDetailsProperties {
}
