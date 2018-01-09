import { Component, OnInit, Input, Output } from '@angular/core';
import { Termin } from './termin';
import { Http, URLSearchParams } from '@angular/http';
import { jrkEntitaet } from './jrkEntitaet.model';

@Component({
  selector: 'app-termin',
  templateUrl: './termin.component.html',
  styleUrls: ['./termin.component.css']
})
export class TerminComponent implements OnInit {
@Input() jrkEntitaet: jrkEntitaet;
  constructor(private http: Http) { }

  ngOnInit() {
  }
  save(){
    /**
    var eingabefehler=false;
    console.log(this.actTermin);
    console.log(this.jrkEntitaet);

    if(this.actTermin.title==""||this.actTermin.title.length==0){
      alert("Titel muss eingegeben werden!");
      eingabefehler = true;
    }
    else{
      if(!(this.actTermin.title.match(/([a-z0-9.,;:öäüß _-])/gi))){
        alert("Ungültige Zeichen im Titel!");
        eingabefehler = true;
      }
      else{
        if(this.actTermin.title.length>100){
          alert("Titel zu lange!");
          eingabefehler = true;
        }
      }
    }
    if(this.actTermin.beschreibung==""||this.actTermin.beschreibung.length==0){
      alert("Beschreibung muss eingegeben werden!");
      eingabefehler = true;
    }
    else{
      if(!(this.actTermin.beschreibung.match(/([a-z0-9.,;:öäüß _-])/gi))){
        alert("Ungültige Zeichen in der Beschreibung!");
        eingabefehler = true;
      }
      else{
        if(this.actTermin.beschreibung.length>100){
          alert("Beschreibung zu lange!");
          eingabefehler = true;
        }
      }
    }
    if(this.actTermin.ort==""||this.actTermin.ort.length==0){
      alert("Ort muss eingegeben werden!");
      eingabefehler = true;
    }
    else{
      if(!(this.actTermin.ort.match(/([a-z0-9.,;:öäüß _-])/gi))){
        alert("Ungültige Zeichen im Ort!");
        eingabefehler = true;
      }
      else{
        if(this.actTermin.ort.length>100){
          alert("Ort zu lange!");
          eingabefehler = true;
        }
      }
    }
    if(!eingabefehler){
      */
      this.actTermin.jrkEntitaet=this.jrkEntitaet;
      console.log(this.actTermin);
      
      this.http
        .post('http://localhost:8080/api/service/insertTermin',this.actTermin)
        .subscribe(data => {
          // Read the result field from the JSON response.
          console.log("insert Termin");
      });
    /**}*/
  }
  
  actTermin:Termin = new Termin(0,'','','','','',null);
  s_time;
  e_time;
  submitted = false;
 
  onSubmit() { this.submitted = true; }
}
