import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Answer, AnswersDto, Question, Questions, ResponseTest, ResponseTopics} from "../../../core/models/Answers.dto";
import {environment} from "../../../../environments/environment";
import {TestService} from "../../../core/services/test.service";
import { Activity_area_enumMapping } from 'src/app/core/enum/ActivityArea.enum';
import { Workforce_enumMapping } from 'src/app/core/enum/Workforce.enum';


@Component({
  selector: 'app-test-details',
  templateUrl: './test-details.component.html',
  styleUrls: ['./test-details.component.css']
})

export class TestDetailsComponent implements OnInit{
  @ViewChild("checkbox00") checkbox!: ElementRef<HTMLInputElement>;
  constructor(private testService:TestService, private router: Router, private route: ActivatedRoute) {
  }

  Loading = false;
  public activity_area_enumMapping = Activity_area_enumMapping
  public workforce_enumMapping = Workforce_enumMapping;
  responseTest !: ResponseTest;

  responseTopics !: ResponseTopics[];

  ngOnInit(): void {

    const id = this.route.snapshot.params['id'];


    this.testService.getTestById(id).subscribe({
        next: (data) => {
          this.responseTest=data.test
          this.responseTopics=data.topic
        },
        error: (e) => {
        }
      })
  }
}

class TestDto {
  id!: string
}

