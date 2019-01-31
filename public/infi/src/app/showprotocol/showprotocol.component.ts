import { RestService } from '../rest.service';
import { Pipe } from '@angular/core';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

/**
 * Overview over all Protocols which this User is allowed to see
 */
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
  @Output() showProtocol: EventEmitter<any> = new EventEmitter();

  records: Array<any> = new Array();
  isDesc: boolean = false;
  column: string = 'CategoryName';
  searchText;

  constructor(
    public rest: RestService
  ) { }

  /**
   * get all Protocols, this User is allowed to see
   */
  ngOnInit(): void {
    this.rest.showprotocol(this.jrkEntitaet).subscribe(data => {
      this.protocol = data;
      // get object with protocol attributes
      for (var i = 0; i < this.protocol.length; i++) {
        const myObj = { Titel: this.protocol[i].title, Datum: this.protocol[i].s_date.slice(0, 16), Beschreibung: this.protocol[i].beschreibung, id: this.protocol[i].id };
        //push to records
        this.records.push(myObj);
      }
      this.rest.showSuccessMessage("Erfolg", "Protokolle geladen!");
    });
  }

  // Declare local variable
  direction: number;
  /**
   * sort the records by a property
   * @param property 
   */
  sort(property) {
    this.isDesc = !this.isDesc; //change the direction    
    this.column = property;
    let direction = this.isDesc ? 1 : -1;

    //sort records
    this.records.sort(function (a, b) {
      if (a[property] < b[property]) {
        //change direction
        return -1 * direction;
      }
      else if (a[property] > b[property]) {
        //change direction
        return 1 * direction;
      }
      else {
        //do nothing
        return 0;
      }
    });
  };

  /**
   * show details
   * @param id 
   */
  showDetail(id) {
    this.showProtocol.emit(id);
  }

}