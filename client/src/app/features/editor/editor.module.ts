import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'ng2-file-upload';

import { SharedModule } from '@shared/shared.module';
import { EditorComponent } from './editor.component';
import { EditorRoutingModule } from './editor-routing.module';

@NgModule({
  declarations: [
    EditorComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    EditorRoutingModule,
    FileUploadModule,
  ]
})
export class EditorModule { }
