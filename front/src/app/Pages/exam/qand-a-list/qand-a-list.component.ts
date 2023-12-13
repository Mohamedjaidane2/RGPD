import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Topic } from '../../../core/models/TopicModel';
import { NgForm } from '@angular/forms';
import { QuestionByTopic } from '../../../core/models/Question.model.bytopic';
import { TestAnswers } from '../../../core/models/Answers.model';
import { QuestionService } from '../../../core/services/question.service';
import { TopicService } from '../../../core/services/topic.service';
import { AnswerService } from '../../../core/services/answer.service';
import {
  QuestionSuggestionSrcWithJoin,
  SuggestionSrc,
} from '../../../core/models/Answers.dto';
import { SideBarService } from 'src/app/core/services/side-bar.service';

@Component({
  selector: 'app-qand-a-list',
  templateUrl: './qand-a-list.component.html',
  styleUrls: ['./qand-a-list.component.css'],
})
export class QandAListComponent implements OnInit {
  constructor(
    private questionService: QuestionService,
    private topicService: TopicService,
    private sideBarService: SideBarService,
    private answerService: AnswerService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  Loading = false;

  Topics: Topic[] = [];

  topicTitle: string = '';

  possibleRoutes: string[] = [];

  Questions: QuestionByTopic[] = [];

  reponseList: TestAnswers[] = [];

  UniqueSelectedList: string[] = [];

  suggestionSrcList: QuestionSuggestionSrcWithJoin[] = [];

  subQuestionList: string[] = [];

  //testid!: string;

  ngOnInit(): void {
    const email = localStorage.getItem('email');
    this.route.paramMap.subscribe((data) => {
      if (data.get('idTest'))
        this.sideBarService.invokeIdTest(data.get('idTest'));
    });
    // @ts-ignore
    /*this.testService.getTestByGuestEmail(email).subscribe((data) => {
      this.testid = data.id;
    });*/

    this.topicService.getAllTopics().subscribe({
      next: (data) => {
        this.Topics = data;
        this.Topics.map((topic) => {
          if (topic.id != null) {
            this.possibleRoutes.push(topic.id);
          }
        });
      },
      error: (e) => {},
    });
    this.fetchQuestions();
  }

  fetchQuestions() {
    const id = this.route.snapshot.params['id'];
    this.questionService.getQuestionByTopicId(id).subscribe({
      next: (data) => {
        this.Questions = data;

        this.topicTitle = data[0].topic.title;
        this.Questions.map((value) => {
          value.suggestions.map(sugg =>{
            sugg.intRefS = value.intRefQ = parseInt(sugg.refS.replace(/\./g, '')); 
          })

          value.suggestions.sort((a, b) => {
            const firstDigits = a.intRefS.toString().split('');
            const secondDigits = b.intRefS.toString().split('');
  
            for (let i = 0; i < Math.min(firstDigits.length, secondDigits.length); i++) {
              const firstDigit = parseInt(firstDigits[i]);
              const secondDigit = parseInt(secondDigits[i]);
  
              if (firstDigit !== secondDigit) {
                return firstDigit - secondDigit;
              }
            }
            return a.intRefS - b.intRefS;
          })
          // replace reference to a number
          value.intRefQ = parseInt(value.refQ.replace(/\./g, '')); 
          if (value.suggestionSrc?.length > 0) {
            value.display = false;
            this.suggestionSrcList.push({
              questionId: value.id,
              suggestionsSrc: value.suggestionSrc,
            });
          } else {
            value.display = true;
          }
        });

        this.Questions.sort((a, b) => {
          const firstDigits = a.intRefQ.toString().split('');
          const secondDigits = b.intRefQ.toString().split('');

          for (let i = 0; i < Math.min(firstDigits.length, secondDigits.length); i++) {
            const firstDigit = parseInt(firstDigits[i]);
            const secondDigit = parseInt(secondDigits[i]);

            if (firstDigit !== secondDigit) {
              return firstDigit - secondDigit;
            }
          }
          return a.intRefQ - b.intRefQ;
        });

        data.forEach((q) => {
          this.reponseList.push({
            questionId: q.id,
            suggestions: [],
          });
        });
      },
      error: (e) => {},
    });
  }
  PostData(form: NgForm) {
    const id = this.route.snapshot.params['id'];
    const idTest = this.route.snapshot.params['idTest'];
    const data = {
      answers: this.reponseList,
      testId: idTest,
    };
    this.answerService.PostAnswer(data).subscribe({
      next: (data) => {
        let index = this.possibleRoutes.indexOf(id);
        if (this.possibleRoutes[index + 1] == undefined) {
          //this.testService.calculate(idTest).subscribe((value) => {});
          localStorage.setItem('ResultTest', idTest);
          this.router.navigate(['/result', idTest]).then(() => {
            this.fetchQuestions();
            //window.location.reload();
          });
        }
        this.router
          .navigate(['/exam', idTest, 'topic', this.possibleRoutes[index + 1]])
          .then(() => {
            this.fetchQuestions();
            //window.location.reload();
          });
      },
      error: (e) => {},
    });
    this.reponseList = [];
  }

  onSelectChange(questionId: string, select: HTMLSelectElement) {
    const result = this.reponseList.find(
      (value) => value.questionId === questionId
    );

    //test Unique Select
    if (select.value) {
      let resultTest = this.UniqueSelectedList.find((value) => select.value);

      if (!resultTest) {
        //Push new value
        this.UniqueSelectedList.push(select.value);
        result?.suggestions.push(select.value);
      } else {
        //Delete occurence
        let index = result?.suggestions.indexOf(this.UniqueSelectedList[0]);
        this.UniqueSelectedList.splice(index!, 1);
        //Push new value
        result?.suggestions.splice(index!, 1);
        this.UniqueSelectedList.push(select.value);
        result?.suggestions.push(select.value);
      }
      this.checkToAddSubQuestion(questionId, result?.suggestions ?? []);
    }
  }

  checkToAddSubQuestion(questionId: string, suggestions: string[]) {
    var continuate: boolean = true;
    const parentQuestion = this.Questions.find(
      (question) => question.id === questionId
    );

    const questions = this.Questions.filter(
      (value) => value.suggestionSrc.length > 0
    );

    questions.forEach((question) => {
      const test = parentQuestion?.suggestions
        .map((mapping) => mapping.id)
        .includes(question.suggestionSrc[0].id);
      if (test) {
        if (suggestions?.length < 1) {
          question.display = false;
          return;
        }

        suggestions?.forEach((suggestionId) => {
          if (
            question.suggestionSrc.map((src) => src.id).includes(suggestionId)
          ) {
            question.display = true;
            continuate = false;
            return;
          } else if (continuate) {
            question.display = false;
          }
        });
      }
    });
  }

  // this is for checkboxes only
  onCheckboxChange(questionId: string, suggestionId: string) {
    //Get QuestionID
    //Get suggestion table where table id equal question id
    const result = this.reponseList.find(
      (value) => value.questionId === questionId
    );

    const checkedOrNot = result?.suggestions.find(
      (data) => suggestionId == data
    );

    //Push and delete from suggestion with Test
    if (!checkedOrNot) {
      result?.suggestions.push(suggestionId);
    } else {
      const index = result?.suggestions.indexOf(suggestionId);

      if (index != undefined) result?.suggestions.splice(index, 1);
    }
    this.checkToAddSubQuestion(questionId, result?.suggestions ?? []);
  }
}
