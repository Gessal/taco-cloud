package com.springinaction.tacocloud.controller;

import com.springinaction.tacocloud.domain.User;
import com.springinaction.tacocloud.repository.OrderRepository;
import com.springinaction.tacocloud.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepo;

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("fullname", user.getFullname());
        model.addAttribute("street", user.getStreet());
        model.addAttribute("city", user.getCity());
        model.addAttribute("state", user.getState());
        model.addAttribute("zip", user.getZip());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        log.info("Order submitted: " + order);
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
