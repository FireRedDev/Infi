import { Http } from '@angular/http';
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
  constructor(private http: Http, private router:Router, private user:UserService) {
   }

  results: string[];
  

  ngOnInit() {
    console.log('hit');
  }

  loginUser(e) {
    e.preventDefault();
    console.log(e);
    var username = e.target.elements[0].value;
    var password = e.target.elements[1].value;

     // Make the HTTP request:
     // Md5.hashStr(password)
     
     const body = {"username": username,"password":password};
     console.log(body);
     this.http
       .post('http://localhost:8080/api/service/login', body)
       .map((data: any) => {
        if (data) {
            if (data.status === 201) {
                return [{ status: data.status, json: data }]
            }
            else if (data.status === 200) {
                return [{ status: data.status, json: data }]
            }
        }
    }).catch((error: any) => {
        if (error.status < 400 ||  error.status ===500) {
          alert("Falscher Username oder falsches Passwort eingegeben!");
          return Observable.throw(new Error(error.status));
        }
    })
       // See below - subscribe() is still necessary when using post().
       .subscribe(data => {
          console.log(data);
          if(data[0].json["_body"]>0){
          this.user.setUserLoggedIn();
          localStorage.setItem('currentUser',data[0].json["_body"]);
          this.router.navigate(['dashboard']);
          }
          else{
            alert("Falscher Username oder falsches Passwort eingegeben!");
          }
        err=>{
          console.log("error");
          alert("Falscher Username oder falsches Passwort eingegeben!");
        }
      });    
  }

}
