import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  registrationForm: FormGroup;
  isLoading: boolean;

  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit() {
    this.registrationForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    });

    this.isLoading = false;
  }

  onSubmit(): void {
    this.isLoading = true;
    this.http.post('/users', this.registrationForm.value)
      .subscribe((res) => this.isLoading = false);
  }
}
