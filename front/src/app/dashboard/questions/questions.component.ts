import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { QuestionService } from '../../core/services/question.service';
import {
  GetAllQuestionsDto,
  QuestionModel,
} from '../../core/models/Question.model';
import {
  Question_type_enum,
  Question_type_enumMapping,
} from 'src/app/core/enum/QuestionType.enum';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
})
export class QuestionsComponent implements OnInit {

  public question_type_enumMapping = Question_type_enumMapping;
  idToDelete!: string;

  constructor(private questionService: QuestionService) {}

  filteredItems!: GetAllQuestionsDto[];
  questions: GetAllQuestionsDto[] = [];
  onlyQuestionsList: QuestionModel[] = [];
  isLoading = false;
  

  ngOnInit(): void {
    this.isLoading = true;
    this.getAllQuestions();
  }
  showType(type: Question_type_enum): string {
    switch (type) {
      case Question_type_enum.CHOIX_MULTIPLE:
        return 'Choix multiple';
      case Question_type_enum.CHOIX_UNIQUE:
        return 'Choix unique';
      default:
        return "N'est pas défini";
    }
  }
  getAllQuestions() {
    this.questionService.getAllQuestions().subscribe({
      next: (data) => {
        this.questions = data;
        this.isLoading = false;
        this.questions.map((value) => {
          value.questions.map((subValue) => {
            this.onlyQuestionsList.push(subValue);
          });
        });
        this.filteredItems = this.questions;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });
  }
  //search section
  searchTerm!: string;

  filterItems() {
    this.filteredItems = this.questions.filter(
      (item) =>
        item.questions.some((subitem) =>
          subitem.refQ?.toLowerCase().includes(this.searchTerm.toLowerCase())
        ) ||
        item.topic.title?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  deleteQuestion(id: any) {
    this.isLoading = true;
    this.questionService.deleteQuestion(id).subscribe(
      (response) => {
        this.isLoading = false;
        this.getAllQuestions();
        //window.location.reload()
      },
      (error) => {
        this.isLoading = false;
      }
    );
  }
  
  saveQuestionToDelete(item: string) {
    this.idToDelete = item;
  }
  confirmDelete() {
    this.questionService.deleteQuestion(this.idToDelete).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {},
    });
  }
}
