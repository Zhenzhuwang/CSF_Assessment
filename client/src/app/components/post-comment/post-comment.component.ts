import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MovieReviewsService } from 'src/app/services/movie-reviews.service';

@Component({
  selector: 'app-post-comment',
  templateUrl: './post-comment.component.html',
  styleUrls: ['./post-comment.component.css']
})
export class PostCommentComponent implements OnInit, OnDestroy{
  form!: FormGroup;
  queryParams$! :  Subscription;
  movieParam!: any;
  movieName! : string;
  Id!: string;

  constructor(private activatedRoute: ActivatedRoute,  private formBuilder: FormBuilder,
    private movieReviewsSvc: MovieReviewsService, private router: Router){

  }

  ngOnInit(): void {
    this.form = this.createForm();
    this.queryParams$ = this.activatedRoute.queryParams.subscribe(
      (queryParams) => {
        this.movieParam = queryParams['movieParam'].split('|');
        console.log(this.movieParam[0]);
        console.log(this.movieParam[1]);
        this.movieName = this.movieParam[0];
        this.Id = this.movieParam[1];
      }
    );

  }

  saveComment(){
    const commentFormVal = this.form?.value['comment'];
    const c = {} as Comment;
    // c.comment = commentFormVal;
    // c.id = this.Id;

    this.movieReviewsSvc.saveComment(c);
    this.router.navigate(['/list', this.Id]);
  }

  back(){
    this.router.navigate(['/list', this.Id]);
  }

  ngOnDestroy(): void{
    this.queryParams$.unsubscribe();
  }

  private createForm(): FormGroup{
    return this.formBuilder.group({
      comment : this.formBuilder.control(''),  
    })
  }

}
