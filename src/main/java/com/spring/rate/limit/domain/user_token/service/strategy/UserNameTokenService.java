package com.spring.rate.limit.domain.user_token.service.strategy;

import com.spring.rate.limit.domain.exceptions.UnauthorizedException;
import com.spring.rate.limit.domain.user_token.model.enums.UserTokenType;
import com.spring.rate.limit.domain.user_token.service.UserTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserNameTokenService implements UserTokenService {

    private static final String USERNAME = "user_name";

    @Override
    public String getToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(JwtAuthenticationToken.class::cast)
                .map(JwtAuthenticationToken::getTokenAttributes)
                .map(attributes -> String.valueOf(attributes.get(USERNAME)))
                .orElseThrow(() -> new UnauthorizedException("Unable to fetch current user from access token"));
    }

    @Override
    public boolean canHandle(UserTokenType type) {
        return UserTokenType.USERNAME.equals(type);
    }
}
