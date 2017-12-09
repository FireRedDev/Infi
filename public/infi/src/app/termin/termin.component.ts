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
    console.log(this.actTermin);
    console.log(this.jrkEntitaet);

    this.actTermin.jrkEntitaet=this.jrkEntitaet;
    console.log(this.actTermin);
    
    this.http
      .post('http://localhost:8080/api/service/insertTermin',this.actTermin)
      .subscribe(data => {
        // Read the result field from the JSON response.
        console.log("insert Termin");
      });
  }
  
  actTermin:Termin = new Termin();

}
