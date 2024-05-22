package org.example.perfumecatalog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebResourcesConfig implements WebMvcConfigurer {

    private final String pathToUploadProfilePictures;

    private final ResourcePatternResolver pathResolver;

    public WebResourcesConfig(
            @Qualifier("perfumePictureAbsoluteUploadPath")
            String pathToUploadProfilePictures,
            ResourcePatternResolver pathResolver) {
        this.pathToUploadProfilePictures = pathToUploadProfilePictures;
        this.pathResolver = pathResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/perfume-pictures/**")
                .addResourceLocations(
                        pathResolver.getResource(
                                "file:" + transformIfWindowsStyle(pathToUploadProfilePictures)
                        )
                );
    }

    private String transformIfWindowsStyle(String absolutePath) {
        if (absolutePath.length() > 1 && Character.isLetter(absolutePath.charAt(0)) && absolutePath.charAt(1) == ':') {
            return "///" + absolutePath;
        }
        return absolutePath;
    }

}
