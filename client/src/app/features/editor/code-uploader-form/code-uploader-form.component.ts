import { Component, Input, EventEmitter, Output, ChangeDetectionStrategy, AfterViewInit, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import * as CodeMirror from 'codemirror';
import 'codemirror/mode/clike/clike';

@Component({
    selector: 'app-code-uploader-form',
    templateUrl: './code-uploader-form.component.html',
    styleUrls: ['./code-uploader-form.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CodeUploaderFormComponent implements AfterViewInit {
    @Input() isLoading: boolean;
    @Output() submit = new EventEmitter<string>();
    @ViewChild('editor') editor: any;

    editorCode: any;
    uploader: FileUploader;
    reader: FileReader;

    constructor() {
        this.uploader = new FileUploader({});
        this.reader = new FileReader();

        this.uploader.onAfterAddingFile = (fileItem) => this.reader.readAsText(fileItem._file);
        this.reader.onload = (ev: any) => this.editorCode.setValue(ev.target.result);
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

    onSubmitFile(): void {
        this.submit.emit(this.editorCode.getValue());
    }
}
