package com.spring.rate.limit.domain.user_token.service.strategy;

import com.spring.rate.limit.domain.exceptions.ServerErrorException;
import com.spring.rate.limit.domain.user_token.model.enums.UserTokenType;
import com.spring.rate.limit.domain.user_token.service.UserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static com.spring.rate.limit.domain.user_token.model.enums.UserTokenType.IP;

@Service
public class IPTokenService implements UserTokenService {
    @Override
    public String getToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            throw new ServerErrorException("Unable to reach the server");
        }

        HttpServletRequest request = requestAttributes.getRequest();

        return request.getRemoteAddr();
    }

    @Override
    public boolean canHandle(UserTokenType type) {
        return IP.equals(type);
    }
}
