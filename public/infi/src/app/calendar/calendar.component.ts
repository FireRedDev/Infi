import {
  CalendarEvent,
  CalendarDateFormatter,
  DAYS_OF_WEEK,
  CalendarEventTitleFormatter,
  CalendarModule
} from 'angular-calendar';
import { CustomDateFormatter } from './custom-date-formatter.provider';
import { DateTimePickerComponent } from './date-time-picker.component';
import { colors } from './colors';
import { RestService } from '../rest.service';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs';
import {
  isSameMonth,
  isSameDay,
  startOfMonth,
  endOfMonth,
  startOfWeek,
  endOfWeek,
  startOfDay,
  endOfDay
} from 'date-fns';
import {
  NgbDatepickerModule,
  NgbTimepickerModule
} from '@ng-bootstrap/ng-bootstrap';
import { CustomEventTitleFormatter } from './custom-event-title-formatter.provider';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Benutzer } from '../login-form/benutzer.model';
import {
  Component,
  NgModule,
  OnInit,
  Input,
  Output,
  EventEmitter
} from '@angular/core';
import { Planning } from 'src/app/planning/planning';

interface Termin {
  id: number;
  title: string;
  s_date: string;
  e_date: string;
  beschreibung: string;
  benutzer: Benutzer;
  ort: string;
  imgpath: string;
  plan: Planning;
}
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbDatepickerModule.forRoot(),
    NgbTimepickerModule.forRoot(),
    CalendarModule
  ],
  declarations: [DateTimePickerComponent],
  exports: [DateTimePickerComponent]
})
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
  providers: [
    {
      provide: CalendarDateFormatter,
      useClass: CustomDateFormatter
    },
    {
      provide: CalendarEventTitleFormatter,
      useClass: CustomEventTitleFormatter
    }
  ]
})
export class CalendarComponent implements OnInit {
  viewDate: Date = new Date();
  title = 'app';
  @Input() view;
  @Output() showDetail = new EventEmitter();

  weekStartsOn: number = DAYS_OF_WEEK.MONDAY;
  weekendDays: number[] = [DAYS_OF_WEEK.SATURDAY, DAYS_OF_WEEK.SUNDAY];

  events$: Observable<Array<CalendarEvent<{ termin: Termin }>>>;

  locale: string = 'de-AT';

  activeDayIsOpen = false;

  constructor(public rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {
    this.fetchEvents();
  }

  fetchEvents(): void {
    const getStart: any = {
      month: startOfMonth,
      week: startOfWeek,
      day: startOfDay
    }[this.view];

    const getEnd: any = {
      month: endOfMonth,
      week: endOfWeek,
      day: endOfDay
    }[this.view];

    const body = localStorage.getItem('currentUser');
    //setzen der Termine
    this.events$ = this.rest.getUserTermine(body)
      .map(json => {
        return this.convertEvents(json as Termin[]);
      });
  }

  //Konvertieren der Events zum anzeigen
  convertEvents(events: Array<Termin>): Array<any> {
    const calendarEvents = [];
    events.forEach(function (event) {
      var text = 'Titel: ' + event.title;
      calendarEvents.push({
        id: event.id,
        termin: event,
        title: text,
        beschreibung: event.beschreibung,
        imagePath: event.imgpath,
        start: new Date(event.s_date),
        end: new Date(event.e_date),
        ort: event.ort,
        plan: event.plan,
        color: colors.red,
        cssClass: 'my-custom-class'
      });
    });
    return calendarEvents;
  }
  dayClicked({
    date,
    events
  }: {
      date: Date;
      events: Array<CalendarEvent<{ termin: Termin }>>;
    }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
        this.viewDate = date;
      }
    }
  }
  openDetail(event) {
    this.showDetail.emit(event);
  }
}
