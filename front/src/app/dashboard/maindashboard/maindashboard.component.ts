import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth/auth.service';

@Component({
  selector: 'app-maindashboard',
  templateUrl: './maindashboard.component.html',
  styleUrls: ['./maindashboard.component.css'],
})
export class MaindashboardComponent implements OnInit {
  constructor(private router: Router, private authService: AuthService) {}
  ngOnInit(): void {
    this.authService.checkAuth().subscribe({
      next: (data) => {
        if (!data) {
          this.router.navigate(['/', 'admin', 'login']);
        }
      },
    });
  }
}
