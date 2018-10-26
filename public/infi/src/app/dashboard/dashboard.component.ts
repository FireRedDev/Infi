import {
  Component,
  OnInit,
} from '@angular/core';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../rest.service';
declare var jquery: any;
declare var $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {

  username: String;
  isEditor = true;
  isAdmin = false;
  jrkEntitaet: any;
  view = 'home';
  password1 = "";
  password2 = "";
  calendarEntry = {};

  //einige Varbiablen zum konfifurieren der Sidebar
  public _opened = false;
  public _modeNum = 0;
  public _positionNum = 0;
  public _dock = false;
  public _closeOnClickOutside = true;
  public _closeOnClickBackdrop = true;
  public _showBackdrop = true;
  public _animate = true;
  public _trapFocus = true;
  public _autoFocus = true;
  public _keyClose = false;
  public _autoCollapseHeight: number = null;
  public _autoCollapseWidth: number = null;
  id: number;
  public sub: any;

  public _MODES: Array<string> = ['over', 'push', 'slide'];
  public _POSITIONS: Array<string> = ['left', 'right', 'top', 'bottom'];

  //einige Funktionen für die Funktionalität der Sidebar
  public _toggleOpened(): void {
    this._opened = !this._opened;
  }

  public _toggleMode(): void {
    this._modeNum++;

    if (this._modeNum === this._MODES.length) {
      this._modeNum = 0;
    }
  }

  public _toggleAutoCollapseHeight(): void {
    this._autoCollapseHeight = this._autoCollapseHeight ? null : 500;
  }

  public _toggleAutoCollapseWidth(): void {
    this._autoCollapseWidth = this._autoCollapseWidth ? null : 500;
  }

  public _togglePosition(): void {
    this._positionNum++;

    if (this._positionNum === this._POSITIONS.length) {
      this._positionNum = 0;
    }
  }

  public _toggleDock(): void {
    this._dock = !this._dock;
  }

  public _toggleCloseOnClickOutside(): void {
    this._closeOnClickOutside = !this._closeOnClickOutside;
  }

  public _toggleCloseOnClickBackdrop(): void {
    this._closeOnClickBackdrop = !this._closeOnClickBackdrop;
  }

  public _toggleShowBackdrop(): void {
    this._showBackdrop = !this._showBackdrop;
  }

  public _toggleAnimate(): void {
    this._animate = !this._animate;
  }

  public _toggleTrapFocus(): void {
    this._trapFocus = !this._trapFocus;
  }

  public _toggleAutoFocus(): void {
    this._autoFocus = !this._autoFocus;
  }

  public _toggleKeyClose(): void {
    this._keyClose = !this._keyClose;
  }

  public _onOpenStart(): void {
  }

  public _onOpened(): void {
  }

  public _onCloseStart(): void {
  }

  public _onClosed(): void {
  }

  constructor(public rest: RestService, public user: UserService, public route: ActivatedRoute, public router: Router) {
    this.rest = rest;
  }

  ngOnInit(): void {
    const body = localStorage.getItem('currentUser');
    this.rest.sendToken(body, localStorage.getItem('pushToken')).subscribe();
    //Username von Server erfragen
    this.rest.getName(body)
      .subscribe(data => {
        this.username = JSON.stringify(data).replace("\"", "").replace("\"", "");
      });

    //Abfragen ob dieser User Editor ist
    this.rest.isEditor(body)
      .subscribe(data => {
        this.isEditor = (data === true);
      });

    //Abfragen ob dieser User Admin ist
    this.rest.isAdmin(body)
      .subscribe(data => {
        this.isAdmin = (data === true);
      });

    //Muss dieser User das Passwort ändern
    this.rest.needPwdChange(body)
      .subscribe(data => {
        if (data != true) {
          $('#pwdModal').modal({
            backdrop: 'static',
            keyboard: false
          });
          //this remove the close button on top if you need
          $('#pwdModal').find('.close').remove();
          //this unbind the event click on the shadow zone
          $('#pwdModal').unbind('click');
          $('#pwdModal').modal('show');
        }
      });

    //JRKEntität von Person abrufen
    this.rest.getJRKEntitaet(body)
      .subscribe(data => {
        this.jrkEntitaet = data;
      });
  }

  //ändern der View
  changeView(i) {
    this.view = i;
  }
  showDetail(i) {
    this.calendarEntry = i;
    this.view = 'calendar-detail';
  }
  changeViewCalendar(i) {
    this.calendarEntry = i.item;
    this.view = i.view;
  }
  changeViewProtocol() {
    this.calendarEntry = null;
    this.view = 'protocol';
  }

  //Password ändern
  changePwd() {
    if (this.password1 == this.password2 && this.password1 != "") {
      const body = { 'id': localStorage.getItem('currentUser'), 'password': this.password1 };
      this.rest.changePassword(body).subscribe(data => {
        //console.log("message: " + data);
        $('#pwdModal').modal('hide');
      });
    }
    else {
      this.rest.showErrorMessage("Error", "Die beiden Passwörter sind nicht ident, versuche es nochmal!");
    }
  }
}