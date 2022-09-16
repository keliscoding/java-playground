package io.github.zam0k.compras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import static graphql.scalars.ExtendedScalars.*;

@Configuration
public class GraphQlConfig {
    @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(GraphQLBigDecimal).scalar(DateTime).scalar(PositiveInt);
    }
}
