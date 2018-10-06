package com.webstore.utility;

import com.webstore.domain.Order;
import com.webstore.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

//import org.omg.CORBA.Environment;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    @Autowired
    private TemplateEngine templateEngine;

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

    public MimeMessagePreparator constructOrderConfirmationEmail (User user, Order order, Locale locale) {
        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("user", user);
        context.setVariable("cartItemList", order.getCartItemList());
        String text = templateEngine.process("orderConfirmationEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getEmail());
                email.setSubject("Potwierdzenie złożenia zamówienia - "+order.getId());
                email.setText(text, true);
                email.setFrom(new InternetAddress("bezel193@o2.pl"));
            }
        };

        return messagePreparator;
    }
}
