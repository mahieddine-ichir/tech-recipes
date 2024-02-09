package com.thinatech.recipes.data;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Recipe {

    private UUID id;

    private String name;
}
