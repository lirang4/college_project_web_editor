import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
    selector: 'app-code-editor-form',
    templateUrl: './code-editor-form.component.html',
    styleUrls: ['./code-editor-form.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CodeEditorFormComponent implements OnInit {
    @Input() isLoading: boolean;

    @Output() submit = new EventEmitter<string>();

    editorForm: FormGroup;

    constructor(private fb: FormBuilder) { }

    ngOnInit() {
        this.editorForm = this.fb.group({
            code: ['', Validators.required]
        });
    }

    onSubmitCode(): void {
        this.submit.emit(this.editorForm.value);
    }
}
