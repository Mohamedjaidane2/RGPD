import { Component, OnInit } from '@angular/core';
import { Topic } from '../../../core/models/TopicModel';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../../environments/environment';
import { TopicService } from '../../../core/services/topic.service';
import { SideBarService } from '../../../core/services/side-bar.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css'],
})
export class TopicListComponent implements OnInit {
  constructor(
    private topicService: TopicService,
    public router: Router,
    private sideBarService: SideBarService
  ) {}
  idTest: string | null = null;
  Topics: Topic[] = [];
  isLoading = false;

  ngOnInit(): void {
    this.sideBarService.idTest.subscribe({
      next: (data: string | null) => {
        this.idTest = data;
      },
    });
    this.isLoading = true;
    this.topicService.getAllTopics().subscribe({
      next: (data) => {
        this.Topics = data;
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });
  }
}
