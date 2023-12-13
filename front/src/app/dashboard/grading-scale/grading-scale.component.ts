import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SuggestionModel } from '../../core/models/Suggestion.model';
import { environment } from '../../../environments/environment';
import { gradingScaleResponse } from '../../core/models/GradingScale.model';

@Component({
  selector: 'app-grading-scale',
  templateUrl: './grading-scale.component.html',
  styleUrls: ['./grading-scale.component.css'],
})
export class GradingScaleComponent implements OnInit {
  constructor(private router: Router, private http: HttpClient) {}

  filteredItems!: gradingScaleResponse[];
  gradingScales: gradingScaleResponse[] = [];
  isLoading = false;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    }),
  };

  modalStatus = false;
  openModal() {
    return (this.modalStatus = true);
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.getAllGradingScale();
  }
  getAllGradingScale() {
    this.http
      .get<Array<gradingScaleResponse>>(
        `${environment.APP_PORT + `/api/grading-scale/all`}`,
        this.httpOptions
      )
      .subscribe({
        next: (data) => {
          this.gradingScales = data;
          this.isLoading = false;
          this.filteredItems = this.gradingScales;
        },
        error: (e) => {
          this.isLoading = false;
        },
      });
  }
  //search section
  searchTerm!: string;
  filterItems() {
    this.filteredItems = this.gradingScales.filter((item) =>
      item.title?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  deleteGradingScale(id: string) {
    this.isLoading = true;
    this.http
      .delete(
        `${environment.APP_PORT + `/api/grading-scale/${id}/delete`}`,
        this.httpOptions
      )
      .subscribe({
        next: (response) => {
          this.isLoading = false;
          this.getAllGradingScale();
        },
        error: (error) => {
          this.isLoading = false;
        },
      });
  }
}
