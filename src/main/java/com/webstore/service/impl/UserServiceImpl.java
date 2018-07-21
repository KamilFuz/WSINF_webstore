package com.webstore.service.impl;

import com.webstore.domain.User;
import com.webstore.domain.security.PasswordResetToken;
import com.webstore.domain.security.UserRole;
import com.webstore.repository.PasswordResetTokenRepository;
import com.webstore.repository.RoleRepository;
import com.webstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements com.webstore.service.impl.UserService {

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

    public User createUser(User user, Set<UserRole> userRoles) throws Exception{
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            throw new Exception("Użytkownik istnieje, nic nie zostanie wykonane");
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            localUser = userRepository.save(user);
        }

        return localUser;
    }
}
