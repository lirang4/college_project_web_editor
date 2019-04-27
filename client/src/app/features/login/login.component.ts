import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { AuthService } from '@core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading: boolean;
  isErrorAccrued: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.isLoading = false;
    this.isErrorAccrued = false;
  }

  onSubmit(): void {
    this.isLoading = true;
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe(res => {
      this.isLoading = false;
      this.isErrorAccrued = !res;
    });
  }
}
