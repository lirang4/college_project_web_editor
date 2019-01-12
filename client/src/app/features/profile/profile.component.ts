import { Component, OnInit } from '@angular/core';
import { UserService } from '@app/core/services/user.service';
import { User } from '@app/shared/models/User';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;

  constructor(
    private userService: UserService) { }

  ngOnInit() {
    this.user = this.userService.User;
  }
}
