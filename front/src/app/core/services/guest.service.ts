import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GuestModel } from '../models/Guest.model';
import { environment } from '../../../environments/environment';
import { SuccessDto } from '../models/success.dto';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class GuestService {
  constructor(private http: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      }),
    };
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    }),
  };

  getAllGuests() {
    return this.http.get<Array<GuestModel>>(
      `${environment.APP_PORT + '/api/guest/all'}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        }),
      }
    );
  }

  //returning id of test
  addGuest(data: any): Observable<SuccessDto> {
    return this.http.post<SuccessDto>(
      `${environment.APP_PORT + `/api/guest/add`}`,
      data
    );
  }
}
