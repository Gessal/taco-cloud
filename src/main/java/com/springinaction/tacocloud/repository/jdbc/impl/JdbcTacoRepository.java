package com.springinaction.tacocloud.repository.jdbc.impl;

import com.springinaction.tacocloud.domain.Ingredient;
import com.springinaction.tacocloud.domain.Taco;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

//@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository implements com.springinaction.tacocloud.repository.jdbc.JdbcTacoRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Taco save(Taco taco) {
        Long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient.getId(), tacoId);
        }
        return taco;
    }

    private Long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());


        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into Taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(taco.getTacoName(), new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void saveIngredientToTaco(String ingredientId, Long tacoId) {
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredientId
        );
    }
}
