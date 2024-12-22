import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideClientHydration } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, provideHttpClient, withFetch, withInterceptorsFromDi} from '@angular/common/http';

import { routes } from './app.routes';
import { TokenInterceptor } from './interceptors/http-token.interceptor';
import { TokenExpiryInterceptor } from './interceptors/token-expiry.interceptor';

export const appConfig: ApplicationConfig = {

  providers: [
    provideHttpClient(withInterceptorsFromDi() , withFetch()),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenExpiryInterceptor,
      multi: true
    }
  ],
};
