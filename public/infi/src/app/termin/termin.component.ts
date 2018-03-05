import { Component, OnInit, Input, Output } from '@angular/core';
import { Termin } from './termin';
import { HttpClient } from '@angular/common/http';
import { jrkEntitaet } from './jrkEntitaet.model';
import { EventEmitter } from '@angular/core';

@Component({
selector: 'app-termin',
templateUrl: './termin.component.html',
styleUrls: ['./termin.component.css']
})
export class TerminComponent implements OnInit {
@Input() jrkEntitaet: jrkEntitaet;
@Output() changeView: EventEmitter<string> = new EventEmitter();
    success=false;
    constructor(private http: HttpClient) {
    this.http=http;
    }
    de: any;

    ngOnInit() {
    this.de = {
    firstDayOfWeek: 0,
    dayNames: ["Sonntag", "Montag", "Dienstag","Mittwoch", "Donnerstag", "Freitag", "Samstag"],
    dayNamesShort: ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"],
    monthNames: [ "Jänner","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember" ],
    monthNamesShort: [ "Jän", "Feb", "Mär", "Apr", "Mai", "Jun","Jul", "Aug", "Sep", "Okt", "Nov", "Dez" ]
    };
    }
    save(){
    // console.log(this.actTermin);
      
    this.http
    .post('http://localhost:8080/api/service/insertTermin/'+this.jrkEntitaet.id,this.actTermin)
    .subscribe(data => {
    // Read the result field from the JSON response.
    this.changeView.emit("month");
    });
    this.success=true;
    }
  
    actTermin:Termin = new Termin(0,'','','','','');
    submitted = false;
 
    onSubmit() { this.submitted = true; }
    }
