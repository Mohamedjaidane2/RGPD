import {Component, OnInit} from '@angular/core';
import {ResponseDto} from "../../core/models/Response.dto";
import {TestService} from "../../core/services/test.service";


@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.css']
})
export class TestsComponent implements OnInit {
  constructor(private testServices :TestService) {
  }

  filteredItems!:any[]
  tests: ResponseDto[] = [];
  isLoading = false;
  modalStatus = false

  openModal() {
    return this.modalStatus = true
  }

  ngOnInit(): void {

    this.isLoading = true;
    this.testServices.getAllTests().subscribe({
        next: (data) => {
          this.tests = data
          this.tests.map(value => {
          value.score=Math.round(value.score)
          })
          this.isLoading = false
          this.filteredItems = this.tests;
        },
        error: (e) => {
          this.isLoading = false
        }
      })
  }
//search section
  searchTerm!: string;
  filterItems() {
    this.filteredItems = this.tests.filter(item =>
      item.guest.email?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

}

