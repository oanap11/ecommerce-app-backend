package com.ecommerce.store.config;

import com.ecommerce.store.entity.Product;
import com.ecommerce.store.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        configureExposure(config, Product.class, unsupportedActions);
        configureExposure(config, ProductCategory.class, unsupportedActions);
    }

    private void configureExposure(RepositoryRestConfiguration config, Class<?> entityType, HttpMethod... unsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(entityType)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
    }
}
