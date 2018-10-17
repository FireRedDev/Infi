import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { EventEmitter, Output } from '@angular/core';
import { Termin } from 'src/app/models/termin';

@Component({
  selector: 'app-dateconfirm',
  templateUrl: './dateconfirm.component.html',
  styleUrls: ['./dateconfirm.component.css']
})
export class DateconfirmComponent implements OnInit {
  public confirmed;
  public termin = [];

  constructor(public rest: RestService) { }

  ngOnInit() {
    this.rest.shownextDate().subscribe(data => {
      this.termin = data;
    });
  }

}
