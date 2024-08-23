package org.example.smartinventory.config;

import org.example.smartinventory.workbench.converter.ProductModelConverter;
import org.example.smartinventory.workbench.converter.StockConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public ProductModelConverter productModelConverter() {
        return new ProductModelConverter();
    }

    @Bean
    public StockConverter stockConverter() {
        return new StockConverter();
    }

}
