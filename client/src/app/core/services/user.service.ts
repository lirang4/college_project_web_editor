import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { User } from '@app/shared/models/User';

@Injectable()
export class UserService {
    private user: User;
    private codes: Array<{ id: string, date: Date, userName: string, content: string }>;

    constructor(private http: HttpClient) { }

    get User(): User {
        return this.user;
    }

    get Codes(): Array<{ id: string, date: Date, userName: string, content: string }> {
        return this.codes;
    }

    loadUser(userName: string, password: string): Observable<User> {
        const subject = new Subject<User>();
        this.http.get('/users', { params: { userName: userName, password: password } })
            .subscribe(
                (res: any) => {
                    this.user = res;
                    this.loadCodes();
                },
                (err) => this.user = undefined,
                () => subject.next(this.user)
            );
        return subject.asObservable();
    }

    loadCodes() {
        this.http.get('/codes', { params: { userName: this.user.userName } })
            .subscribe(
                (res: any) => this.codes = res,
                (err) => this.codes = undefined,
                () => { }
            );
    }
}
