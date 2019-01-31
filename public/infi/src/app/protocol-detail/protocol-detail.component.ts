import { Component, OnInit, Input, Output } from '@angular/core';
import { RestService } from '../rest.service';
import { Protokoll } from '../protocol/protocol';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { Termin } from '../protocol/termin';
import { EventEmitter } from '@angular/core';
import { DateTimeAdapter } from 'ng-pick-datetime';
import { ProtocolComponent } from '../protocol/protocol.component';

@Component({
  selector: 'app-protocol-detail',
  templateUrl: './protocol-detail.component.html',
  styleUrls: ['./protocol-detail.component.css']
})
/**
 * Show a detailed Protocol
 * Componend is opend from the Component showProtocol
 */
export class ProtocolDetailComponent implements OnInit {
  constructor(public rest: RestService, dateTimeAdapter: DateTimeAdapter<any>) {
    this.children = [];
    this.betreuer = [];
    this.rest = rest;
    dateTimeAdapter.setLocale('de-De');
  }

  @Input() protocol: number;
  @Output() changeView: EventEmitter<string> = new EventEmitter();
  de: any;

  ngOnInit() {
    console.log("Initialising protocol-detail");
    //get documentation
    this.rest.getDokuById(this.protocol).subscribe(data => {
      this.actTermin = data;
      //set startdate and enddate
      this.s_date = this.actTermin.s_date;
      this.e_date = this.actTermin.e_date;
      //set doko
      this.proto = this.actTermin.doko;
    })
    this.de = {
      firstDayOfWeek: 0,
      // day - form of the calander
      dayNames: ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"],
      // day -short form of the calander
      dayNamesShort: ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"],
      // month - from of the calander
      monthNames: ["Jänner", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"],
      // month -short form of the calander
      monthNamesShort: ["Jän", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"]
    };
  }
  actProtokol: Protokoll = new Protokoll(0, null, null, '', 'Soziales');
  actTermin: Termin = new Termin();

  proto: Protokoll = new Protokoll();
  children: any;
  betreuer: any;
  s_date;
  e_date;
}
