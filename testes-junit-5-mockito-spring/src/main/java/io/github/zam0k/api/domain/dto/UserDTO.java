package io.github.zam0k.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Setter @Getter
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;

    /*
        @JsonIgnore
        json ignore manda o compilador ignorar o getter do atributo,
        o que acaba atrapalhando o mapper
     */
    @JsonProperty(access = WRITE_ONLY)
    private String password;
}
