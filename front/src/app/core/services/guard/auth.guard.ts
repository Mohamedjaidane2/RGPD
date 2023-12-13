import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { map, of } from 'rxjs';
import { AuthService } from '../auth/auth.service';

export const authGuard = (next: ActivatedRouteSnapshot) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  authService.checkAuth().subscribe({
    next: (checkAuth) => {
      if (checkAuth) {

        return true;
      }
      else {
        router.navigate(['/', 'admin', 'login']);
        return false;
      }
    },
    error: (error) => {
      router.navigate(['/', 'admin', 'login']);
      return false;
    },
  });
};
