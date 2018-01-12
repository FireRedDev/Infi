// src/app/auth/auth.service.ts
import { Injectable } from '@angular/core';
import decode from 'jwt-decode';
import { HttpRequest } from '@angular/common/http';
@Injectable()
export class AuthService {
  public getToken(): string {
    return localStorage.getItem('token');
  }
  public isAuthenticated(): boolean {
    // get the token
    const token = this.getToken();
    // return a boolean reflecting 
    // whether or not the token is expired
    return tokenNotExpired(null, token);
  }
  cachedRequests: Array<HttpRequest<any>> = [];
  public collectFailedRequest(request): void {
      this.cachedRequests.push(request);
    }
  public retryFailedRequests(): void {
      // retry the requests. this method can
      // be called after the token is refreshed
    }
}