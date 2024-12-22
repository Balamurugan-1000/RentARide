import { Injectable } from '@angular/core';

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
}
