import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn = false;

  private nextUrl: string;

  constructor(private router: Router) {
    this.nextUrl = '/home';
  }

  login(): void {
    this.isLoggedIn = true;

    this.navigateNext();
  }

  logout(): void {
    this.isLoggedIn = false;
    this.nextUrl = '/home';

    this.navigateNext();
  }

  saveUrl(url: string): void {
    this.nextUrl = url;
  }

  private navigateNext(): void {
    this.router.navigate([this.nextUrl]);
  }
}
