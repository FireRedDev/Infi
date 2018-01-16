import { Component, OnInit, Input, Output } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Protokoll } from './protocol';

@Component({
  selector: 'app-protocol',
  templateUrl: './protocol.component.html',
  styleUrls: ['./protocol.component.css']
})
export class ProtocolComponent implements OnInit {

  constructor(private http: Http) { }

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
     // this.actTermin.jrkEntitaet=this.jrkEntitaet;
     // console.log(this.actTermin);
      
      this.http
        .post('http://localhost:8080/api/service/insertTermin',this)
        .subscribe(data => {
          // Read the result field from the JSON response.
          console.log("insert Termin");
      });
  }
  
  actTermin: Protokoll = new Protokoll('','','','','','','','','Soziales');
  s_time;
  e_time;
  submitted = false;
 
  onSubmit() { this.submitted = true; }
}

