package com.webstore.service.impl;

import com.webstore.domain.User;
import com.webstore.domain.security.PasswordResetToken;

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);
}
