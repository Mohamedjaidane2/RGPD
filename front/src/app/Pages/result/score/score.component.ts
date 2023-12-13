import { Component, NgModule, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { TestModel } from '../../../core/models/Test.model';
import { TestService } from '../../../core/services/test.service';
@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.css'],
})
export class ScoreComponent implements OnInit {
  constructor(
    private testService: TestService,
    private route: ActivatedRoute
  ) {}
  percentage: number =0;
  test!: TestModel;

  get formattedDashArray():number {
    return Math.round(this.test.score) ;
  }

  ngOnInit(): void {
    const idTest = this.route.snapshot.params['idTest'];
    this.testService.calculate(idTest).subscribe((value) => {
      this.testService.getResultTestById(idTest).subscribe((res) => {
        this.test = res;
        this.test.score = Math.round(this.test.score);
      });
    });

    //let id = localStorage.getItem('ResultTest');
  }
  // class implementation
}
