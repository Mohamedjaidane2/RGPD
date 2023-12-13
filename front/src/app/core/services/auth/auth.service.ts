import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { User } from '../../models/User.model';
import { environment } from '../../../../environments/environment';
import { GuardResponse } from '../../models/auth/GuardResponse';

interface AuthResponseData {
  token: string;
  name: string;
  role: string;
}
@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new Subject<User>();
  constructor(private http: HttpClient) {}


  login(email: string, password: string): Observable<AuthResponseData> {
    return this.http.post<AuthResponseData>(
      `${environment.APP_PORT + '/api/user/login'}`,
      {
        email: email,
        password: password,
      }
    );
  }

  checkAuth(): Observable<boolean> {
    return this.http.get<boolean>(
      `${environment.APP_PORT + '/api/user/check-auth'}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        }),
      }
    );
  }
}
