import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-confirm-question-delete',
  templateUrl: './confirm-question-delete.component.html',
  styleUrls: ['./confirm-question-delete.component.css']
})
export class ConfirmQuestionDeleteComponent {
  constructor() {}
  @Output('confirmDelete') confirmDelete = new EventEmitter();

  deleteQuestion() {
    this.confirmDelete.emit();
  }
}
