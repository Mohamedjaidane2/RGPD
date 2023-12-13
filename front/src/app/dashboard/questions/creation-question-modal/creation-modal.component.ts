import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Topic } from '../../../core/models/TopicModel';
import { QuestionService } from '../../../core/services/question.service';
import { TopicService } from '../../../core/services/topic.service';
import { Suggestion } from '../../../core/models/Answers.dto';
import { SuggestionService } from '../../../core/services/suggestion.service';
import {
  GetSuggestionByTopicModel,
  SuggestionModel,
} from '../../../core/models/Suggestion.model';

@Component({
  selector: 'app-creation-question-modal',
  templateUrl: './creation-question-modal.component.html',
  styleUrls: ['./creation-question-modal.component.css'],
})
export class CreationQuestionModalComponent implements OnInit {
  constructor(
    private questionService: QuestionService,
    private topicService: TopicService,
    private suggestionService: SuggestionService,
    private router: Router
  ) {}
  Topics: Topic[] = [];
  isLoading = false;
  isSubQuestion!: boolean;
  sugestions: SuggestionModel[] = [];
  UniqueSelectedList: string[] = [];
  topic_id!: string;

  ngOnInit(): void {
    this.isSubQuestion = false;
    this.isLoading = true;

    this.suggestionService.getAllSeggestions().subscribe({
      next: (data) => {
        this.sugestions = data;
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });

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

  check() {
    this.isSubQuestion = !this.isSubQuestion;
  }
  onSelectChange(select: HTMLSelectElement) {
    //test Unique Select
    if (select.value) {
      localStorage.setItem('topicId', select.value);
    }
  }

  AddQuestion(form: NgForm) {
    const refQ = form.value.ref;
    const title = form.value.title;
    const topic_id = form.value.topic;
    const type = form.value.type;
    let suggestionSrc = form.value.suggestionSrc;
    if (!form.value.suggestionSrc) {
      suggestionSrc = null;
    }
    const data = {
      refQ: refQ,
      title: title,
      topic_id: topic_id,
      suggestionSrc: suggestionSrc,
      type: type,
    };
    this.questionService.addQuestion(data).subscribe((response) => {
      this.router.navigate(['/admin/dashboard/questions']);
      window.location.reload()
    });
  }
}
