package io.github.zam0k.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    //isso aqui serve pro spring gerenciar a instancia pra vc
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
