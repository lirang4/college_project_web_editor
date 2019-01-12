import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.scss']
})
export class EditorComponent implements OnInit {
  isEditor: boolean;
  isUploader: boolean;

  isLoading: boolean;

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.isLoading = false;
  }

  onSubmit(code: string): void {
    this.isLoading = true;

    // TODO: send the code to the server
    setTimeout(() => this.isLoading = false, 3000);
  }
}
