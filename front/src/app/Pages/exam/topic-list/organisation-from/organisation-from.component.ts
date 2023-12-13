import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Topic } from '../../../../core/models/TopicModel';
import { environment } from '../../../../../environments/environment';
import { OrganisationService } from '../../../../core/services/organisation.service';

@Component({
  selector: 'app-organisation-from',
  templateUrl: './organisation-from.component.html',
  styleUrls: ['./organisation-from.component.css'],
})
export class OrganisationFromComponent implements OnInit {
  constructor(
    private organisationService: OrganisationService,
    private router: Router
  ) {}
  isLoading = false;

  ngOnInit(): void {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    }),
  };

  AddOrganisation(form: NgForm) {
    const activityArea = form.value.activity;
    const name = form.value.name;
    const workforce = form.value.workforce;
    const expertiseField = form.value.expertise;
    const data = {
      activityArea: activityArea,
      expertiseField: expertiseField,
      name: name,
      workforce: workforce,
    };
    localStorage.setItem('organisation', name);

    this.organisationService.addOrganisation(data).subscribe({
      next: (data) => {
        this.router.navigate(['/exam/guest']).then((value) => {
          //window.location.reload();
        });
      },
      error: (e) => {
        if (e) {
          this.router.navigate(['/exam/guest']).then((value) => {
            //window.location.reload();
          });
        }
      },
    });
  }
}
