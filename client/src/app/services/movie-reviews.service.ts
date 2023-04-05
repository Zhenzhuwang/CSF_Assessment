import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Movie } from '../models/movies';

@Injectable({
  providedIn: 'root'
})
export class MovieReviewsService {
  
  private API_URI: string = "/api/search";

  constructor(private httpClient: HttpClient) { }

  getMovies(movieName: string, offset: number, limit: number): Promise<any>{
    const params = new HttpParams()
          .set("display_title", movieName)
          .set("limit", limit)
          .set("offset", offset);
          const headers = new HttpHeaders().set("Content-Type", "application/json; charset=UTF-8");
        
          return lastValueFrom(this.httpClient.get<Movie[]>(this.API_URI, {params: params, headers: headers}));
  }

  getMoviesDetails(id: string): Promise<any>{
    const headers = new HttpHeaders().set("Content-Type", "application/json; charset=UTF-8");
    return lastValueFrom(this.httpClient.get<Movie[]>(this.API_URI, {headers: headers}));
  }

  saveComment(c:Comment): Promise<any>{
    const headers = new HttpHeaders().set("Content-Type", "application/json; charset=UTF-8");
    const body = JSON.stringify(c);
    return lastValueFrom(this.httpClient.post<Comment>(this.API_URI + "/" + body, {headers: headers}));
  }

  getMovieComments(id : string): Promise<Comment[]>{
    const headers = new HttpHeaders().set("Content-Type", "application/json; charset=UTF-8"); 
    return lastValueFrom(this.httpClient.get<Comment[]>(this.API_URI + "/comments/" + id, {headers: headers}));
  }

}
