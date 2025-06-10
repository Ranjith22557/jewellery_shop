    package com.rtech.jewellery.controller;

    import com.rtech.jewellery.entity.Product;
    import com.rtech.jewellery.entity.Sales;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class PageController {


        @GetMapping("/login")
        public String login(){
            return "login";
        }

        @GetMapping("/home")
        public String Showhome(HttpSession session,Model model){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            model.addAttribute("username",name);
            return "home";
        }

        @GetMapping("/purchase")
        public String Purchase(HttpSession session,Model model){
            model.addAttribute("product",new Product());
            String name = session.getAttribute("userName").toString();
            model.addAttribute("username",name);
            return "purchase";
        }

        @GetMapping("/sales")
        public String Sales(HttpSession session,Model model){
            model.addAttribute("sale",new Sales());
            String name = session.getAttribute("userName").toString();
            model.addAttribute("username",name);
            return "sales";
        }

        @GetMapping("/payment")
        public String Payment(HttpSession session,Model model){
            String name = session.getAttribute("userName").toString();
            model.addAttribute("username",name);
            return "payment";
        }

        @GetMapping("/stock")
        public String Stock(HttpSession session,Model model){
            String name = session.getAttribute("userName").toString();
            model.addAttribute("username",name);
            return "stock";
        }

        @GetMapping("/salesHistory")
        public String salesHistory(HttpSession session,Model model){
            String name = session.getAttribute("userName").toString();
            model.addAttribute("username",name);
            return "salesHistory";
        }

        @GetMapping("/purchaseHistory")
        public String PurchaseHistory(HttpSession session,Model model){
            String name = session.getAttribute("userName").toString();
            model.addAttribute("username",name);
            return "purchaseHistory";
        }

        @GetMapping("/logout")
        public String logout(HttpSession session){
            session.invalidate();
            return "login";
        }
    }
