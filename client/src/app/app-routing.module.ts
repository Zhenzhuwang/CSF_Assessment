import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovieReviewsListComponent } from './components/movie-reviews-list/movie-reviews-list.component';
import { SearchReviewComponent } from './components/search-review/search-review.component';
import { PostCommentComponent} from './components/post-comment/post-comment.component';
const routes: Routes = [
  {path: '', component: SearchReviewComponent},
  {path:'list/', component: MovieReviewsListComponent},
  {path:'comment', component: PostCommentComponent},
  { path: '**', redirectTo: '', pathMatch: 'full'} 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
