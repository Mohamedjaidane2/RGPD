import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { TestService } from '../test.service';

export const resultGuard = (next: ActivatedRouteSnapshot) => {
  const router = inject(Router);
  const testService = inject(TestService);
  const idTest = next.params['idTest'];
  testService.checkById(idTest).subscribe({
    next: (check) => {
      if (check) return true;

      router.navigate(['/']);
      return false;
    },
    error: (error) => {
      router.navigate(['/']);
      return false;
    },
  });
};
