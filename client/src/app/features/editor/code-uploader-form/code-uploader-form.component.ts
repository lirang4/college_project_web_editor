import { Component, OnInit, Input, EventEmitter, Output, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { FileUploader } from 'ng2-file-upload';

const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

@Component({
    selector: 'app-code-uploader-form',
    templateUrl: './code-uploader-form.component.html',
    styleUrls: ['./code-uploader-form.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CodeUploaderFormComponent implements OnInit {
    @Input() isLoading: boolean;
    @Output() submit = new EventEmitter<string>();

    uploaderForm: FormGroup;
    uploader: FileUploader = new FileUploader({ url: URL });

    constructor(private fb: FormBuilder) { }

    ngOnInit() {
        this.uploaderForm = this.fb.group({});
    }

    onSubmitFile(): void {
        this.submit.emit(this.uploaderForm.value);
    }
}
