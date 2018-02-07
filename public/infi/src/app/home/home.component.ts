import { Component, OnInit } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private infos;
  constructor(private http: Http) { }

  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    this.http
        .post('http://localhost:8080/api/service/getUserInfos', JSON.parse(body))
        .subscribe(data => {
          // Read the result field from the JSON response.
          this.infos=JSON.parse(data["_body"]);
      });
  }

}
