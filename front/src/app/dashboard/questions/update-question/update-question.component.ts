import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { QuestionService } from '../../../core/services/question.service';
import { QuestionModel } from '../../../core/models/Question.model';
import { Topic } from '../../../core/models/TopicModel';
import { SuggestionModel } from '../../../core/models/Suggestion.model';
import { TopicService } from '../../../core/services/topic.service';
import { SuggestionService } from '../../../core/services/suggestion.service';
import { successfullyModified } from 'src/app/core/const/const.interface';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.css'],
})
export class UpdateQuestionComponent implements OnInit {
  constructor(
    private questionService: QuestionService,
    private topicService: TopicService,
    private suggestionService: SuggestionService,
    private route: ActivatedRoute
  ) {}

  Topics: Topic[] = [];
  isLoading = false;
  isSubQuestion!: boolean;
  suggestions: SuggestionModel[] = [];
  UniqueSelectedList: string[] = [];
  currentQuestion!: QuestionModel;
  selectedSuggestionSrc: (string | undefined)[] = [];
  selectedSuggestions: (string | undefined)[] = [];
  topic_id!: string;

  ngOnInit(): void {
    this.isSubQuestion = false;
    this.isLoading = true;
    const id = this.route.snapshot.params['id'];
    this.questionService.getQuestionById(id).subscribe({
      next: (data) => {
        this.currentQuestion = data;
        this.selectedSuggestionSrc = data.suggestionSrc.map((src) => src.id);
        this.selectedSuggestions = data.suggestions.map((sugg) => sugg.id);
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });

    this.suggestionService.getAllSeggestions().subscribe({
      next: (data) => {
        this.suggestions = data;
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
  res_msg!: string;
  err_msg!: string;

  data!: UpdateQuestionComponent;

  goBack() {
    window.history.go(-1);
  }

  UpdateQuestion(form: NgForm) {
    const id = this.route.snapshot.params['id'];
    const refQ = form.value.ref;
    const title = form.value.title;
    const topic_id = form.value.topic;
    const type = form.value.type;
    const suggestionIds = form.value.suggestionIds;
    let suggestionSrc = form.value.suggestionSrc;
    if (!form.value.suggestionSrc) {
      suggestionSrc = [];
    }
    const data = {
      id: id,
      refQ: refQ,
      title: title,
      topicId: topic_id,
      type: type,
      suggestionSrcIds: suggestionSrc,
      suggestionIds: suggestionIds,
    };
    this.questionService.updateQuestion(data).subscribe({
      next: (data: any) => {
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
