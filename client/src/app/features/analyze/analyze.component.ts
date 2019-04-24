import { Component, OnInit } from '@angular/core';

import { UserService } from '@core/services/user.service';
import { User } from '@shared/models/User';

@Component({
  selector: 'app-analyze',
  templateUrl: './analyze.component.html',
  styleUrls: ['./analyze.component.scss']
})
export class AnalyzeComponent implements OnInit {
  user: User;
  codes: Array<{ id: string, date: Date, userName: string, content: string }>;
  currentCode: { id: string, date: Date, userName: string, content: string };

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = this.userService.User;
    this.codes = this.userService.Codes;
  }

  onCodeSelected(code: { id: string, date: Date, userName: string, content: string }) {
    this.currentCode = code;
  }
}
