package org.example.perfumecatalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:application.yml")
public class PerfumePicturesConfig {


    @Value("${application.perfume-pictures.upload-path}")
    private String pathToPicturesStorage;

    @Value("#{'${application.perfume-pictures.allowed-extensions}'.split(',')}")
    private List<String> allowedPictureExtensions;

    @Bean
    public String perfumePictureAbsoluteUploadPath() {
        return pathToPicturesStorage;
    }

    @Bean
    public List<String> allowedPictureExtensions() {
        return allowedPictureExtensions;
    }

}
