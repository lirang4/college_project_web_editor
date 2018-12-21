import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  isLoading: boolean;

  constructor() { }

  ngOnInit() {
    this.isLoading = false;
  }

  onRegistration(): void {
    this.isLoading = true;

    setTimeout(() => this.isLoading = false, 5000);
  }

}
