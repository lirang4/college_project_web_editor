import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { tap, delay } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn = true;

  constructor(private router: Router) { }

  login(): void {
    this.isLoggedIn = true;
    this.router.navigate(['/home']);
  }

  logout(): void {
    this.isLoggedIn = false;
    this.router.navigate(['/home']);
  }
}
