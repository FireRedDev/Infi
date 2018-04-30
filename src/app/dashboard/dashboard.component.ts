import { jrkEntitaet } from '../termin/jrkEntitaet.model';
import { forEach } from '@angular/router/src/utils/collection';
import { Component,
ChangeDetectionStrategy,
OnInit,
Input,
Output,
EventEmitter,
ViewChild,
Provider,
OnDestroy,
TemplateRef } from '@angular/core';
import {UserService} from '../user.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Benutzer } from '../login-form/benutzer.model';
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
  isEditor= true;
  jrkEntitaet: any;
  view = 'home';
  password1="";
  password2="";

  public _opened = false;
  public _modeNum = 0;
  public _positionNum = 0;
  public _dock = false;
  public _closeOnClickOutside = true;
  public _closeOnClickBackdrop = false;
  public _showBackdrop = false;
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

  constructor(public rest: RestService,public user: UserService, public route: ActivatedRoute, public router: Router) {
    this.rest=rest;
  }

  ngOnInit(): void {
    const body = localStorage.getItem('currentUser');
    this.rest.getName(body)
      .subscribe(data => {
        this.username = JSON.stringify(data).replace("\"","").replace("\"","");
      });
    this.rest.isEditor(body)
      .subscribe(data => {
        this.isEditor = (data === true);
      });
    this.rest.needPwdChange(body)
    .subscribe(data => {
      if(data!=true){
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
    this.rest.getJRKEntitaet(body)
      .subscribe(data => {
        this.jrkEntitaet = data;
      }); 
  }

  changeView(i){
    this.view = i;
  }

  changePwd(){
    if(this.password1==this.password2&&this.password1!=""){
      const body = {'id': localStorage.getItem('currentUser'), 'password': this.password1};
      this.rest.changePassword(body).subscribe(data => {
        console.log("message: "+data);
        $('#pwdModal').modal('hide');
      });
    }
    else{
      alert("Die beiden Passwörter sind nicht ident, versuche es nochmal!");
    }
  }
}


