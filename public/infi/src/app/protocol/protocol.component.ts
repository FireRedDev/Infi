import { Component, OnInit, Input, Output } from '@angular/core';
import { RestService } from '../rest.service';
import { Protokoll } from './protocol';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { Termin } from './termin';
import { EventEmitter } from '@angular/core';
import { DateTimeAdapter } from 'ng-pick-datetime';

export const DefaultIntl = {
  /** A label for the cancel button */
  cancelBtnLabel: 'Abbrechen',

  /** A label for the set button */
  setBtnLabel: 'Setzten',

};
@Component({
  selector: 'app-protocol',
  templateUrl: './protocol.component.html',
  styleUrls: ['./protocol.component.css']
})
export class ProtocolComponent implements OnInit {
  constructor(public rest: RestService, dateTimeAdapter: DateTimeAdapter<any>) {
      this.newChild = '';
      this.children = [];
      this.newBetreuer = '';
      this.betreuer = [];
      this.rest=rest;
      dateTimeAdapter.setLocale('de-De');
   }

  @Output() changeView: EventEmitter<string> = new EventEmitter();
  de: any;
  public term: Termin[];
  
  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    this.rest.getOpenDoko(body)
        .subscribe(data => {
          this.term=data as Termin[];
          this.actTermin=this.term[0];
      });
    this.de = {
            firstDayOfWeek: 0,
            dayNames: ["Sonntag", "Montag", "Dienstag","Mittwoch", "Donnerstag", "Freitag", "Samstag"],
            dayNamesShort: ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"],
            monthNames: [ "Jänner","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember" ],
            monthNamesShort: [ "Jän", "Feb", "Mär", "Apr", "Mai", "Jun","Jul", "Aug", "Sep", "Okt", "Nov", "Dez" ]
        };
  }
  save(){
    var actTermin=this.actTermin
    actTermin.s_date=new Date(this.s_date).toISOString().substr(0, 19).replace('T', ' ');
    actTermin.e_date=new Date(this.e_date).toISOString().substr(0, 19).replace('T', ' ');
    this.actProtokol.kinderliste = this.children;
    this.actProtokol.betreuer = this.betreuer;
      actTermin.doko = this.actProtokol;
      this.rest.insertDoku(actTermin)
        .subscribe(data => {
          this.changeView.emit('month');
      });

  }
  
  actProtokol: Protokoll = new Protokoll(0,null,null,'','Soziales');
  actTermin:Termin = new Termin();
  submitted = false;
 
  onSubmit() { this.submitted = true; }

  setTermin(id: any): void {
    var index;
    for (index = 0; index < this.term.length; ++index) {
      if(this.term[index].id == id){
        this.actTermin=this.term[index];
        this.s_date=this.actTermin.s_date;
        this.e_date=this.actTermin.e_date;
      }
    }
    }
    newChild: string;
    children: any;

    newBetreuer: string;
    betreuer: any;
    s_date;
    e_date;

    addChild(event) {
      this.children.push(this.newChild);
      this.newChild = '';
      event.preventDefault();
    }

    deleteChild(index) {
      this.children.splice(index, 1);
    }

    addBetreuer(event) {
      this.betreuer.push(this.newBetreuer);
      this.newBetreuer = '';
      event.preventDefault();
    }

    deleteBetreuer(index) {
      this.betreuer.splice(index, 1);
    }

}

