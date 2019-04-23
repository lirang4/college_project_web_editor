import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Injectable()
export class AuthService {
  isLoggedIn = false;

  private nextUrl: string;

  constructor(
    private router: Router,
    private userService: UserService) {

    this.nextUrl = '/home';
  }

  login(userName: string, password: string): void {
    this.userService.loadUser(userName, password).subscribe(res => {
      if (res) {
        this.isLoggedIn = true;
        this.navigateNext();
      }
    });
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
