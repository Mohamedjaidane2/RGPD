import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Penalty_status_enumMapping } from 'src/app/core/enum/PenaltyStatus.enum';
import { SuggestionModel } from '../../core/models/Suggestion.model';
import { SuggestionService } from '../../core/services/suggestion.service';

@Component({
  selector: 'app-suggestion',
  templateUrl: './suggestion.component.html',
  styleUrls: ['./suggestion.component.css'],
})
export class SuggestionComponent {
  idToDelete!: string;

  constructor(
    private router: Router,
    private suggestionService: SuggestionService
  ) {}
  public PenaltyStatusMapping = Penalty_status_enumMapping;
  filteredItems!: SuggestionModel[];
  Topics: SuggestionModel[] = [];
  isLoading = false;

  ngOnInit(): void {
    this.isLoading = true;
    this.getAllSeggestions();
  }
  getAllSeggestions() {
    this.suggestionService.getAllSeggestions().subscribe({
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
      item.refS?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  /*deleteSuggestion(id: any) {
    this.isLoading = true;
    this.suggestionService.deleteSuggestion(id).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.getAllSeggestions();
      },
      error: (error) => {
        this.isLoading = false;
      },
    });
  }*/

  saveSuggestionToDelete(item: string) {
    this.idToDelete = item;
  }
  confirmDelete() {
    this.isLoading = true;
    this.suggestionService.deleteSuggestion(this.idToDelete).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.getAllSeggestions();
      },
      error: (error) => {
        this.isLoading = false;
      },
    });
  }
}
