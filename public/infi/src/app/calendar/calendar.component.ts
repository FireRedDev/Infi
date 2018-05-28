import {
  CalendarEvent,
  CalendarDateFormatter,
  DAYS_OF_WEEK,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarEventTitleFormatter,
  CalendarModule
} from 'angular-calendar';
import { CustomDateFormatter } from './custom-date-formatter.provider';
import { DateTimePickerComponent } from './date-time-picker.component';
import { colors } from './colors';
import { RestService } from '../rest.service';
import 'rxjs/add/operator/map';
import {
  isSameMonth,
  isSameDay,
  startOfMonth,
  endOfMonth,
  startOfWeek,
  endOfWeek,
  startOfDay,
  endOfDay,
  format,
  subDays,
  addDays,
  addHours
} from 'date-fns';
import { Observable } from 'rxjs/Observable';
import {
  NgbDatepickerModule,
  NgbTimepickerModule, NgbModal
} from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs/Subject';
import { CustomEventTitleFormatter } from './custom-event-title-formatter.provider';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Benutzer } from '../login-form/benutzer.model';
import { Component,
  NgModule,
  ChangeDetectionStrategy,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ViewChild,
  Provider,
  OnDestroy,
  TemplateRef } from '@angular/core';

interface Termin {
  id: number;
  title: string;
  s_date: string;
  e_date: string;
  beschreibung: string;
  benutzer: Benutzer;
  ort: String;
  imgpath: String;
}
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbDatepickerModule.forRoot(),
    NgbTimepickerModule.forRoot(),
    CalendarModule
  ],
  declarations: [ DateTimePickerComponent],
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
  locale = 'de';
  @Input() view;
  weekStartsOn: number = DAYS_OF_WEEK.MONDAY;
  weekendDays: number[] = [DAYS_OF_WEEK.SATURDAY, DAYS_OF_WEEK.SUNDAY];

  events$: Observable<Array<CalendarEvent<{ termin: Termin }>>>;

  activeDayIsOpen = false;

  @Output() viewChange: EventEmitter<string> = new EventEmitter();
  @Output() viewDateChange: EventEmitter<Date> = new EventEmitter();

  constructor(public rest: RestService,private route: ActivatedRoute, private router: Router) {
    this.rest=rest;
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
    this.events$ = this.rest.getUserTermine(body)
      .map(json => {
        return this.convertEvents(json as Termin[]);
      });
  }

  convertEvents(events: Array<Termin>): Array<any>{
    const calendarEvents = [];
    events.forEach(function(event){
      var text='Titel: ' + event.title + '<br>Beschreibung: ' + event.beschreibung + '<br>Ort: ' + event.ort
      if(event.imgpath){
        text+="<br><img width='200px' src='"+event.imgpath+"'>"
      }
      calendarEvents.push({
        title: text,
        start: new Date(event.s_date),
        end: new Date(event.e_date),
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
}
