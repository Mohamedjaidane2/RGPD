import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Topic } from '../../../core/models/TopicModel';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-creation-modal',
  templateUrl: './creation-modal.component.html',
  styleUrls: ['./creation-modal.component.css'],
})
export class CreationModalComponent {
  constructor(private http: HttpClient, private router: Router) {}

  exists: boolean = false;
  @Output('submitted') submitted = new EventEmitter();
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    }),
  };

  refreshPage() {
    this.router
      .navigateByUrl(this.router.url, { skipLocationChange: true })
      .then(() => {
        this.router.navigate([this.router.url]);
      });
  }
  AddTopic(form: NgForm) {
    const title = form.value.name;

    this.http
      .post(
        `${environment.APP_PORT + `/api/topic/add/${title}`}`,
        title,
        this.httpOptions
      )
      .subscribe({
        next: (response) => {
          this.exists = false;
          this.router.navigate(['/admin/dashboard/topics']);
          this.submitted.emit();
          window.location.reload();
        },
        error: (error:HttpErrorResponse) => {
          if(error.status == 400) {
            this.exists = true;
          }
        },
      });
  }
}
