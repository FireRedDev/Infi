import { Component, OnInit } from '@angular/core';
import {single, multi, single2, multi2, single3, multi3, singleChartBar, multiChartBar} from './data';
import { Http } from '@angular/http';

@Component({
selector: 'app-diagrams',
templateUrl: './diagrams.component.html',
styleUrls: ['./diagrams.component.css']
})
export class DiagramsComponent implements OnInit{

private jrkEnitaet = 2;
private JRKEntitaeten;
data: any;
single: any[];
multi: any[];
single2: any[];
multi2: any[];
single3: any[];
multi3: any[];
multiChart : any[];
singleChart : any[];
singleChartBar : any[];
multiChartBar : any[];
view: any[] = [700, 400];

showLegend = true;

colorScheme = {
domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
};

constructor(private http: Http) {
Object.assign(this, {single, multi});
Object.assign(this, {single2, multi2}); 
Object.assign(this, {single3, multi3});
Object.assign(this, {singleChartBar, multiChartBar});
this.http = http;  
}
  
ngOnInit(){
const body = localStorage.getItem('currentUser');
this.jrkEnitaet = JSON.parse(body);

this.http
.post('http://localhost:8080/api/service/getJRKEntitaetdown', JSON.parse(body))
.subscribe(data => {
console.log(data)
// Read the result field from the JSON response.
this.JRKEntitaeten=JSON.parse(data["_body"]);
console.log(this.JRKEntitaeten);
});

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

this.http
.post('http://localhost:8080/api/service/getYearlyHoursPerPeople',  this.jrkEnitaet)
.subscribe(value => {
console.log(value);
this.multi3 = JSON.parse(value["_body"]);
this.single3 = JSON.parse(value["_body"]);
});

this.http
.post('http://localhost:8080/api/service/getTimelineValues',  this.jrkEnitaet)
.subscribe(value => {
console.log(value);
//this.multiChartBar = JSON.parse(value["_body"]);
this.singleChartBar = JSON.parse(value["_body"]);
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

