import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import {
  GetGradingScaleDto,
  gradingScaleId,
} from '../models/GradingScale.model';

@Injectable({ providedIn: 'root' })
export class GradingScaleService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    }),
  };

  addGradingScale(data: any) {
    return this.http.post<gradingScaleId>(
      `${environment.APP_PORT + `/api/grading-scale/add`}`,
      data,
      this.httpOptions
    );
  }

  updateGradingScale(data: any, id: string) {
    return this.http.patch<any>(
      `${environment.APP_PORT + `/api/grading-scale/update/${id}`}`,
      data,
      this.httpOptions
    );
  }

  getGradingScaleBySuggestionId(id: string) {
    return this.http.get<GetGradingScaleDto>(
      `${environment.APP_PORT + `/api/grading-scale/bySuggestionId/${id}`}`,
      this.httpOptions
    );
  }
}
