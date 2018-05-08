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
  isAdmin=false;
  jrkEntitaet: any;
  view = 'home';
  password1="";
  password2="";

  private _opened = false;
  private _modeNum = 0;
  private _positionNum = 0;
  private _dock = false;
  private _closeOnClickOutside = true;
  private _closeOnClickBackdrop = false;
  private _showBackdrop = false;
  private _animate = true;
  private _trapFocus = true;
  private _autoFocus = true;
  private _keyClose = false;
  private _autoCollapseHeight: number = null;
  private _autoCollapseWidth: number = null;
  id: number;
  private sub: any;

  private _MODES: Array<string> = ['over', 'push', 'slide'];
  private _POSITIONS: Array<string> = ['left', 'right', 'top', 'bottom'];

  private _toggleOpened(): void {
    this._opened = !this._opened;
  }

  private _toggleMode(): void {
    this._modeNum++;

    if (this._modeNum === this._MODES.length) {
      this._modeNum = 0;
    }
  }

  private _toggleAutoCollapseHeight(): void {
    this._autoCollapseHeight = this._autoCollapseHeight ? null : 500;
  }

  private _toggleAutoCollapseWidth(): void {
    this._autoCollapseWidth = this._autoCollapseWidth ? null : 500;
  }

  private _togglePosition(): void {
    this._positionNum++;

    if (this._positionNum === this._POSITIONS.length) {
      this._positionNum = 0;
    }
  }

  private _toggleDock(): void {
    this._dock = !this._dock;
  }

  private _toggleCloseOnClickOutside(): void {
    this._closeOnClickOutside = !this._closeOnClickOutside;
  }

  private _toggleCloseOnClickBackdrop(): void {
    this._closeOnClickBackdrop = !this._closeOnClickBackdrop;
  }

  private _toggleShowBackdrop(): void {
    this._showBackdrop = !this._showBackdrop;
  }

  private _toggleAnimate(): void {
    this._animate = !this._animate;
  }

  private _toggleTrapFocus(): void {
    this._trapFocus = !this._trapFocus;
  }

  private _toggleAutoFocus(): void {
    this._autoFocus = !this._autoFocus;
  }

  private _toggleKeyClose(): void {
    this._keyClose = !this._keyClose;
  }

  private _onOpenStart(): void {
  }

  private _onOpened(): void {
  }

  private _onCloseStart(): void {
  }

  private _onClosed(): void {
  }

  constructor(public rest: RestService,private user: UserService, private route: ActivatedRoute, private router: Router) {
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
      this.rest.isAdmin(body)
      .subscribe(data => {
        this.isAdmin = (data === true);
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


