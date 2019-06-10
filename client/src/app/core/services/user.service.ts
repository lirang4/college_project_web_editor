import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

import { User } from '@shared/models/User';

@Injectable()
export class UserService {
    private user: User;
    private codes: Subject<Array<{ id: string, date: Date, userName: string, content: string, report: Array<any> }>>;

    constructor(private http: HttpClient) {
        this.codes = new Subject<Array<{ id: string, date: Date, userName: string, content: string, report: Array<any> }>>();
    }

    get User(): User {
        return this.user;
    }

    get Codes(): Observable<Array<{ id: string, date: Date, userName: string, content: string, report: Array<any> }>> {
        return this.codes.asObservable();
    }

    loadUser(userName: string, password: string): Observable<boolean> {
        const subject = new Subject<boolean>();
        this.http.get('/users', { params: { userName: userName, password: password } })
            .subscribe(
                (res: any) => this.user = res,
                (err) => this.user = undefined,
            )
            .add(() => subject.next(this.user !== undefined));
        return subject.asObservable();
    }

    loadCodes() {
        this.http.get('/codes', { params: { userName: this.user.userName } })
            .subscribe(
                (res: any) => this.codes.next(res),
                (err) => this.codes = undefined
            );
    }

    logout() {
        this.user = undefined;
        this.codes.next(undefined);
    }
}
