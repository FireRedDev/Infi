// src/app/auth/auth.service.ts
import { Injectable } from '@angular/core';
import decode from 'jwt-decode';
import { HttpRequest } from '@angular/common/http';
@Injectable()
export class AuthService {
public getToken(): string {
//beim login in java token generieren, mitgeben, abspeichern mit localStorage.setitem() und hier returnen.
//tabelle machen wo ich userid und token abspeichere um von token auf user auf passwort im containrequestfilter zu schließen
return localStorage.getItem('token');
}
public isAuthenticated(): boolean {
// get the token
const token = this.getToken();
// return a boolean reflecting 
// whether or not the token is expired
return true;//tokenNotExpired(null, token);
}
cachedRequests: Array<HttpRequest
    <any>> = [];
        public collectFailedRequest(request): void {
        this.cachedRequests.push(request);
        }
        public retryFailedRequests(): void {
        // retry the requests. this method can
        // be called after the token is refreshed
        }
        }