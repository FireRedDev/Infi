import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { Planning } from 'src/app/planning/Planning';

@Component({
  selector: 'app-showplanning',
  templateUrl: './showplanning.component.html',
  styleUrls: ['./showplanning.component.css']
})
export class ShowplanningComponent implements OnInit {

  constructor(
    public rest: RestService
  ) { }

  ngOnInit() {
    this.rest.getAllPlanning().subscribe(data => {
      this.termins = data;
      for (var i = 0; i < this.termins.length; i++) {
        this.plan = this.termins[i].planning
        const myObj = { Datum: this.termins[i].s_date, Beschreibung: this.plan.plannung };
        this.records.push(myObj);
      }
    });
  }
  private plan: Planning;
  private termins = [];
  records: Array<any> = new Array();
}
