import { Http } from '@angular/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import {Md5} from 'ts-md5/dist/md5';
import 'rxjs/add/operator/retry';
import * as $ from 'jquery';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  constructor(private http: Http, private router:Router, private user:UserService) { }

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
       // See below - subscribe() is still necessary when using post().
       .subscribe(data => {
         console.log(data);
        if(data["_body"]=="true") {
          this.user.setUserLoggedIn();
          this.router.navigate(['dashboard']);
        }
        else{
          alert("wrong username or password");
        }
      });    
  }

}
