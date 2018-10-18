import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import 'rxjs/add/operator/retry';
import { Benutzer } from './benutzer.model';
import { Observable } from 'rxjs';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  public benutzer: Benutzer;

  constructor(public rest: RestService, private router: Router, private user: UserService) {
    this.rest = rest;
    this.user = user;
  }

  results: string[];

  ngOnInit() {
  }

  loginUser(e) {
    e.preventDefault();
    const email = e.target.elements[0].value;
    const password = e.target.elements[1].value;

    const body = { 'email': email, 'password': password };

    //workaround
    var rt = this.router;
    var u = this.user;

    var rest = this.rest;
    this.rest.login(body).
      subscribe({
        next(data) {
          if (data != null) {
            u.setUserLoggedIn();
            localStorage.setItem('currentUser', JSON.parse(data.userID));
            localStorage.setItem('token', JSON.stringify(data.token));
            rt.navigate(['dashboard']);
          }
          else {
            rest.showErrorMessage("Error", "Falsche Email oder falsches Passwort eingegeben!");
          }
        },
        error(error) {
          if (error.status < 400 || error.status === 500) {
            rest.showErrorMessage("Error", "Falsche Email oder falsches Passwort eingegeben!");
            return Observable.throw(new Error(error.status));
          }
        },
        complete() {
          console.log('Completed');
        }
      });
  }
}