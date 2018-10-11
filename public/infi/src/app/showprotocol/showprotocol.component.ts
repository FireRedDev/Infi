import { Component, OnInit, Input } from '@angular/core';
import { RestService } from '../rest.service';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-showprotocol',
  templateUrl: './showprotocol.component.html',
  styleUrls: ['./showprotocol.component.css']
})
export class ShowprotocolComponent implements OnInit {
  public protocol = [];
  @Input() jrkEntitaet: jrkEntitaet;
  @Output() changeView: EventEmitter<string> = new EventEmitter();

  constructor(public rest: RestService) { }

  ngOnInit() {
    this.rest.showprotocol(this.jrkEntitaet).subscribe(data => {
      this.protocol = data;
    });
  }

}
