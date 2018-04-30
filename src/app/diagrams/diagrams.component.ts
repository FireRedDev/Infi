import { Component, OnInit } from '@angular/core';
//import {single, multi, single2, multi2, single3, multi3, singleChartBar, multiChartBar} from './data';
import { RestService } from '../rest.service';

@Component({
selector: 'app-diagrams',
templateUrl: './diagrams.component.html',
styleUrls: ['./diagrams.component.css']
})
export class DiagramsComponent implements OnInit{

public jrkEnitaet = 2;
public JRKEntitaeten;
data: any;
single=[];
single2=[];
single3=[];
singleChartBar=[];
multiChartBar=[];
view: any[] = [700, 400];

showLegend = true;

colorScheme = {
domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
};

  constructor(public rest: RestService) {
    this.rest = rest;  
  }
  
ngOnInit(){
    var body = localStorage.getItem('currentUser');
    this.jrkEnitaet = JSON.parse(body);

    this.rest.getJRKEntitaetdown(body)
    .subscribe(data => {
      this.JRKEntitaeten=data;

      body=this.JRKEntitaeten[0];
      this.rest.getChartValues(body)
      .subscribe(value => {
          this.single = value;
      });
  
     this.rest.getLowerEntityHourList(body)
      .subscribe(value => {
          this.single2 = value;
      });
  
      this.rest.getYearlyHoursPerPeople(body)
      .subscribe(value => {
          this.single3 = value;
      });
  
      this.rest.getTimelineValues(body)
      .subscribe(value => {
          this.singleChartBar = value;
      });
  });

   
  }

// pie
showLabels = true;
explodeSlices = false;
doughnut = false;

  set(body: any): void {
    this.rest.getChartValues(body)
    .subscribe(value => {
        this.single = value;
    });

   this.rest.getLowerEntityHourList(body)
    .subscribe(value => {
        this.single2 = value;
    });

    this.rest.getYearlyHoursPerPeople(body)
    .subscribe(value => {
        this.single3 = value;
    });

    this.rest.getTimelineValues(body)
    .subscribe(value => {
        this.singleChartBar = value;
    });
  }

onSelect(event) {
console.log(event);
}
  
}

