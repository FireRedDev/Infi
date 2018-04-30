import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
selector: 'app-home',
templateUrl: './home.component.html',
styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public infos;
  constructor(public rest: RestService) {
    this.rest=rest;
   }

  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    this.rest.getUserInfos(body)
        .subscribe(data => {
          this.infos=data;
          console.log(this.infos);
      });
  }

}
