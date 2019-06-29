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
  @ViewChild('editor', { static: true }) editor: any;

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
    this.isUnusedParams = false;
    this.showInfinteLoop = false;
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

    this.showInfinteLoop = false;
    this.showUnusedParams = false;

    this.isUnusedParams = Object.values(this.currentCode.report.toatlUnusedVars).length > 0;
    this.isInfinteLoop = [...this.currentCode.report.bestResults, ...this.currentCode.report.worstResults]
      .find(r => r.rowCover === -1) !== undefined;

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
          label: 'Best Line Coverage',
          fill: false,
          borderColor: '#ffffff',
          backgroundColor: '#ffffff',
          pointBackgroundColor: '#ffffff',
          pointBorderColor: '#ffffff',
          pointHoverBackgroundColor: '#ffffff',
          pointHoverBorderColor: '#ffffff',
          data: this.currentCode.report.bestResults.map(run => run.lineCoveragePresentage),
        },
        {
          label: 'Wrost Line Coverage',
          fill: false,
          borderColor: '#FF0000',
          backgroundColor: '#FF0000',
          pointBackgroundColor: '#FF0000',
          pointBorderColor: '#FF0000',
          pointHoverBackgroundColor: '#FF0000',
          pointHoverBorderColor: '#FF0000',
          data: this.currentCode.report.worstResults.map(run => run.lineCoveragePresentage),
        }],
        labels: this.currentCode.report.bestResults.map(run => run.parameterValue)
      },
    });
  }
}
