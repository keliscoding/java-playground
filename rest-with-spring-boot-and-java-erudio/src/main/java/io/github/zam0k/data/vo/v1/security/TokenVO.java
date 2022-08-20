package io.github.zam0k.data.vo.v1.security;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TokenVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4283786362700905210L;

    private String username;
    private Boolean authenticated;
    private LocalDate created;
    private LocalDate expiration;
    private String accessToken;
    private String refreshToken;

    public TokenVO(String username, Boolean authenticated,
                   LocalDate created, LocalDate expiration,
                   String accessToken, String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenVO tokenVO = (TokenVO) o;
        return Objects.equals(getUsername(), tokenVO.getUsername()) && Objects.equals(getAuthenticated(), tokenVO.getAuthenticated()) && Objects.equals(getCreated(), tokenVO.getCreated()) && Objects.equals(getExpiration(), tokenVO.getExpiration()) && Objects.equals(getAccessToken(), tokenVO.getAccessToken()) && Objects.equals(getRefreshToken(), tokenVO.getRefreshToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getAuthenticated(), getCreated(), getExpiration(), getAccessToken(), getRefreshToken());
    }
}
