import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CalendlyUserModel } from '../../../core/models/Calendly.model';

declare var Calendly: any;

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
})
export class CalendarComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    Calendly.initInlineWidget({
      url: environment.CALENDLY_URL,
      parentElement: document.querySelector('.calendly-inline-widget'),
    });
  }
}
