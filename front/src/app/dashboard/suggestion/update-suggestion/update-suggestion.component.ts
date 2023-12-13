import { Component, OnInit } from '@angular/core';
import { Topic } from '../../../core/models/TopicModel';
import {
  SuggestionModel,
  UpdateSuggestionDto,
} from '../../../core/models/Suggestion.model';
import { QuestionModel } from '../../../core/models/Question.model';
import { NgForm } from '@angular/forms';
import { QuestionService } from '../../../core/services/question.service';
import { TopicService } from '../../../core/services/topic.service';
import { SuggestionService } from '../../../core/services/suggestion.service';
import { ActivatedRoute, Router } from '@angular/router';
import {
  GetGradingScaleDto,
  gradingScaleRequest,
} from '../../../core/models/GradingScale.model';
import { GradingScaleService } from '../../../core/services/gradingScale.service';
import { data } from 'autoprefixer';
import { UpdateGradingScale as UpdateGradingScaleDto } from 'src/app/core/models/updateRequest/UpdateGradingScale.model';
import { Penalty_status_enum } from 'src/app/core/enum/PenaltyStatus.enum';
import { successfullyModified } from 'src/app/core/const/const.interface';

@Component({
  selector: 'app-update-suggestion',
  templateUrl: './update-suggestion.component.html',
  styleUrls: ['./update-suggestion.component.css'],
})
export class UpdateSuggestionComponent implements OnInit {
  constructor(
    private topicService: TopicService,
    private suggestionService: SuggestionService,
    private questionService: QuestionService,
    private gradingScaleService: GradingScaleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  updateGradingScaleDto!: gradingScaleRequest;
  updateSuggestionDto!: UpdateSuggestionDto;

  isLoading = false;
  suggestions: SuggestionModel[] = [];
  currentSuggestion!: SuggestionModel;
  currentGradingScale: GetGradingScaleDto = {
    id: '',
    penaltyAmount: 0,
    penaltyJail: 0,
    penaltyStatus: Penalty_status_enum.CHECKED,
  };
  Question: QuestionModel[] = [];
  gradingScaleId!: string;
  questionsList: QuestionModel[] = [];

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.suggestionService.getSuggestionById(id).subscribe({
      next: (data: SuggestionModel) => {
        this.currentSuggestion = data;
      },
      error: () => {
        this.isLoading = false;
      },
    });
    this.isLoading = true;
    this.isLoading = true;

    this.questionService.getAllRefQuestions().subscribe({
      next: (data) => {
        this.questionsList = data;
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });

    this.gradingScaleService.getGradingScaleBySuggestionId(id).subscribe({
      next: (res) => {
        this.currentGradingScale = res;
        this.gradingScaleId = this.currentGradingScale.id;
      },
      error: () => {
        this.isLoading = false;
      },
    });

    this.questionService.getAllRefQuestions().subscribe({
      next: (data) => {
        this.Question = data;
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });
  }

  res_msg!: string;
  err_msg!: string;

  goBack() {
    window.history.go(-1);
  }

  submit(form: NgForm) {
    this.isLoading = true;
    this.updateGradingScaleDto = {
      penaltyAmount: Number(form.value.penaltyAmount),
      penaltyJail: Number(form.value.penaltyJail),
      penaltyStatus: form.value.penaltyStatus,
    };
    this.updateSuggestionDto = {
      id: this.route.snapshot.params['id'],
      gradingScaleId: '',
      refS: form.value.ref,
      title: form.value.title,
    };
    if (!this.currentGradingScale.id) {
      this.createGradingScale(form, this.updateSuggestionDto);
    } else {
      this.updateGradingScale(form, this.updateSuggestionDto);
    }
  }
  createGradingScale(form: NgForm, updateSuggestionDto: UpdateSuggestionDto) {
    this.updateGradingScaleDto = {
      penaltyAmount: Number(form.value.penaltyAmount),
      penaltyJail: Number(form.value.penaltyJail),
      penaltyStatus: form.value.penaltyStatus,
    };
    this.gradingScaleService
      .addGradingScale(this.updateGradingScaleDto)
      .subscribe((response) => {
        this.updateSuggestion(updateSuggestionDto, response.id);
      });
  }

  updateGradingScale(form: NgForm, updateSuggestionDto: UpdateSuggestionDto) {
    this.updateGradingScaleDto = {
      penaltyAmount: Number(form.value.penaltyAmount),
      penaltyJail: Number(form.value.penaltyJail),
      penaltyStatus: form.value.penaltyStatus,
    };
    this.gradingScaleService
      .updateGradingScale(this.updateGradingScaleDto, this.gradingScaleId!)
      .subscribe({
        next: (data: any) => {
          //this.res_msg = data.message;
          this.isLoading = false;
          this.updateSuggestion(updateSuggestionDto, this.gradingScaleId);
        },
        error: (error: any) => {
          //this.err_msg = error;
          this.isLoading = false;
        },
      });
  }

  updateSuggestion(
    updateSuggestionDto: UpdateSuggestionDto,
    gradingScaleId: string
  ) {
    updateSuggestionDto.gradingScaleId = gradingScaleId;
    this.suggestionService.updateSuggestion(updateSuggestionDto).subscribe({
      next: (data: any) => {
        this.res_msg = successfullyModified;
        this.isLoading = false;
      },
      error: (error: any) => {
        //this.err_msg = error;
        this.isLoading = false;
      },
    });
  }
}
