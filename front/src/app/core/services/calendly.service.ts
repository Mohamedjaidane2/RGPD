import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AnswersDto} from "../models/Answers.dto";
import {environment} from "../../../environments/environment";
import {ResponseDto} from "../models/Response.dto";
import {TestModel} from "../models/Test.model";

@Injectable({providedIn:"root"})
export class CalendlyService {
  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer eyJraWQiOiIxY2UxZTEzNjE3ZGNmNzY2YjNjZWJjY2Y4ZGM1YmFmYThhNjVlNjg0MDIzZjdjMzJiZTgzNDliMjM4MDEzNWI0IiwidHlwIjoiUEFUIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiJodHRwczovL2F1dGguY2FsZW5kbHkuY29tIiwiaWF0IjoxNjg0MTUxNTk2LCJqdGkiOiI2MWJiMzkwNS1jY2ZjLTQzYzQtOGRjMC1jYTI4NWEzZWFiNTQiLCJ1c2VyX3V1aWQiOiI5ZjYyZTFjZi00ZGM4LTRlZDUtYWVkNi05MzhmY2EyOGI0MDgifQ.12_U2jelK-vSTvWHiURHx1K0x31h1C4Jjet692SCVHeKkZaUsRGc1DsaloKAANF_jCjnS6iDhufMWc8048bg8g`
    })
  }

getUserInfo(){
    return this.http.get("https://api.calendly.com\n" +
      "/users/me",this.httpOptions)
}
}

