import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Pages/home/home.component';
import { ExamComponent } from './Pages/exam/exam.component';
import { ResultComponent } from './Pages/result/result.component';
import { LoginComponent } from './dashboard/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotfoundComponent } from './Pages/notfound/notfound.component';
import { MaindashboardComponent } from './dashboard/maindashboard/maindashboard.component';
import { TopicsComponent } from './dashboard/topics/topics.component';
import { QuestionsComponent } from './dashboard/questions/questions.component';
import { SuggestionComponent } from './dashboard/suggestion/suggestion.component';
import { OrganisationFromComponent } from './Pages/exam/topic-list/organisation-from/organisation-from.component';
import { GuestFormComponent } from './Pages/exam/topic-list/guest-form/guest-form.component';
import { QandAListComponent } from './Pages/exam/qand-a-list/qand-a-list.component';
import { UpdateTopicComponent } from './dashboard/topics/update-topic/update-topic.component';
import { GradingScaleComponent } from './dashboard/grading-scale/grading-scale.component';
import { TestsComponent } from './dashboard/tests/tests.component';
import { TestDetailsComponent } from './dashboard/tests/test-details/test-details.component';
import { UpdateQuestionComponent } from './dashboard/questions/update-question/update-question.component';
import { UpdateSuggestionComponent } from './dashboard/suggestion/update-suggestion/update-suggestion.component';
import { authGuard } from './core/services/guard/auth.guard';
import { resultGuard } from './core/services/guard/result.guard';
import {PdfComponent} from "./shared/pdf/pdf.component";
const routes: Routes = [
  {
    path: 'result/:idTest',
    canActivate: [resultGuard],
    component: ResultComponent,
  },
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'pdf',
    component: PdfComponent,
  },
  {
    path: 'exam',
    component: ExamComponent,
    children: [
      {
        path: 'organisation',
        component: OrganisationFromComponent,
      },
      {
        path: 'guest',
        component: GuestFormComponent,
      },
      {
        path: ':idTest/topic/:id',
        component: QandAListComponent,
      },
    ],
  },
  {
    path: 'admin/login',
    component: LoginComponent,
  },
  {
    path: 'admin/dashboard',
    component: DashboardComponent,
    children: [
      {
        path: '',
        canActivate: [authGuard],
        component: MaindashboardComponent,
      },

      {
        path: 'topics',
        canActivate: [authGuard],
        component: TopicsComponent,
      },
      {
        path: 'topics/update/:title',
        canActivate: [authGuard],
        component: UpdateTopicComponent,
      },
      {
        path: 'questions/update/:id',
        canActivate: [authGuard],
        component: UpdateQuestionComponent,
      },
      {
        path: 'questions',
        canActivate: [authGuard],
        component: QuestionsComponent,
      },
      {
        path: 'suggestions',
        canActivate: [authGuard],
        component: SuggestionComponent,
      },
      {
        path: 'suggestions/update/:id',
        canActivate: [authGuard],
        component: UpdateSuggestionComponent,
      },
      {
        path: 'gradingscale',
        canActivate: [authGuard],
        component: GradingScaleComponent,
      },
      {
        path: 'tests',
        canActivate: [authGuard],
        component: TestsComponent,
      },
      {
        path: 'tests/test/:id',
        canActivate: [authGuard],
        component: TestDetailsComponent,
      },
    ],
  },
  {
    path: '**',
    component: NotfoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [],
})
export class AppRoutingModule {}
