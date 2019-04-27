import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs';

import { UserService } from '@core/services/user.service';
import { User } from '@shared/models/User';

@Component({
  selector: 'app-analyze',
  templateUrl: './analyze.component.html',
  styleUrls: ['./analyze.component.scss']
})
export class AnalyzeComponent implements OnInit, OnDestroy {
  private readonly POLLING_INTERVAL = 3000;

  user: User;
  codes: Observable<Array<{ id: string, date: Date, userName: string, content: string }>>;
  currentCode: { id: string, date: Date, userName: string, content: string };

  pollingInterval: any;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = this.userService.User;
    this.codes = this.userService.Codes;

    this.initiateListPolling();
  }

  ngOnDestroy(): void {
    clearInterval(this.pollingInterval);
  }

  onCodeSelected(code: { id: string, date: Date, userName: string, content: string }) {
    this.currentCode = code;
  }

  private initiateListPolling(): void {
    this.userService.loadCodes();
    this.pollingInterval = setInterval(() => this.userService.loadCodes(), this.POLLING_INTERVAL);
  }
}
