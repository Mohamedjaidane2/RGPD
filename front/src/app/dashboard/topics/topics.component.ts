import { Component, Inject, OnInit } from '@angular/core';
import { Topic } from '../../core/models/TopicModel';
import { TopicService } from '../../core/services/topic.service';
import { CreationModalComponent } from './creation-modal/creation-modal.component';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.css'],
})
export class TopicsComponent implements OnInit {
  constructor(private topicService: TopicService) {}
  filteredItems!: Topic[];
  Topics: Topic[] = [];
  isLoading = false;
  idToDelete!: string;
  modalStatus = false;
  openModal() {
    return (this.modalStatus = true);
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.getAllTopics();
  }
  getAllTopics() {
    this.topicService.getAllTopics().subscribe({
      next: (data) => {
        this.Topics = data;
        this.isLoading = false;
        this.filteredItems = this.Topics;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });
  }
  //search section
  searchTerm!: string;
  filterItems() {
    this.filteredItems = this.Topics.filter((item) =>
      item.title?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
  saveTopicToDelete(item: string) {
    this.idToDelete = item;
  }
  confirmDelete() {
    this.topicService.deleteTopic(this.idToDelete).subscribe({
      next: (response) => {
        window.location.reload();
      },
      error: (error) => {},
    });
  }
}
