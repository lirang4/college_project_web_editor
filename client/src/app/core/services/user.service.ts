import { Injectable } from '@angular/core';
import { User } from '@app/shared/models/User';

@Injectable()
export class UserService {
    private user: User;

    constructor() { }

    get User(): User {
        return this.user;
    }

    loadUser(userName: string, password: string): void {
        this.user = {
            userName: 'userName',
            email: 'email@gmail.com',
            firstName: 'firstName',
            lastName: 'lastName'
        };
    }
}
