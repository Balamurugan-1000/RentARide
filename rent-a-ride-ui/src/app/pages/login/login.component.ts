import { TokenService } from './../../services/token/token.service';
import { FormsModule } from '@angular/forms';
import {  CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AuthenticationRequest, AuthenticationResponse } from '../../services/models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(
    private router: Router,
    private authService : AuthenticationService,
    private tokenService : TokenService
  ){}
register() {
  this.router.navigate(["register"])
}
login() : void {

  this.errorMsg=[]
  this.authService.authenticate({
    body:this.authRequest,
  }).subscribe({
    next: (res: AuthenticationResponse) => {
      console.log(res)
      this.tokenService.token = res.token as string
      this.router.navigate(["vehicles"])
    },
    error:(err) => {
      console.log(err.error.validationErrors)
      if(err.error.validationErrors){
        this.errorMsg = err.error.validationErrors
      }else{
        this.errorMsg.push(err.error.error)
      }
    }
  })
}


  authRequest : AuthenticationRequest = { email : "" , password : ""}
  errorMsg: Array<String> = [];
}
