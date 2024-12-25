import { Injectable } from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  // Check if window is defined to ensure we're on the client side
  private isBrowser = typeof window !== 'undefined';

  set token(token: string) {
    if (this.isBrowser) {
      localStorage.setItem('token', token);
    }
  }

  get token() {
    if (this.isBrowser) {
      return localStorage.getItem('token') as string;
    }
    return "";
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    if (!this.token) {
      return false;
    }
    const jwtHelper : JwtHelperService = new JwtHelperService();
    const isExpired = jwtHelper.isTokenExpired(this.token);
    if (isExpired) {
      this.token = "";
      localStorage.clear()
      return false
    }
    return true;

  }
}
