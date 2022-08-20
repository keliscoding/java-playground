package io.github.zam0k.controllers;

import io.github.zam0k.data.vo.v1.security.AccountCredentialsVO;
import io.github.zam0k.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Endpoint")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        if (checkIfParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request.");

        ResponseEntity token = authServices.signin(data);

        if(token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request.");

        return token;
    }

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable(value = "username") String username,
                                       @RequestHeader(value = "Authorization") String refreshToken) {

        //dava pra ter colocado essa logica aqui dentro do service
        if (checkIfParamIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request.");
        ResponseEntity token = authServices.refreshToken(username, refreshToken);

        if(token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request.");

        return token;
    }

    private boolean checkIfParamIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() ||
                username == null || username.isBlank();
    }


    private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
