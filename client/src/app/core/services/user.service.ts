import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { User } from '@app/shared/models/User';

@Injectable()
export class UserService {
    private user: User;

    constructor(private http: HttpClient) { }

    get User(): User {
        return this.user;
    }

    loadUser(userName: string, password: string): Observable<User> {
        const subject = new Subject<User>();
        this.http.get('/users', { params: { userName: userName, password: password } })
            .subscribe(
                (res: any) => this.user = res,
                (err) => this.user = undefined,
                () => subject.next(this.user)
            );
        return subject.asObservable();
    }
}
