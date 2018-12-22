import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(
    private router: Router,
    private authService: AuthService) { }

  ngOnInit() { }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }

  onLogout(): void {
    this.authService.logout();
  }

  navigage(naviageTo: string): void {
    this.router.navigate([`/${naviageTo}`]);
  }
}
