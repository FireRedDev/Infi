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

  ngOnInit() {
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
  
  actTermin: Protokoll = new Protokoll('','','','','','','','','','','Soziales');
  s_time;
  e_time;
  submitted = false;
 
  onSubmit() { this.submitted = true; }
}

