package com.springinaction.tacocloud.repository.jdbc;

import com.springinaction.tacocloud.domain.Ingredient;

public interface JdbcIngredientRepository{
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
