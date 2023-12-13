import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { QuestionModel } from '../../../core/models/Question.model';
import { SuggestionService } from '../../../core/services/suggestion.service';
import { QuestionService } from '../../../core/services/question.service';
import { gradingScaleRequest } from '../../../core/models/GradingScale.model';
import { GradingScaleService } from '../../../core/services/gradingScale.service';

@Component({
  selector: 'app-creation-suggestion-modal',
  templateUrl: './creation-suggestion-modal.component.html',
  styleUrls: ['./creation-suggestion-modal.component.css'],
})
export class CreationSuggestionModalComponent {
  constructor(
    private suggestionService: SuggestionService,
    private questionService: QuestionService,
    private gradingScaleService: GradingScaleService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  questionsList: QuestionModel[] = [];
  isLoading = false;
  @Output('submitted') submitted = new EventEmitter();

  ngOnInit(): void {
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
  }

  AddSuggestion(form: NgForm) {
    const requestdto: gradingScaleRequest = {
      penaltyAmount: form.value.penaltyAmount,
      penaltyJail: form.value.penaltyJail,
      penaltyStatus: form.value.penaltyStatus,
    };

    this.gradingScaleService
      .addGradingScale(requestdto)
      .subscribe((response) => {
        localStorage.setItem('gradingScale', response.id);
        const title = form.value.title;
        const question = form.value.question;
        const ref = form.value.ref;
        const gradingscaleid = localStorage.getItem('gradingScale');
        const id = this.route.snapshot.params['id'];
        const data = {
          question_id: question,
          gradingScale_id: gradingscaleid,
          ref: ref,
          title: title,
        };
        this.addSuggestionStep2(data);
      });
  }

  addSuggestionStep2(data: any) {
    this.suggestionService.addSuggestion(data).subscribe((response) => {
      this.router.navigate(['/admin/dashboard/suggestions']);
      this.submitted.emit();
      window.location.reload();
    });
  }
}
