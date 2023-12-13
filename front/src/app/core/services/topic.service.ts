import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Topic} from "../models/TopicModel";
import {environment} from "../../../environments/environment";
import { Observable } from "rxjs";

@Injectable({providedIn:"root"})
export class TopicService {
  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("token")}`
    })
  }

  getAllTopics(){
    return this.http.get<Array<Topic>>(`${environment.APP_PORT + '/api/topic/all'}`)
  }

  deleteTopic(topicId:string){
    return this.http.delete(`${environment.APP_PORT + `/api/topic/${topicId}/deleteById`}`,this.httpOptions)

  }

  updateTopic(oldTitle:string,newTitle:string):Observable<any>{
    return this.http
    .patch<any>(
      `${environment.APP_PORT + `/api/topic/update/details/${oldTitle}`}`,
      { title: newTitle },
      this.httpOptions
    )
  }

}
