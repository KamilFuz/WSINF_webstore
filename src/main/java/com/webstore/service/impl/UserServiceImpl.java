package com.webstore.service.impl;

import com.webstore.domain.User;
import com.webstore.domain.UserBilling;
import com.webstore.domain.UserPayment;
import com.webstore.domain.security.PasswordResetToken;
import com.webstore.domain.security.UserRole;
import com.webstore.repository.PasswordResetTokenRepository;
import com.webstore.repository.RoleRepository;
import com.webstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements com.webstore.service.impl.UserService {

    private static final Logger LOG = LoggerFactory.getLogger(com.webstore.service.impl.UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user,final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception{
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("Użytkownik {} istnieje, nic nie zostanie wykonane", user.getUsername());
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            localUser = userRepository.save(user);
        }

        return localUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);
        userPayment.setDefaultPayment(true);
        userBilling.setUserPayment(userPayment);
        user.getUserPaymentList().add(userPayment);
        save(user);
    }
}
