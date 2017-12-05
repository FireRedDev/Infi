import { Component, OnInit } from '@angular/core';
import { Termin } from './termin';
import { Http, URLSearchParams } from '@angular/http';

@Component({
  selector: 'app-termin',
  templateUrl: './termin.component.html',
  styleUrls: ['./termin.component.css']
})
export class TerminComponent implements OnInit {

  constructor(private http: Http) { }

  ngOnInit() {
  }
  save(){
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
