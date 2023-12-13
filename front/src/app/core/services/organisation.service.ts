import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({providedIn:"root"})
export class OrganisationService {
  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("token")}`
    })
  }
  addOrganisation(data:any){
    return this.http.post(`${environment.APP_PORT + `/api/organisation/add`}`,data)
  }
}
