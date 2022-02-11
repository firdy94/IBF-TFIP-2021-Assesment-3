package ibf2021.assessment.csf.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController {

	@Autowired
	RecipeService recipeSvc;

	@GetMapping()
	public ResponseEntity<String> getRecipes() {
		List<Recipe> recipes = recipeSvc.getAllRecipes();
		JsonArrayBuilder jsonArrayBuild = Json.createArrayBuilder();

		for (Recipe recipe : recipes) {
			JsonObjectBuilder jsonObjBuild = Json.createObjectBuilder();
			JsonObject jsonObj = jsonObjBuild.add("name", recipe.getTitle()).add("id", recipe.getId()).build();
			jsonArrayBuild.add(jsonObj);
		}
		String jsonResp = jsonArrayBuild.build().toString();
		return new ResponseEntity<>(jsonResp, HttpStatus.OK);
	}

}
