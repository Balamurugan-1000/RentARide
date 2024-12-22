import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authStatusSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.getAuthStatusFromLocalStorage());
  private apiUrl: string = 'https://your-api-url.com'; // You can keep this if you plan to implement API routes later

  constructor() {}

  // Get the authentication status from localStorage (or a default value)
  private getAuthStatusFromLocalStorage(): boolean {
    const authStatus = localStorage.getItem('authStatus');
    return authStatus ? JSON.parse(authStatus) : false;
  }

  // Check if the user is authenticated by checking localStorage
  checkAuthStatus(): Observable<any> {
    // Simulate a successful auth check (you can replace this with any custom logic)
    return new Observable((observer) => {
      const isAuthenticated = this.getAuthStatusFromLocalStorage();
      if (isAuthenticated) {
        observer.next({ status: 'authenticated' });
      } else {
        observer.next({ status: 'unauthenticated' });
      }
      observer.complete();
    });
  }

  // Get the current authentication status as an observable
  getAuthStatus(): Observable<boolean> {
    return this.authStatusSubject.asObservable();
  }

  // Log the user in by setting a flag in localStorage
  login(): void {
    localStorage.setItem('authStatus', JSON.stringify(true));
    this.authStatusSubject.next(true);
    console.log('User logged in');
  }

  // Log the user out by removing the flag from localStorage
  logout(): void {
    localStorage.removeItem('authStatus');
    this.authStatusSubject.next(false);
    console.log('User logged out');
  }
}
