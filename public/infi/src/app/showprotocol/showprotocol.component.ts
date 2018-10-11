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
  private protocol = [];
  @Input() jrkEntitaet: jrkEntitaet;
  @Output() showProtocol: EventEmitter<any> = new EventEmitter();

  constructor(public rest: RestService) { }

  ngOnInit() {
    this.rest.showprotocol(this.jrkEntitaet).subscribe(data=>{
        this.protocol = data;
    });
  }

  showDetail(id){
    this.showProtocol.emit(id);
  }

}
