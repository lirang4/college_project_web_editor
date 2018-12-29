import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileUploader } from 'ng2-file-upload';

const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.scss']
})
export class EditorComponent implements OnInit {
  isEditor: boolean;
  isUploader: boolean;

  editorForm: FormGroup;
  uploaderForm: FormGroup;

  isLoading: boolean;

  uploader: FileUploader = new FileUploader({ url: URL });

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.editorForm = this.fb.group({ code: ['', Validators.required] });
    this.uploaderForm = this.fb.group({});

    this.isLoading = false;
  }

  onSubmitCode(): void {
    this.isLoading = true;
    console.log(this.editorForm.value);

    setTimeout(() => {
      this.isLoading = false;
    }, 3000);
  }

  onSubmitFile(): void {
    this.isLoading = true;
    console.log(this.uploader);

    setTimeout(() => {
      this.isLoading = false;
    }, 3000);
  }
}
