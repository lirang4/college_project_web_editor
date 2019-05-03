import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

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

  login(userName: string, password: string): Observable<boolean> {
    const result = this.userService.loadUser(userName, password);
    result.subscribe(res => {
      if (res) {
        this.isLoggedIn = true;
        this.navigateNext();
      }
    });
    return result;
  }

  logout(): void {
    this.isLoggedIn = false;
    this.nextUrl = '/home';
    this.userService.logout();

    this.navigateNext();
  }

  saveUrl(url: string): void {
    this.nextUrl = url;
  }

  private navigateNext(): void {
    this.router.navigate([this.nextUrl]);
  }
}
