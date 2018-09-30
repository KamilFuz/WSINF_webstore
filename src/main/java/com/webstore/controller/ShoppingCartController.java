package com.webstore.controller;

import com.webstore.domain.CartItem;
import com.webstore.domain.Product;
import com.webstore.domain.ShoppingCart;
import com.webstore.domain.User;
import com.webstore.service.CartItemService;
import com.webstore.service.ProductService;
import com.webstore.service.ShoppingCartService;
import com.webstore.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
//        ---TUTAJ SIĘ KRZACZY, JUŻ NIE---//
        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping("/addItem")
    public String addItem(
            @ModelAttribute("product") Product product,
            @ModelAttribute("qty") String qty,
            Model model, Principal principal
    ) {
        User user = userService.findByUsername(principal.getName());
        product = productService.findById(product.getId()).orElse(null);

        if (Integer.parseInt(qty) > product.getInStockNumber()) {
            model.addAttribute("notEnoughStock", true);
            return "forward:/productDetail?id="+product.getId();
        }

        CartItem cartItem = cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty));
        model.addAttribute("addProductSuccess", true);

        return "forward:/productDetail?id="+product.getId();
    }

    @RequestMapping("/updateCartItem")
    public String updateShoppingCart(
            @ModelAttribute("id") Long cartItemId,
            @ModelAttribute("qty") int qty
    ) {
        CartItem cartItem = cartItemService.findById(cartItemId)
                .orElse(null);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);

        return "forward:/shoppingCart/cart";
    }

    @RequestMapping("/removeItem")
    public String removeItem(@RequestParam("id") Long id) {
        cartItemService.removeCartItem(cartItemService.findById(id).orElse(null));

        return "forward:/shoppingCart/cart";
    }

}