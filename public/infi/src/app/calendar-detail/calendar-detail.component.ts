import { RestService } from '../rest.service';
import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter
} from '@angular/core';

@Component({
  selector: 'app-calendar-detail',
  templateUrl: './calendar-detail.component.html',
  styleUrls: ['./calendar-detail.component.css']
})
export class CalendarDetailComponent implements OnInit {
  @Input() calendarEntry;
  @Output() writeDoku = new EventEmitter();
  isEditor;
  constructor(public rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {

    const body = localStorage.getItem('currentUser');

    //Abfragen ob dieser User Editor ist
    this.rest.isEditor(body)
      .subscribe(data => {
        this.isEditor = (data === true);
      });
  }

  changeView(item) {
    this.writeDoku.emit(item);
  }
  /* Plannung speichern */
  public textModel;

  save(item) {
    debugger;
    const body = item.id;
    this.rest.insertPlannungsText(body, this.textModel).subscribe();
    this.textModel = "";
  }
}
