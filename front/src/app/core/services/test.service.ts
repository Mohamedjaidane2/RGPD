import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AnswersDto } from '../models/Answers.dto';
import { environment } from '../../../environments/environment';
import { ResponseDto } from '../models/Response.dto';
import { TestModel } from '../models/Test.model';

@Injectable({ providedIn: 'root' })
export class TestService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    }),
  };

  getTestById(id: string) {
    return this.http.get<AnswersDto>(
      `${environment.APP_PORT + `/api/answer/byTest/${id}`}`,
      this.httpOptions
    );
  }

  checkById(id: string) {
    return this.http.get<boolean>(
      `${environment.APP_PORT + `/api/test/checkById/${id}`}`,
      this.httpOptions
    );
  }

  getResultTestById(id: string) {
    return this.http.get<TestModel>(
      `${environment.APP_PORT + `/api/test/${id}`}`,
      this.httpOptions
    );
  }

  getAllTests() {
    return this.http.get<Array<ResponseDto>>(
      `${environment.APP_PORT + `/api/test/all`}`,
      this.httpOptions
    );
  }

  getTestByGuestEmail(email: string) {
    return this.http.get<TestDto>(
      `${environment.APP_PORT + `/api/test/guestEmail/${email}`}`
    );
  }

  calculate(testId: string) {
    return this.http.get(
      `${environment.APP_PORT + `/api/test/${testId}/calculate`}`
    );
  }
}

class TestDto {
  id!: string;
}
