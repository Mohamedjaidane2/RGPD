import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../core/services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(private AuthService: AuthService, private router: Router) {}
  ngOnInit(): void {
    if(localStorage.getItem('token'))
    this.AuthService.checkAuth().subscribe({next:(data)=>{
      if(data) this.router.navigate(['/','admin','dashboard'])
    }})
  }

  isLoading = false;
  err!: string;

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const email = form.value.email;
    const password = form.value.password;
    this.isLoading = true;
    this.AuthService.login(email, password).subscribe({
      next: (response) => {
        this.isLoading = false;
        if (response.token) localStorage.setItem('token', response.token);

        this.router.navigate(['/admin/dashboard']);
      },
      error: (err) => {
        this.err = 'These credentials do not match our records !';
        this.isLoading = false;

      },
    });
    /*this.AuthService.login(email, password).subscribe(
      (response) => {
        this.isLoading = false;
        if (response.token) localStorage.setItem('token', response.token);
        this.router.navigate(['/admin/dashboard']);
      },
      (error) => {
        this.err = 'These credentials do not match our records !';
        this.isLoading = false;
      }
    );*/
    form.reset();
  }
}
