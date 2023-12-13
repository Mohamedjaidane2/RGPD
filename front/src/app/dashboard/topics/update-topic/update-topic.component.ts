import { Component, Input, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { tap } from 'rxjs';
import { User } from '../../../core/models/User.model';
import { environment } from '../../../../environments/environment';
import { successfullyModified } from 'src/app/core/const/const.interface';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-update-topic',
  templateUrl: './update-topic.component.html',
  styleUrls: ['./update-topic.component.css'],
})
export class UpdateTopicComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private topicService: TopicService
  ) {}
  isLoading = false;
  res_msg!: string;
  err_msg!: string;

  oldtitle!: any;

  testform(form: NgForm) {}
  ngOnInit(): void {
    this.oldtitle = this.route.snapshot.params['title'];
  }

  goBack() {
    window.history.go(-1);
  }

  updateTopic(form: NgForm) {
    const new_title = form.value.name;
    const title = this.route.snapshot.params['title'];
    this.isLoading = true;

    this.topicService.updateTopic(title,new_title)
      .subscribe({
        next: (response: any) => {
          this.res_msg = successfullyModified;
          this.isLoading = false;
        },
        error: (error: any) => {
          this.err_msg = error.error.message;
          this.isLoading = false;
        },
      });
  }
}
