import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public infos;
  public t;
  showInvite = true;

  constructor(private rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {

    const body = localStorage.getItem('currentUser');

    this.rest.getUserInfos(body)
      .subscribe(data => {
        this.infos = data;
        this.rest.showSuccessMessage("Erfolg", "Informationen geladen!");
      });


    this.rest.getActTermin(body).subscribe(data => {
      this.t = data;
    });
  }

  setComing() {
    const body = localStorage.getItem('currentUser');
    this.rest.setComing(this.t.id, body).subscribe();
    this.showInvite = false;
  }
}
