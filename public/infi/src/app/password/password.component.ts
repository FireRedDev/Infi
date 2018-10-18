import { Component, OnInit, Output } from '@angular/core';
import { RestService } from '../rest.service';
import { EventEmitter } from '@angular/core';
declare var $: any;

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  password1 = "";
  password2 = "";
  msg = "";
  err = "";

  @Output() changeView: EventEmitter<string> = new EventEmitter();

  constructor(public rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {
  }

  //Passwort ändern
  changePwd() {
    if (this.password1 == this.password2 && this.password1 != "") {
      const body = { 'id': localStorage.getItem('currentUser'), 'password': this.password1 };
      this.rest.changePassword(body).subscribe(data => {
        this.rest.showSuccessMessage("Erfolg", "Passwort wurde geändert.");
        this.changeView.emit("month");
      });
    }
    else {
      this.rest.showErrorMessage("Error", "Die beiden Passwörter sind nicht ident, versuche es nochmal!");
    }
  }
}
