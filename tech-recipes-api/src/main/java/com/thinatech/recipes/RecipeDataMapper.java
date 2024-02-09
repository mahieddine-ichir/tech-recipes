package com.thinatech.recipes;

import com.thinatech.recipes.api.RecipeResponse;
import com.thinatech.recipes.data.Recipe;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface RecipeDataMapper {

    RecipeResponse toResponse(Recipe recipe);
    Collection<RecipeResponse> toResponse(Collection<Recipe> recipe);
}
