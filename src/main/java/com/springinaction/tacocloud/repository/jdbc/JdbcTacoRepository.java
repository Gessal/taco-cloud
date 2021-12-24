package com.springinaction.tacocloud.repository.jdbc;

import com.springinaction.tacocloud.domain.Taco;

public interface JdbcTacoRepository {
    Taco save(Taco taco);
    void saveIngredientToTaco(String ingredient, Long tacoId);
}
