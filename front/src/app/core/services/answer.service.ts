import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AnswerModel} from "../models/Answers.model";
import {environment} from "../../../environments/environment";

@Injectable({providedIn:"root"})
export class AnswerService {
  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("token")}`
    })
  }
PostAnswer(data:any){
    return this.http.post<AnswerModel>(`${environment.APP_PORT + `/api/answer/addMultiple`}`, data)
}

}
