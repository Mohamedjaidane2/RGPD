import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Topic } from '../../../../core/models/TopicModel';
import { TopicService } from '../../../../core/services/topic.service';
import { GuestService } from '../../../../core/services/guest.service';
import { SuccessDto } from 'src/app/core/models/success.dto';

@Component({
  selector: 'app-guest-form',
  templateUrl: './guest-form.component.html',
  styleUrls: ['./guest-form.component.css'],
})
export class GuestFormComponent implements OnInit {
  err_msg: string | null = null;
  constructor(
    private topicService: TopicService,
    private guestService: GuestService,
    private router: Router
  ) {}
  isLoading = false;

  Topics: Topic[] = [];
  PossibleRoutes: string[] = [];
  goBack() {
    window.history.go(-1);
  }
  ngOnInit(): void {
    this.topicService.getAllTopics().subscribe({
      next: (data) => {
        this.Topics = data;
        this.Topics.map((topic) => {
          if (topic.id != null) {
            this.PossibleRoutes.push(topic.id);
          }
        });
      },
      error: (e) => {},
    });
  }

  AddGuest(form: NgForm) {
    const email = form.value.email;
    const expertiseField = form.value.expertiseField;
    const firstName = form.value.firstName;
    const functionx = form.value.functionx;
    const lastName = form.value.lastName;
    const organisation_name = localStorage.getItem('organisation');
    const profile = form.value.profile;
    const telephone = form.value.telephone.toString();
    const data = {
      email: email,
      expertiseField: expertiseField,
      firstName: firstName,
      function: functionx,
      lastName: lastName,
      organisation_name: organisation_name?.toString(),
      profile: profile,
      telephone: telephone,
    };
    this.guestService.addGuest(data).subscribe({
      next: (data: SuccessDto) => {
        localStorage.setItem('email', email);
        this.router.navigate([
          '/exam',
          data.message, //test id
          'topic',
          this.PossibleRoutes[0],
        ]);
        this.err_msg = null;
      },
      error: (e) => {
        this.err_msg = e.error.message
      },
    });
  }
}
