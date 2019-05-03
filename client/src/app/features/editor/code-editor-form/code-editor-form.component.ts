import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy, ViewChild, AfterViewInit } from '@angular/core';
import * as CodeMirror from 'codemirror';
import 'codemirror/mode/clike/clike';


@Component({
    selector: 'app-code-editor-form',
    templateUrl: './code-editor-form.component.html',
    styleUrls: ['./code-editor-form.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CodeEditorFormComponent implements AfterViewInit {
    @Input() isLoading: boolean;
    @Output() submit = new EventEmitter<string>();
    @ViewChild('editor') editor: any;

    editorCode: any;

    constructor() { }

    ngAfterViewInit() {
        this.editorCode = CodeMirror.fromTextArea(this.editor.nativeElement, {
            lineNumbers: true,
            mode: 'text/x-csrc',
            theme: 'darcula'
        });
    }

    onSubmitCode(): void {
        this.submit.emit(this.editorCode.getValue());
    }
}
