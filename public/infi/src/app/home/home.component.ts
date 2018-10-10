import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { SwPush } from "@angular/service-worker";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public infos;
  public sub;
  readonly VAPID_PUBLIC_KEY = "BJSoPyBlrakA1kmujwb3v03t9nXWppH89bnXY69lERO-nNxwm2RGt-_HNLYcJaNpfQX0eLDHcFtf_CjKW5cejuQ";
  constructor(private rest: RestService,
    private swPush: SwPush) {
    this.rest = rest;
  }

  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    this.rest.getUserInfos(body)
      .subscribe(data => {
        this.infos = data;
        console.log(this.infos);
      });
  }

}
