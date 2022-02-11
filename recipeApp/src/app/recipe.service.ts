import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { RecipeDetail, RecipeMaster } from "./models";

@Injectable()
export class RecipeService {

    constructor(private http: HttpClient) { }

    getAllRecipes(): Promise<RecipeMaster[]> {
        let observ = lastValueFrom(this.http.get<RecipeMaster[]>(`http://localhost:8080/api/recipes`));
        console.log(observ)
        return observ;
    }

    getRecipe(id: string): Promise<RecipeDetail> {
        let observ = lastValueFrom(this.http.get<RecipeDetail>(`http://localhost:8080/api/recipe/${id}`));
        console.log(observ)
        return observ;
    }
    sendRecipe(recipe: RecipeDetail): Promise<any> {
        const headers = new HttpHeaders()
            .set('Content-Type', 'application/json')
        let observ = lastValueFrom(this.http.post<any>('http://localhost:8080/api/recipe', JSON.stringify(recipe),
            { headers: headers }));
        console.log(observ)
        return observ;
    }

}