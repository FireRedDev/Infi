import { Component, OnInit, Input } from '@angular/core';
import { RestService } from '../rest.service';
import { Pipe } from '@angular/core';

@Component({
  selector: 'app-showprotocol',
  templateUrl: './showprotocol.component.html',
  styleUrls: ['./showprotocol.component.css']
})
@Pipe({
  name: 'filterPipe',
})

export class ShowprotocolComponent implements OnInit {
  private protocol = [];
  @Input() jrkEntitaet;

  records: Array<any> = new Array();
  isDesc: boolean = false;
  column: string = 'CategoryName';

  constructor(
    public rest: RestService
  ) { }

  ngOnInit(): void {
    //jrkentität mitgeben
    this.rest.showprotocol(this.jrkEntitaet).subscribe(data => {
      this.protocol = data;

      for (var i = 0; i < this.protocol.length; i++) {
        const myObj = { Titel: this.protocol[i].title, Datum: this.protocol[i].s_date.slice(0, 16), Beschreibung: this.protocol[i].beschreibung };
        this.records.push(myObj);
      }
      this.rest.showSuccessMessage("Erfolg", "Protokolle geladen!");
    });
  }

  // Declare local variable
  direction: number;
  // Change sort function to this: 
  sort(property) {
    this.isDesc = !this.isDesc; //change the direction    
    this.column = property;
    let direction = this.isDesc ? 1 : -1;

    this.records.sort(function (a, b) {
      if (a[property] < b[property]) {
        return -1 * direction;
      }
      else if (a[property] > b[property]) {
        return 1 * direction;
      }
      else {
        return 0;
      }
    });
  };
}