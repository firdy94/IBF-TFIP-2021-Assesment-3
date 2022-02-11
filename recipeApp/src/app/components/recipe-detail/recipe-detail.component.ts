import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecipeDetail } from 'src/app/models';
import { RecipeService } from 'src/app/recipe.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

  recipe!: RecipeDetail;
  recipeId!: string;

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService) { }

  async ngOnInit() {
    this.recipeId = this.activatedRoute.snapshot.params['recipeId'];
    this.recipe = await this.recipeSvc.getRecipe(this.recipeId);
    console.log(this.recipe);
  }

}
