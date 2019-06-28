import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { UserService } from '@app/core/services/user.service';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.scss']
})
export class EditorComponent implements OnInit {
  isEditor: boolean;
  isUploader: boolean;
  isLoading: boolean;

  errMsg: string;

  constructor(private fb: FormBuilder, private http: HttpClient, private userService: UserService) { }

  ngOnInit() {
    this.isLoading = false;
  }

  onSubmit(code: string): void {
    if (!code) {
      return;
    }

    this.isLoading = true;
    this.errMsg = undefined;

    this.http.post(
      '/codes',
      { userName: this.userService.User.userName, content: code },
      { responseType: 'text' }
    )
      .subscribe(
        (res) => {
          this.isLoading = false;
        },
        (err) => {
          this.errMsg = err.error;
          this.isLoading = false;
        }
      );
  }
}
