import { Component, OnInit, Input, Output } from '@angular/core';
import { Info } from '../models/info';
import { RestService } from '../rest.service';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { EventEmitter } from '@angular/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrls: ['./information.component.css']
})
export class InformationComponent implements OnInit {
  @Input() jrkEntitaet: jrkEntitaet;
  @Output() changeView: EventEmitter<string> = new EventEmitter();

  fileInput;
  fileDisplayArea;
  imgpath;

  success=false;
    constructor(private rest: RestService) {
      this.rest=rest;
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
      debugger;
        this.rest.insertInfo(this.jrkEntitaet,this.actInformation)
          .subscribe(data => {
            this.changeView.emit("month");
        });
        this.success=true;
    }
    
      actInformation:Info = new Info(0,'','','');
      submitted = false;
   
      onSubmit() { this.submitted = true; }
 
}
