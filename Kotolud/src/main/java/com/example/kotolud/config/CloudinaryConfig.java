package com.example.kotolud.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        log.info("Creating Cloudinary bean...");

        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret,
                    "secure", true
            ));

            System.out.println("Cloud Name: " + cloudName);
            System.out.println("API Key: " + apiKey);
            System.out.println("API Secret: " + apiSecret.substring(0, Math.min(5, apiSecret.length())) + "...");

            log.info("✅ Cloudinary bean created successfully");
            return cloudinary;

        } catch (Exception e) {
            log.error("❌ Failed to create Cloudinary bean: {}", e.getMessage(), e);
            throw new RuntimeException("Cloudinary configuration failed", e);
        }
    }
}