import { Component, OnInit } from '@angular/core';
import {single, multi, single2, multi2} from './data';
import { Http } from '@angular/http';

@Component({
  selector: 'app-diagrams',
  templateUrl: './diagrams.component.html',
  styleUrls: ['./diagrams.component.css']
})
export class DiagramsComponent implements OnInit{

  private jrkEnitaet = 2;
  data: any;
  single: any[];
  multi: any[];
  single2: any[];
  multi2: any[];
  view: any[] = [700, 400];

  // options
  showLegend = true;

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  constructor(private http: Http) {
    Object.assign(this, {single, multi}) 
    Object.assign(this, {single2, multi2}) 
    this.http = http;  
  }
  
  ngOnInit(){
    this.http
    .post('http://localhost:8080/api/service/getChartValues', this.jrkEnitaet)
    .subscribe(value => {
        console.log(value)
        this.single = JSON.parse(value["_body"]);
        this.multi = JSON.parse(value["_body"]);
    });

    this.http
    .post('http://localhost:8080/api/service/getLowerEntityHourList', 4)
    .subscribe(value => {
        console.log(value)
        this.single2 = JSON.parse(value["_body"]);
        this.multi2 = JSON.parse(value["_body"]);
    });
  }

  // pie
  showLabels = true;
  explodeSlices = false;
  doughnut = false;



  onSelect(event) {
    console.log(event);
  }
  
}

