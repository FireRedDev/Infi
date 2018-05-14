import { Component, OnInit, Input, Output } from '@angular/core';
import { Termin } from '../models/termin';
import { RestService } from '../rest.service';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { EventEmitter } from '@angular/core';
import { DateTimeAdapter } from 'ng-pick-datetime';

export const DefaultIntl = {
  /** A label for the cancel button */
  cancelBtnLabel: 'Abbrechen',

  /** A label for the set button */
  setBtnLabel: 'Setzten',

};

@Component({
selector: 'app-termin',
templateUrl: './termin.component.html',
styleUrls: ['./termin.component.css']
})


export class TerminComponent implements OnInit {
@Input() jrkEntitaet: jrkEntitaet;
@Output() changeView: EventEmitter<string> = new EventEmitter();

success=false;

  constructor(private rest: RestService, dateTimeAdapter: DateTimeAdapter<any>) {
    this.rest=rest;
    dateTimeAdapter.setLocale('de-De');
   }

  ngOnInit() {
    
  }
  save(){
    this.actTermin.s_date=new Date(this.actTermin.s_date).toISOString().substr(0, 19).replace('T', ' ');
    this.actTermin.e_date=new Date(this.actTermin.e_date).toISOString().substr(0, 19).replace('T', ' ');
      this.rest.insertTermin(this.jrkEntitaet,this.actTermin)
        .subscribe(data => {
          this.changeView.emit("month");
      });
      this.success=true;
      
  }

    actTermin:Termin = new Termin(0,'','','','','');
    submitted = false;
 
    onSubmit() { this.submitted = true; }
    }
