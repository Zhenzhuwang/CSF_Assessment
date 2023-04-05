import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Movie } from 'src/app/models/movies';
import { MovieReviewsService } from 'src/app/services/movie-reviews.service';

@Component({
  selector: 'app-movie-reviews-list',
  templateUrl: './movie-reviews-list.component.html',
  styleUrls: ['./movie-reviews-list.component.css']
})
export class MovieReviewsListComponent implements OnInit, OnDestroy {
  movieName = "";
  param$! : Subscription;
  movies!: Movie[];
  currentIndex!: number;

  constructor(private activatedRoute: ActivatedRoute, private movieReviewsSvc: MovieReviewsService, private router: Router) {
    
  }
  
  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe( async (params)=> {
      this.movieName = params['movieName'];
      const l = await this.movieReviewsSvc.getMovies(this.movieName, 0, 20);
      this.currentIndex = 1;
      if (l === undefined || l.length == 0) {
        this.router.navigate(['/'])
      } else {
        this.movies = l;
      }
    });
  }
  
  ngOnDestroy(): void {
    this.param$.unsubscribe();
    }
  

  async back() {
    if(this.currentIndex > 0){
      this.currentIndex = this.currentIndex + 20;
      const l = await this.movieReviewsSvc
            .getMovies(this.movieName, this.currentIndex, 20);
      this.movies = l;
    }
  }

  async comment() {
    this.currentIndex = this.currentIndex + 20;
    const l = await this.movieReviewsSvc.getMovies(this.movieName, this.currentIndex, 20);
    this.movies = l;
  }


}

