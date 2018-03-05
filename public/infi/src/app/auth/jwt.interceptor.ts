import 'rxjs/add/operator/do';
import { Injectable } from '@angular/core';
import {
HttpRequest,
HttpHandler,
HttpEvent,
HttpInterceptor
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs/Observable';
import { HttpResponse } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
export class JwtInterceptor implements HttpInterceptor {
constructor(public auth: AuthService) {}
intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent
        <any>> {    
            return next.handle(request).do((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                // do stuff with response if you want
                }
                }, (err: any) => {
                if (err instanceof HttpErrorResponse) {
                if (err.status === 401) {
                this.auth.collectFailedRequest(request);
                }
                }
                });
                }

                }