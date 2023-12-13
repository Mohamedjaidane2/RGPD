import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
import {NgForm} from "@angular/forms";
import {gradingScaleRequest} from "../../../core/models/GradingScale.model";
import {GradingScaleService} from "../../../core/services/gradingScale.service";

@Component({
  selector: 'app-creation-grading-scale-modal',
  templateUrl: './creation-grading-scale-modal.component.html',
  styleUrls: ['./creation-grading-scale-modal.component.css']
})
export class CreationGradingScaleModalComponent implements OnInit{
  constructor(private gradingScaleService:GradingScaleService,private router:Router) {
  }
  ngOnInit(): void {
  }



  AddGradingScale(form:NgForm){

    const requestdto: gradingScaleRequest = {
      penaltyAmount : form.value.penaltyAmount,
      penaltyJail : form.value.penaltyJail,
      penaltyStatus : form.value.penaltyStatus,
    }

    this.gradingScaleService.addGradingScale(requestdto).subscribe(response=>{
        this.router.navigate(['/admin/dashboard/gradingscale'])
        //window.location.reload()
      })



  }

}
