package com.webstore.service.impl;

import com.webstore.domain.User;
import com.webstore.domain.UserBilling;
import com.webstore.domain.UserPayment;
import com.webstore.domain.UserShipping;
import com.webstore.domain.security.PasswordResetToken;
import com.webstore.domain.security.UserRole;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    Optional<User> findById(Long id);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

    void updateUserShipping(UserShipping userShipping, User user);

    void setUserDefaultPayment(Long userPaymentId, User user);

    void setUserDefaultShipping(Long userShippingId, User user);
}
