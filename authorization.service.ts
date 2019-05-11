import { Injectable } from '@angular/core';
import { WebService } from './web.service';
import { Route, Router } from '@angular/router';
import { SERVER } from '../config/server.config';
import { map } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  constructor(private webService: WebService, private router: Router) {}

  login(user) {
    // tslint:disable-next-line:max-line-length
    return this.webService.post(SERVER.AUTHENTICATE, user, this.webService.defaultOptions());
  }

  getAccount() {
    return this.webService.get(SERVER.ACCOUNT, this.webService.JSONOptions(this.getToken()));
  }

  logout() {
    localStorage.removeItem('mfx-token');
    sessionStorage.removeItem('mfx-token');
  }

  getToken() {
    // tslint:disable-next-line:max-line-length
    return sessionStorage.getItem('mfx-token')
      ? sessionStorage.getItem('mfx-token')
      : localStorage.getItem('mfx-token')
      ? localStorage.getItem('mfx-token')
      : '';
  }

  getLanguage() {
    // tslint:disable-next-line:max-line-length
    return sessionStorage.getItem('mfx-lang')
      ? sessionStorage.getItem('mfx-lang')
      : localStorage.getItem('mfx-lang')
      ? localStorage.getItem('mfx-lang')
      : 'en';
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);
    if (decoded['exp'] === undefined) {
      return null;
    }
    const date = new Date(0);
    date.setUTCSeconds(decoded['exp']);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) {
      token = this.getToken();
    }
    if (!token) {
      return true;
    }
    const date = this.getTokenExpirationDate(token);
    if (date === undefined) {
      return false;
    }
    const expired = !(date.valueOf() > new Date().valueOf());
    if (expired) {
      this.logout();
    }
    return expired;
  }

  isLogged() {
    return this.webService.get(SERVER.AUTHENTICATE, this.webService.textOptions(this.getToken())).pipe(
      map(res => {
        const logged = res && !this.isTokenExpired();
        if (logged) {
          return true;
        } else {
          // this.router.navigate(['/']);
          return false;
        }
      })
    );
  }

  // authetication social --------------------------------------------------------------------------------------------------

  loginWithToken(jwt, rememberMe) {
    if (jwt) {
      this.storeAuthenticationToken(jwt, rememberMe);
      this.router.navigate(['/curso']);
      return Promise.resolve(jwt);
    } else {
      console.log('--> error social ---');
    }
  }

  storeAuthenticationToken(jwt, rememberMe) {
    if (rememberMe) {
      sessionStorage.setItem('mfx-token', jwt);
    } else {
      sessionStorage.setItem('mfx-token', jwt);
    }
  }

  loginWithTokens(jwt, rememberMe) {
    return this.loginWithToken(jwt, rememberMe);
  }

  activateAccount(key) {
    return this.webService.get(SERVER.ACTIVATE + key);
  }
}
