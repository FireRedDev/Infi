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

  @Output() changeViewCalendar = new EventEmitter();
  @Output() changeViewTermin = new EventEmitter();
  isEditor;
  isGruppenleiter
  constructor(public rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {
    debugger;
    const body = localStorage.getItem('currentUser');

    //Abfragen ob dieser User Editor ist
    this.rest.isEditor(body)
      .subscribe(data => {
        this.isEditor = (data === true);
      });

    this.rest.isGruppenleiter(body)
      .subscribe(data => {
        this.isGruppenleiter = (data === true);
      });
  }

  changeViewCalendarDetail(view, item) {
    this.changeViewCalendar.emit({ "view": view, "item": item });
  }

  deleteTermin(item) {
    this.rest.deleteTermin(item.termin).subscribe(data => {
      console.log(data);
    });
  }
  changeTermin(item) {
    this.changeViewTermin.emit({ "view": "termin", "item": item.termin });
  }
}
