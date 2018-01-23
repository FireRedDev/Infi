import { Component, OnInit, Input, Output } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Protokoll } from './protocol';
import { jrkEntitaet } from '../termin/jrkEntitaet.model';
import { Termin } from './termin';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-protocol',
  templateUrl: './protocol.component.html',
  styleUrls: ['./protocol.component.css']
})
export class ProtocolComponent implements OnInit {
  constructor(private http: Http) { }

  @Output() changeView: EventEmitter<string> = new EventEmitter();
  de: any;
  private term;
  
  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    this.http
        .post('http://localhost:8080/api/service/getOpenDoko', JSON.parse(body))
        .subscribe(data => {
          // Read the result field from the JSON response.
          console.log('Protokol: ',data);
          this.term=JSON.parse(data["_body"]);
          console.log(this.term);
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
      this.actTermin.doko = this.actProtokol;
      console.log(this.actTermin);
      this.http
        .post('http://localhost:8080/api/service/insertDoko', this.actTermin)
        .subscribe(data => {
          console.log('insert Protokoll');
          this.changeView.emit('month');
      });

  }
  
  actProtokol: Protokoll = new Protokoll(0,'','','Soziales');
  actTermin:Termin = new Termin();
  submitted = false;
 
  onSubmit() { this.submitted = true; }

  setTermin(id: any): void {
    var index;
    for (index = 0; index < this.term.length; ++index) {
      if(this.term[index].id == id){
        this.actTermin=this.term[index];
      }
    }
}
}

