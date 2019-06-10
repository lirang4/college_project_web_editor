import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';

import * as CodeMirror from 'codemirror';
import 'codemirror/mode/clike/clike';

import { UserService } from '@core/services/user.service';
import { User } from '@shared/models/User';

@Component({
  selector: 'app-analyze',
  templateUrl: './analyze.component.html',
  styleUrls: ['./analyze.component.scss']
})
export class AnalyzeComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('editor') editor: any;

  private readonly POLLING_INTERVAL = 3000;

  user: User;
  codes: Observable<Array<{ id: string, date: Date, userName: string, content: string, report: Array<any> }>>;
  currentCode: { id: string, date: Date, userName: string, content: string };

  pollingInterval: any;
  editorCode: any;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = this.userService.User;
    this.codes = this.userService.Codes;

    this.initiateListPolling();
  }

  ngAfterViewInit() {
    this.editorCode = CodeMirror.fromTextArea(this.editor.nativeElement, {
      lineNumbers: true,
      mode: 'text/x-csrc',
      theme: 'darcula',
      readOnly: true
    });

    this.editorCode.setSize('100%', '100%');
  }

  ngOnDestroy(): void {
    clearInterval(this.pollingInterval);
  }

  onCodeSelected(code: { id: string, date: Date, userName: string, content: string }) {
    this.currentCode = code;
    this.editorCode.setValue(this.currentCode.content);
  }

  private initiateListPolling(): void {
    this.userService.loadCodes();
    this.pollingInterval = setInterval(() => this.userService.loadCodes(), this.POLLING_INTERVAL);
  }
}
