package io.github.zam0k.compras.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QueryGraphQL {

    @QueryMapping
    public String hello() {
        return "Hello GraphQL";
    }

    @QueryMapping
    public int soma(@Argument int a, @Argument int b) {
        return a + b;
    }

}
