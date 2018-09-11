package com.webstore.controller;

import com.webstore.domain.Product;
import com.webstore.domain.User;
import com.webstore.domain.security.PasswordResetToken;
import com.webstore.domain.security.Role;
import com.webstore.domain.security.UserRole;
import com.webstore.service.ProductService;
import com.webstore.service.impl.UserSecurityService;
import com.webstore.service.impl.UserService;
import com.webstore.utility.MailConstructor;
import com.webstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

//    @RequestMapping("/myAccount")
//    public String myAccount() {
//        return "myAccount";
//    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }

    @RequestMapping("/storage")
    public String storage(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "storage";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword (
            HttpServletRequest request,
            @ModelAttribute("email") String email,
//            @ModelAttribute("username") String username,
//            Locale locale,
//            @RequestParam("token") String token,
            Model model) {
//        PasswordResetToken passToken = userService.getPasswordResetToken(token);
        model.addAttribute("classActiveForgetPassword", true);

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExists", true);

            return "myAccount";
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", true);

        return "myAccount";
    }

    @RequestMapping(value="/newUser", method=RequestMethod.POST)
    public String newUserPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model
    ) throws Exception{
        model.addAttribute("classAcitveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);

            return "myAccount";
        }

        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);

            return "myAccount";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        model.addAttribute("emailSent", true);

        return "myAccount";
    }

    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
//        model.addAttribute("userPaymentList", user.getUserPaymentList());
//        model.addAttribute("userShippingList", user.getShippingPaymentList());
//        model.addAttribute("orderList", user.getOrderList());
//        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddress", true);

        List<String> stateList = USConstants.listOfUSStateCode;
        Collection.sort(stateList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("classActiveEdit", true);

        return "myProfile";

    }

    @RequestMapping("/newUser")
    public String newUser (
            Locale locale,
            @RequestParam("token") String token,
            Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if (passToken == null){
            String message = "Invalid Token.";
            model.addAttribute("message",message);
            return "redirect:/badRequest";
        }

        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);

        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    @RequestMapping("/productDetail")
    public String productDetail(
            @PathParam("id") Long id, Model model, Principal principal
    ){
        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

//        Optional<Product> product = productService.findById(id);
        productService.findById(id).ifPresent(product -> model.addAttribute("product", product));
//        model.addAttribute("product", product);

        List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);

        return "productDetail";

    }

}
