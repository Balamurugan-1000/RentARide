import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services/authentication.service';
import { Confirm$Params } from '../../services/fn/authentication/confirm';
import { FormsModule } from '@angular/forms';
import { CodeInputModule } from 'angular-code-input';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-account-activate',
  standalone: true,
  imports : [FormsModule , CodeInputModule,CommonModule],
  templateUrl: './account-activate.component.html',
  styleUrls: ['./account-activate.component.scss']
})
export class AccountActivateComponent {

  message: string = '';
  isOk : boolean = false
  submitted : boolean = false

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  activate(token : string) {
    this.submitted = true

    const params: Confirm$Params = { token: token };

    this.authService.confirm(params).subscribe({
      next: () => {
        this.isOk = true
      this.message = 'Account activated successfully';
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error(JSON.parse(err.error));
        this.message = JSON.parse(err.error).error;
      }
    });
  }
}
