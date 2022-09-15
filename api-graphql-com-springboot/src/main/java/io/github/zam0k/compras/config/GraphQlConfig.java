package io.github.zam0k.compras.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static graphql.scalars.ExtendedScalars.GraphQLBigDecimal;

@Configuration
public class GraphQlConfig {

    @Bean
    public void bigDecimalScalar() {
        RuntimeWiring.newRuntimeWiring().scalar(GraphQLBigDecimal);
    }
}
