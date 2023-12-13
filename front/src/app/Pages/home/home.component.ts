import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
is_checked!:boolean

  ngOnInit(): void {
  this.is_checked=false
  }

  check(){
  this.is_checked=!this.is_checked;
  }
}
