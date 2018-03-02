import { Component, OnInit } from '@angular/core';
import {single, multi, single2, multi2, single3, multi3, singleChartBar, multiChartBar} from './data';
import { RestService } from '../rest.service';

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

  constructor(private rest: RestService) {
    Object.assign(this, {single, multi});
    Object.assign(this, {single2, multi2}); 
    Object.assign(this, {single3, multi3});
    Object.assign(this, {singleChartBar, multiChartBar});
    this.rest = rest;  
  }
  
  ngOnInit(){
    const body = localStorage.getItem('currentUser');
    this.jrkEnitaet = JSON.parse(body);

    this.rest.getJRKEntitaetdown(body)
    .subscribe(data => {
      console.log(data);
      this.JRKEntitaeten=data;
  });

    this.rest.getChartValues(body)
    .subscribe(value => {
        this.single = value;
        this.multi = value;
    });

   this.rest.getLowerEntityHourList(body)
    .subscribe(value => {
        this.single2 = value;
        this.multi2 = value;
    });

    this.rest.getYearlyHoursPerPeople(body)
    .subscribe(value => {
        this.multi3 = value;
        this.single3 = value;
    });

    this.rest.getTimelineValues(body)
    .subscribe(value => {
        this.singleChartBar = value;
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
        this.multi = value;
    });

   this.rest.getLowerEntityHourList(body)
    .subscribe(value => {
        this.single2 = value;
        this.multi2 = value;
    });

    this.rest.getYearlyHoursPerPeople(body)
    .subscribe(value => {
        this.multi3 = value;
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

