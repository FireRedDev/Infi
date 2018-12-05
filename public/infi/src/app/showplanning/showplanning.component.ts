import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

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
      this.plannings = data;
    });
  }
  private plannings = [];

}
