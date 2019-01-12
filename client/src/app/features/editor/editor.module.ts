import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'ng2-file-upload';

import { SharedModule } from '@shared/shared.module';
import { EditorComponent } from './editor.component';
import { EditorRoutingModule } from './editor-routing.module';
import { CodeEditorFormComponent } from './code-editor-form/code-editor-form.component';
import { CodeUploaderFormComponent } from './code-uploader-form/code-uploader-form.component';

@NgModule({
  declarations: [
    EditorComponent,
    CodeEditorFormComponent,
    CodeUploaderFormComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    EditorRoutingModule,
    FileUploadModule,
  ]
})
export class EditorModule { }
