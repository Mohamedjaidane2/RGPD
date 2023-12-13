import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Topic } from 'src/app/core/models/TopicModel';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-confirm-delete',
  templateUrl: './confirm-delete.component.html',
  styleUrls: ['./confirm-delete.component.css'],
  template: ` Topic: {{ topic }} `,
})
export class ConfirmDeleteComponent {
  constructor() {}
  @Output('confirmDelete') confirmDelete = new EventEmitter();

  deleteTopic() {
    this.confirmDelete.emit();
  }
}
