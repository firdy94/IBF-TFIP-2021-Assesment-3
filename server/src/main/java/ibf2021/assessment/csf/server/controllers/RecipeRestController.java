package ibf2021.assessment.csf.server.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeRestController {

	@Autowired
	RecipeService recipeSvc;

	@GetMapping("{id}")
	public ResponseEntity<String> getRecipes(@PathVariable String id) {
		Optional<Recipe> recipeOpt = recipeSvc.getRecipeById(id);
		if (recipeOpt.get() != null) {
			Recipe recipe = recipeOpt.get();
			JsonObjectBuilder jsonObjBuild = Json.createObjectBuilder();
			JsonObjectBuilder jsonObjAdd = jsonObjBuild
					.add("title", recipe.getTitle())
					.add("id", recipe.getId())
					.add("image", recipe.getImage())
					.add("instruction", recipe.getInstruction());
			JsonArrayBuilder listArray = Json.createArrayBuilder();
			for (String ingredient : recipe.getIngredients()) {
				listArray.add(ingredient);
			}
			JsonArray listIngredients = listArray.build();
			JsonObject jsonObj = jsonObjAdd.add("ingredients", listIngredients).build();
			String jsonResp = jsonObj.toString();
			return new ResponseEntity<>(jsonResp, HttpStatus.OK);
		}

		String jsonResp = JsonObject.EMPTY_JSON_OBJECT.toString();
		return new ResponseEntity<>(jsonResp, HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> inputRecipe(@RequestBody String jsonResp) {
		System.out.println(jsonResp);
		JsonReader jsonReader = Json.createReader(new StringReader(jsonResp));
		JsonObject inputObject = jsonReader.readObject();
		Recipe recipe = new Recipe();
		recipeSvc.addRecipe(recipe);
		recipe.setImage(inputObject.getString("image"));
		recipe.setInstruction(inputObject.getString("instruction"));
		recipe.setTitle(inputObject.getString("title"));
		JsonArray ingredientsArray = inputObject.getJsonArray("ingredients");
		for (JsonValue ingredient : ingredientsArray) {
			recipe.addIngredient(ingredient.toString());
		}

		recipeSvc.addRecipe(recipe);
		return new ResponseEntity<>("Recipe created", HttpStatus.CREATED);
	}
}
