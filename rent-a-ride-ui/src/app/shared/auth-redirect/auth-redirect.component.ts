import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-auth-redirect',
  template: ''
})
export class AuthRedirectComponent implements OnInit {

  constructor(
    private router: Router,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    const isAuthenticated = this.isUserAuthenticated();

    if (isAuthenticated) {
      this.router.navigate(['/vehicles']); // Redirect authenticated users to vehicles
    } else {
      this.router.navigate(['/login']); // Redirect unauthenticated users to login
    }
  }

  private isUserAuthenticated(): boolean {
    return !!this.tokenService.token;
  }
}
