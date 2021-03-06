import { Injectable } from '@angular/core';
import { HttpRequest } from '@angular/common/http';
/**
 * Auth Service
 */
@Injectable()
export class AuthService {
  /**
   * get the Token
   */
  public getToken(): string {
    return localStorage.getItem('token');
  }
  /**
   * is this User Authenticated
   */
  public isAuthenticated(): boolean {
    // get the token
    const token = this.getToken();
    // return a boolean reflecting 
    // whether or not the token is expired
    return true;//tokenNotExpired(null, token);
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