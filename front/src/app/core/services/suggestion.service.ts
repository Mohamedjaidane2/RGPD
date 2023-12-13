import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GetSuggestionByTopicModel, SuggestionModel, UpdateSuggestionDto} from "../models/Suggestion.model";
import {environment} from "../../../environments/environment";

@Injectable({providedIn:"root"})
export class SuggestionService {
  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("token")}`
    })
  }

  getAllSeggestions(){
    return this.http.get<Array<SuggestionModel>>(`${environment.APP_PORT + `/api/suggestion/all`}`)

  }
  getSeggestionsByTopic(id:string){
    return this.http.get<Array<GetSuggestionByTopicModel>>(`${environment.APP_PORT + `/api/suggestion/getByTopic/${id}`}`)

  }

  addSuggestion(data:any){
    return  this.http.post(`${environment.APP_PORT + `/api/suggestion/add`}`,data,this.httpOptions)
  }

  getSuggestionById(id:string){
    return this.http.get<SuggestionModel>(`${environment.APP_PORT + `/api/suggestion/${id}`}`,this.httpOptions)
  }

  deleteSuggestion(id:string){
    return this.http.delete(`${environment.APP_PORT + `/api/suggestion/${id}/deleteById`}`,this.httpOptions)

  }

  updateSuggestion(data:UpdateSuggestionDto){
    return this.http.patch<any>(environment.APP_PORT + "/api/suggestion/update",data, this.httpOptions)
  }
}
