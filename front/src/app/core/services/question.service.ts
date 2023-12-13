import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {QuestionByTopic} from "../models/Question.model.bytopic";
import {GetAllQuestionsDto, QuestionModel} from "../models/Question.model";

@Injectable({providedIn:"root"})
export class QuestionService {
  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("token")}`
    })
  }

  getAllQuestions(){
    return this.http.get<Array<GetAllQuestionsDto>>(`${environment.APP_PORT + `/api/question/all`}`)
  }

  getAllRefQuestions(){
    return this.http.get<Array<QuestionModel>>(`${environment.APP_PORT + `/api/question/allRef`}`)
  }
  getQuestionById (id:string){
    return this.http.get<QuestionModel>(`${environment.APP_PORT + `/api/question/id/${id}`}`)
  }

  deleteQuestion(id:string){
    return this.http.delete(`${environment.APP_PORT + `/api/question/${id}/deleteById`}`, this.httpOptions)
  }

  updateQuestion(data:any){
    return this.http.patch<string>(`${environment.APP_PORT + `/api/question/update`}`, data, this.httpOptions)

  }

  addQuestion(adddto:any){
    return this.http.post(`${environment.APP_PORT + '/api/question/addQuestion'}`,adddto,this.httpOptions)
  }

  getQuestionByTopicId(id:string){
    return this.http.get<Array<QuestionByTopic>>(`${environment.APP_PORT + `/api/question/byTopic/${id}`}`)

  }

}

