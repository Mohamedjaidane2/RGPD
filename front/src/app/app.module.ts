import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { NavBarComponent } from './Pages/nav-bar/nav-bar.component';
import { HomeComponent } from './Pages/home/home.component';
import { ExamComponent } from './Pages/exam/exam.component';
import { AppRoutingModule } from './app-routing.module';
import { TopicListComponent } from './Pages/exam/topic-list/topic-list.component';
import { QandAListComponent } from './Pages/exam/qand-a-list/qand-a-list.component';
import { ProgressBarComponent } from './Pages/exam/progress-bar/progress-bar.component';
import { ResultComponent } from './Pages/result/result.component';
import { CalendarComponent } from './Pages/result/calendar/calendar.component';
import { ScoreComponent } from './Pages/result/score/score.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './dashboard/login/login.component';
import { SideBarComponent } from './dashboard/side-bar/side-bar.component';
import { MaindashboardComponent } from './dashboard/maindashboard/maindashboard.component';
import { TopicsComponent } from './dashboard/topics/topics.component';
import { QuestionsComponent } from './dashboard/questions/questions.component';
import { SuggestionComponent } from './dashboard/suggestion/suggestion.component';
import { NotfoundComponent } from './Pages/notfound/notfound.component';
import { GuestsComponent } from './dashboard/maindashboard/guests/guests.component';
import { CreationModalComponent } from './dashboard/topics/creation-modal/creation-modal.component';
import { CreationQuestionModalComponent } from './dashboard/questions/creation-question-modal/creation-modal.component';
import { CreationSuggestionModalComponent } from './dashboard/suggestion/creation-suggestion-modal/creation-suggestion-modal.component';
import { OrganisationFromComponent } from './Pages/exam/topic-list/organisation-from/organisation-from.component';
import { GuestFormComponent } from './Pages/exam/topic-list/guest-form/guest-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoadingSpinnerComponent } from './shared/loading-spinner/loading-spinner.component';
import { UpdateTopicComponent } from './dashboard/topics/update-topic/update-topic.component';
import { UpdateQuestionComponent } from './dashboard/questions/update-question/update-question.component';
import { GradingScaleComponent } from './dashboard/grading-scale/grading-scale.component';
import { CreationGradingScaleModalComponent } from './dashboard/grading-scale/creation-grading-scale-modal/creation-grading-scale-modal.component';
import { TestDetailsComponent } from './dashboard/tests/test-details/test-details.component';
import { TestsComponent } from './dashboard/tests/tests.component';
import { UpdateSuggestionComponent } from './dashboard/suggestion/update-suggestion/update-suggestion.component';
import { ConfirmDeleteComponent } from './dashboard/topics/confirm-delete/confirm-delete.component';
import { ConfirmQuestionDeleteComponent } from './dashboard/questions/confirm-question-delete/confirm-question-delete.component';
import { ConfirmSuggestionDeleteComponent } from './dashboard/suggestion/confirm-suggestion-delete/confirm-suggestion-delete.component';
import { PdfComponent } from './shared/pdf/pdf.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    HomeComponent,
    ExamComponent,
    TopicListComponent,
    QandAListComponent,
    ProgressBarComponent,
    ResultComponent,
    CalendarComponent,
    ScoreComponent,
    DashboardComponent,
    LoginComponent,
    SideBarComponent,
    MaindashboardComponent,
    TopicsComponent,
    QuestionsComponent,
    SuggestionComponent,
    NotfoundComponent,
    GuestsComponent,
    TestsComponent,
    CreationModalComponent,
    CreationQuestionModalComponent,
    CreationSuggestionModalComponent,
    OrganisationFromComponent,
    GuestFormComponent,
    LoadingSpinnerComponent,
    UpdateTopicComponent,
    UpdateQuestionComponent,
    TestDetailsComponent,
    GradingScaleComponent,
    CreationGradingScaleModalComponent,
    UpdateSuggestionComponent,
    ConfirmDeleteComponent,
    ConfirmQuestionDeleteComponent,
    ConfirmSuggestionDeleteComponent,
    PdfComponent,
  ],
  exports: [RouterModule],
  imports: [
    BrowserModule,
    ButtonModule,
    RouterModule.forRoot([]),
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
