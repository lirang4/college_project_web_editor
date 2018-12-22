import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.isLoading = false;
  }

  onSubmit(): void {
    this.isLoading = true;

    setTimeout(() => {
      this.isLoading = false;
      this.authService.login();
    }, 3000);
  }
}
