import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RecipeMaster } from 'src/app/models';
import { RecipeService } from 'src/app/recipe.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {

  recipes!: RecipeMaster[];

  constructor(private recipeSvc: RecipeService, private router: Router) { }

  async ngOnInit() {
    this.recipes = await this.recipeSvc.getAllRecipes();
  }

  go(id: string) {
    console.log(id);
    this.router.navigate(['/recipe', id]);
  }
  onAdd() {
    console.log("clicked!")
    this.router.navigate(['/add']);
  }

}
