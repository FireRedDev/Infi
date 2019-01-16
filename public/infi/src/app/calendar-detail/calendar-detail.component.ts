import { RestService } from '../rest.service';
import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter
} from '@angular/core';

/**
 * Show Appointments of one specific day
 */
@Component({
  selector: 'app-calendar-detail',
  templateUrl: './calendar-detail.component.html',
  styleUrls: ['./calendar-detail.component.css']
})
export class CalendarDetailComponent implements OnInit {
  /** Input are the shown calendar entries */
  @Input() calendarEntry;

  @Output() changeViewCalendar = new EventEmitter();
  @Output() changeViewTermin = new EventEmitter();
  /** is this Person Editor */
  isEditor;
  /** is this Person Gruppenleiter */
  isGruppenleiter

  /**
   * Constructor
   * @param rest RestService
   */
  constructor(public rest: RestService) {
    this.rest = rest;
  }

  /**
   * is this User Editor?
   * is this User group leader?
   */
  ngOnInit() {
    const body = localStorage.getItem('currentUser');

    this.rest.isEditor(body)
      .subscribe(data => {
        this.isEditor = (data === true);
      });

    this.rest.isGruppenleiter(body)
      .subscribe(data => {
        this.isGruppenleiter = (data === true);
      });
  }

  /**
   * 
   * @param view View
   * @param item current Appointment
   */
  changeViewCalendarDetail(view, item) {
    this.changeViewCalendar.emit({ "view": view, "item": item });
  }

  /**
   * 
   * @param item current Appointment
   */
  deleteTermin(item) {
    this.rest.deleteTermin(item.termin).subscribe(data => {
      console.log(data);
    });
  }

  /**
   * 
   * @param item current Appointment
   */
  changeTermin(item) {
    this.changeViewTermin.emit({ "view": "termin", "item": item.termin });
  }
}
