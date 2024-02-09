package com.thinatech.recipes.data;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class RecipesDataRepo {

    static final Map<UUID, Recipe> recipes = new HashMap<>();

    static {
        IntStream.range(1, 13).boxed()
                .map(i -> Recipe.builder().id(UUID.randomUUID()).name("recipe-%d".formatted(i)).build())
                .forEach(recipe -> recipes.put(recipe.getId(), recipe));
    }

    public List<Recipe> list(int page, int size) {
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex+size, recipes.size());
        return recipes.values().stream().toList().subList(startIndex, endIndex);
    }

    public Recipe find(UUID id) {
        return recipes.get(id);
    }

    public int count() {
        return recipes.size();
    }
}
