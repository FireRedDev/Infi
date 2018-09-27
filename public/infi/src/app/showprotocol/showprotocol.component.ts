import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-showprotocol',
  templateUrl: './showprotocol.component.html',
  styleUrls: ['./showprotocol.component.css']
})
export class ShowprotocolComponent implements OnInit {
  private protocol = [];
  constructor(public rest: RestService) { }

  ngOnInit() {
    this.rest.showprotocol().subscribe(data=>{
        this.protocol = data;
    });
  }

}
