import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
// import { AuthService } from './myServices/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'rent-a-ride-ui';
  isLoading = true;

  constructor() {}

  ngOnInit() {
    // this.authService.checkAuthStatus()



  }
}
