import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';

import Chart from 'chart.js';
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
  codes: Observable<Array<{ id: string, date: Date, userName: string, content: string, report: any }>>;
  currentCode: { id: string, date: Date, userName: string, content: string, report: any };

  pollingInterval: any;
  editorCode: any;

  isInfinteLoop: boolean;
  showInfinteLoop: boolean;
  isUnusedParams: boolean;
  showUnusedParams: boolean;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = this.userService.User;
    this.codes = this.userService.Codes;

    this.isInfinteLoop = false;
    this.showInfinteLoop = false;
    this.isUnusedParams = false;
    this.showUnusedParams = false;

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

  onCodeSelected(code: { id: string, date: Date, userName: string, content: string, report: any }) {
    this.currentCode = code;
    this.editorCode.setValue(this.currentCode.content);

    this.isInfinteLoop = [...this.currentCode.report.bestResults, ...this.currentCode.report.worstResults]
      .find(r => r.rowCover === -1) !== undefined;
    this.isUnusedParams = Object.values(this.currentCode.report.toatlUnusedVars)
      .find(r => r === 100) !== undefined;

    setTimeout(() => this.initiateGraphs(), 0);
  }

  private initiateListPolling(): void {
    this.userService.loadCodes();
    this.pollingInterval = setInterval(() => this.userService.loadCodes(), this.POLLING_INTERVAL);
  }

  private initiateGraphs(): void {
    const mixedChart = new Chart(document.getElementById('lineCoverageGraph'), {
      type: 'line',
      data: {
        datasets: [{
          label: '',
          data: this.currentCode.report.bestResults.map(run => run.lineCoveragePresentage),
        }],
        labels: this.currentCode.report.bestResults.map(run => run.parameterValue)
      },
    });
  }
}
