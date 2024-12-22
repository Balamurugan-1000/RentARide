import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthenticationRequest } from '../../services/models/authentication-request';
import { AuthenticationResponse, RegisterationRequest } from '../../services/models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  constructor(
    private router :Router,
    private authService : AuthenticationService,
    private tokenService : TokenService
  ){}
login() {
  this.router.navigate(["login"])
}
register() {
  this.errorMsg = []
  this.authService.register({
    body : {
      email :this.registerRequest.email,
      password : this.registerRequest.password,
      firstname : this.registerRequest.firstname,
      lastname : this.registerRequest.lastname
    }
  }).subscribe({
    next:()=>{
      this.errorMsg = []
      this.router.navigate(["activate-account"])
    },
    error:(err) => {
      if(err.error.validationErrors){
        this.errorMsg = err.error.validationErrors
      }else{
        this.errorMsg.push(err.error.error)
      }
      console.log(err)
    }
  })
}


  registerRequest : RegisterationRequest = { email : "" , password : "" ,firstname :"" , lastname : ""}

  errorMsg: Array<String> = [];

}
