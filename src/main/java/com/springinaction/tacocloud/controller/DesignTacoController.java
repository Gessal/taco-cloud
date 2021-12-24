package com.springinaction.tacocloud.controller;

import com.springinaction.tacocloud.repository.IngredientRepository;
import com.springinaction.tacocloud.repository.TacoRepository;
import com.springinaction.tacocloud.domain.Ingredient;
import com.springinaction.tacocloud.domain.Order;
import com.springinaction.tacocloud.domain.Taco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes(value = {"order"})
@RequiredArgsConstructor
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type: types) {
            model.addAttribute(
                    type.toString().toLowerCase(),
                    ingredients.stream()
                            .filter(ingredient -> ingredient.getType() == type)
                            .collect(Collectors.toList()));
        }
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, @ModelAttribute("order") Order order, Errors errors, Model model) {
        if (errors.hasErrors()) {
            List<Ingredient> ingredients = new ArrayList<>();
            ingredientRepo.findAll().forEach(ingredients::add);
            Ingredient.Type[] types = Ingredient.Type.values();
            for (Ingredient.Type type: types) {
                model.addAttribute(type.toString().toLowerCase(), ingredients.stream().filter(ingredient -> ingredient.getType() == type).collect(Collectors.toList()));
            }
            model.addAttribute("design", new Taco());
            return "design";
        }
        log.info("Processing design: " + taco);
        Taco saved = tacoRepo.save(taco);
        order.getTacos().add(saved);
        return "redirect:/orders/current";
    }
}
