import { Component, OnInit, Input, Output } from '@angular/core';
import { RestService } from '../rest.service';
import {UserService} from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EventEmitter } from '@angular/core';
declare var $: any;

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  password1="";
  password2="";
  msg = "";
  err="";

  @Output() changeView: EventEmitter<string> = new EventEmitter();

  constructor(public rest: RestService,private user: UserService, private route: ActivatedRoute, private router: Router) {
    this.rest=rest;
  }

  ngOnInit() {
  }

  changePwd(){
      if(this.password1==this.password2&&this.password1!=""){
        const body = {'id': localStorage.getItem('currentUser'), 'password': this.password1};
        this.rest.changePassword(body).subscribe(data => {
          this.msg = "Passwort wurde geändert.";
          console.log("message: "+data);
          $('#pwdModal').modal('hide');
        });
      }
      else{
        this.err="Die beiden Passwörter sind nicht ident, versuche es nochmal!";
      }
   
  }
}
