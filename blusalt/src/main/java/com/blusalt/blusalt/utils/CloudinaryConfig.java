package com.blusalt.blusalt.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Value("${cloudinaryName}")
    private String cloudName;

    @Value("${cloudinaryApiKey}")
    private String apiKey;

    @Value("${cloudinaryApiSecret}")
    private String apiSecret;

//    private String cloudName = "dzuygn6kw";
////    private String apiKey = configuration.getString("cloudinary.apikey");
//    private String apiKey = "236147183542475";
////    private String apiSecret = configuration.getString("cloudinary.apikey");
//    private String apiSecret = "ejpiqwFaGKETpzDQXFAHN0ubs3M";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }




}
