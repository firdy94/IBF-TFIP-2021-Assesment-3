import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RecipeDetail } from 'src/app/models';
import { RecipeService } from 'src/app/recipe.service';


@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {

  formGroup!: FormGroup;
  formArray!: FormArray;

  recipe!: RecipeDetail;

  constructor(private fb: FormBuilder, private recipeSvc: RecipeService, private router: Router) { }

  ngOnInit(): void {
    this.formArray = this.fb.array([]);
    this.formGroup = this.fb.group({
      title: this.fb.control('', [Validators.minLength(3), Validators.required]),
      instruction: this.fb.control('', [Validators.minLength(3), Validators.required]),
      image: this.fb.control('', [Validators.required]),
      ingredients: this.formArray,

    })
  }

  addIngredient() {
    // const ingredientGroup = this.fb.group({
    //   ingred: ,
    // })
    this.formArray.push(this.fb.control('', [Validators.minLength(3), Validators.required]));
  }

  deleteIngredient(i: number) {
    this.formArray.removeAt(i);
  }

  async onSubmit() {
    this.recipe = this.formGroup.value as RecipeDetail;
    let obj = this.recipe.ingredients
    console.log(this.recipe);
    console.log(obj.values)
    await this.recipeSvc.sendRecipe(this.recipe);
    this.formGroup.reset();
    this.router.navigate(['/']);


  }

}
