import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpHeaders } from '@angular/common/http';
import { TokenService } from '../services/token/token.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.tokenService.token;
    if (token) {
      const clonedReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });

      clonedReq.headers.append('Content-Type', 'application/json');
      return next.handle(clonedReq);
    } else {
      console.log('No token');

      return next.handle(req);
    }
  }
}
