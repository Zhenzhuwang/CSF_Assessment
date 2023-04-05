import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-review',
  templateUrl: './search-review.component.html',
  styleUrls: ['./search-review.component.css']
})
export class SearchReviewComponent implements OnInit, OnDestroy {
  form!: FormGroup;
  movieName?: string;
  
  searchButtonDisabled = true;

  constructor (private formBuilder: FormBuilder, private router: Router) {
    
  }

  ngOnInit(): void {
      this.form = this.createForm();
  }

  ngOnDestroy(): void {
      
  }

  search() {
    const movieName = this.form?.value['movieName'].trim();
    const isInvalid = movieName.length < 2 || this.formInvalid();
    this.searchButtonDisabled = isInvalid; 
    this.router.navigate(['/list', movieName]);
  }


  private createForm() {
    return this.formBuilder.group({
      movieName: this.formBuilder.control<string>('', [Validators.required])
    });
  }

  formInvalid(): boolean {
		return this.form.invalid;
	}

}
