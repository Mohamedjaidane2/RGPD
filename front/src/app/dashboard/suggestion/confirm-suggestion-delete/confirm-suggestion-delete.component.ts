import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-confirm-suggestion-delete',
  templateUrl: './confirm-suggestion-delete.component.html',
  styleUrls: ['./confirm-suggestion-delete.component.css'],
})
export class ConfirmSuggestionDeleteComponent {
  @Output('confirmDelete') confirmDelete = new EventEmitter();

  deleteSuggestion() {
    this.confirmDelete.emit();
  }
}
