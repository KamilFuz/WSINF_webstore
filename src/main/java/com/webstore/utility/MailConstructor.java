package com.webstore.utility;

import com.webstore.domain.User;
//import org.omg.CORBA.Environment;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {
    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user, String password
    ) {
        String url = contextPath + "/newUser?token=" + token;
        String message = "\nProszę kliknąć w ten link w celu potwierdzenia adresu email. Twoje hasło to \n" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Webstore - Nowy użytkownik");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}
