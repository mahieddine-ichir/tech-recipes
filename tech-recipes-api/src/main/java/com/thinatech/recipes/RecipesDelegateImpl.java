package com.thinatech.recipes;

import com.thinatech.recipes.api.Page;
import com.thinatech.recipes.api.RecipeResponse;
import com.thinatech.recipes.api.RecipesApiDelegate;
import com.thinatech.recipes.api.RecipesResponse;
import com.thinatech.recipes.data.RecipesDataRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecipesDelegateImpl implements RecipesApiDelegate {

    private final RecipesDataRepo recipesDataRepo;

    private final RecipeDataMapper recipeDataMapper;

    @Override
    public ResponseEntity<RecipesResponse> listRecipes(Integer page, Integer size) {

        Collection<RecipeResponse> responses = recipeDataMapper.toResponse(recipesDataRepo.list(page, size));

        Page page1 = new Page();
        int count = recipesDataRepo.count();
        if (hasPrevious(page, size, count)) {
            UriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .queryParam("size", size);
            page1.setPrevious(uriComponentsBuilder.queryParam("page", page-1).toUriString());
        }
        if (hasNext(page, size, count)) {
            UriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .queryParam("size", size);
            page1.setNext(uriComponentsBuilder.queryParam("page", page+1).toUriString());
        }
        page1.setTotal(count);

        RecipesResponse recipesResponse = new RecipesResponse();
        recipesResponse.setRecipes(responses.stream().toList());
        recipesResponse.setPage(page1);
        return ResponseEntity.ok(recipesResponse);
    }

    boolean hasNext(int page, int size, int total) {
        int nbPages = (total / size) + 1;
        return page < nbPages;
    }

    boolean hasPrevious(int page, int size, int total) {
        return page > 1;
    }

    @Override
    public ResponseEntity<RecipeResponse> getRecipe(String id) {
        Optional<RecipeResponse> recipeResponseResponseEntity = Optional.of(id)
                .map(UUID::fromString)
                .map(recipesDataRepo::find)
                .map(recipeDataMapper::toResponse);

        return ResponseEntity.of(recipeResponseResponseEntity);
    }

    @Override
    public ResponseEntity<RecipeResponse> createRecipe(RecipeResponse recipe) {
        return RecipesApiDelegate.super.createRecipe(recipe);
    }
}
