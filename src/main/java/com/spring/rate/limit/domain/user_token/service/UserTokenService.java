package com.spring.rate.limit.domain.user_token.service;

import com.spring.rate.limit.domain.user_token.model.enums.UserTokenType;

public interface UserTokenService {

    String getToken();

    boolean canHandle(UserTokenType type);
}
