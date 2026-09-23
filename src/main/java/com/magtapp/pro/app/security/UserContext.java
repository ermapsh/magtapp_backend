package com.magtapp.pro.app.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.util.UUID;

@Component
@Getter
@Setter
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS) // it will create later on when needed
public class UserContext {
    private UUID userId;

    public void clear() {
        userId = null;
    }
}
