//import { Http, Headers, RequestOptions } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import {Md5} from 'ts-md5/dist/md5';
import 'rxjs/add/operator/retry';
import * as $ from 'jquery';
import { Benutzer } from './benutzer.model';
import {Observable} from 'rxjs/Rx';

@Component({
selector: 'app-login-form',
templateUrl: './login-form.component.html',
styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
public benutzer: Benutzer;
constructor(public http: HttpClient,private router: Router, private user: UserService) {
this.http=http;
}

results: string[];


ngOnInit() {
}

loginUser(e) {
e.preventDefault();
const personalnr = e.target.elements[0].value;
const password = e.target.elements[1].value;

// Make the HTTP request:
// Md5.hashStr(password)

const body = {'personalnr': personalnr, 'password': password};
// console.log(body);

// const headers = new Headers();
// headers.append('Content-Type', 'application/json');
// headers.append('Authorization', 'Basic cGFzc21l');

// const options = new RequestOptions({headers: headers});
this.http
.post('http://localhost:8080/api/service/login', body)
.map((data: any) => {
if (data) {
return data;
}
}).catch((error: any) => {
if (error.status < 400 ||  error.status === 500) {
alert('Falsche Personalnummer oder falsches Passwort eingegeben!');
return Observable.throw(new Error(error.status));
}
})
.subscribe(data => {   
console.log(data);
this.user.setUserLoggedIn();
localStorage.setItem('currentUser',JSON.parse(data.userID));
localStorage.setItem('token',JSON.stringify(data.token));
this.router.navigate(['dashboard']);
err=>{
console.log("error");
alert("Falsche Personalnummer oder falsches Passwort eingegeben!");
}
});    
}

}
