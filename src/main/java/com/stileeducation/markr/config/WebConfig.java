package com.stileeducation.markr.config;

import com.stileeducation.markr.converter.XmlMarkrMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        // Register custom message converter to support text/xml+mark content type
        converters.add(new XmlMarkrMessageConverter());
    }
}
