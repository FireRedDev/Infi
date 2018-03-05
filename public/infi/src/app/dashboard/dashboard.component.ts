import { jrkEntitaet } from '../termin/jrkEntitaet.model';
import { forEach } from '@angular/router/src/utils/collection';
import { Component,
ChangeDetectionStrategy,
OnInit,
Input,
Output,
EventEmitter,
ViewChild,
Provider,
OnDestroy,
TemplateRef } from '@angular/core';
import {UserService} from '../user.service';
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
import { HttpClient } from '@angular/common/http';
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
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
NgbDatepickerModule,
NgbTimepickerModule, NgbModal
} from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs/Subject';
import { CustomEventTitleFormatter } from './custom-event-title-formatter.provider';
import { ActivatedRoute, Router } from '@angular/router';
import { Benutzer } from '../login-form/benutzer.model';

interface Termin {
id: number;
title: string;
s_date: string;
e_date: string;
beschreibung: string;
benutzer: Benutzer;
ort: String;
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
selector: 'app-dashboard',
templateUrl: './dashboard.component.html',
styleUrls: ['./dashboard.component.css'],
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

export class DashboardComponent implements OnInit {

username: String;
isEditor= true;
jrkEntitaet: any;
@ViewChild('modalContent') modalContent: TemplateRef<any>;

    viewDate: Date = new Date();
    title = 'app';
    locale = 'de';
    view = 'month';
    weekStartsOn: number = DAYS_OF_WEEK.MONDAY;
    weekendDays: number[] = [DAYS_OF_WEEK.SATURDAY, DAYS_OF_WEEK.SUNDAY];

    events$: Observable<Array
        <CalendarEvent<{ termin: Termin }>>>;

            activeDayIsOpen = false;

            @Output() viewChange: EventEmitter<string> = new EventEmitter();
                @Output() viewDateChange: EventEmitter<Date> = new EventEmitter();

                    private _opened = false;
                    private _modeNum = 0;
                    private _positionNum = 0;
                    private _dock = false;
                    private _closeOnClickOutside = false;
                    private _closeOnClickBackdrop = false;
                    private _showBackdrop = false;
                    private _animate = true;
                    private _trapFocus = true;
                    private _autoFocus = true;
                    private _keyClose = false;
                    private _autoCollapseHeight: number = null;
                    private _autoCollapseWidth: number = null;
                    id: number;
                    private sub: any;

                    private _MODES: Array<string> = ['over', 'push', 'slide'];
                        private _POSITIONS: Array<string> = ['left', 'right', 'top', 'bottom'];

                            private _toggleOpened(): void {
                            this._opened = !this._opened;
                            }

                            private _toggleMode(): void {
                            this._modeNum++;

                            if (this._modeNum === this._MODES.length) {
                            this._modeNum = 0;
                            }
                            }

                            private _toggleAutoCollapseHeight(): void {
                            this._autoCollapseHeight = this._autoCollapseHeight ? null : 500;
                            }

                            private _toggleAutoCollapseWidth(): void {
                            this._autoCollapseWidth = this._autoCollapseWidth ? null : 500;
                            }

                            private _togglePosition(): void {
                            this._positionNum++;

                            if (this._positionNum === this._POSITIONS.length) {
                            this._positionNum = 0;
                            }
                            }

                            private _toggleDock(): void {
                            this._dock = !this._dock;
                            }

                            private _toggleCloseOnClickOutside(): void {
                            this._closeOnClickOutside = !this._closeOnClickOutside;
                            }

                            private _toggleCloseOnClickBackdrop(): void {
                            this._closeOnClickBackdrop = !this._closeOnClickBackdrop;
                            }

                            private _toggleShowBackdrop(): void {
                            this._showBackdrop = !this._showBackdrop;
                            }

                            private _toggleAnimate(): void {
                            this._animate = !this._animate;
                            }

                            private _toggleTrapFocus(): void {
                            this._trapFocus = !this._trapFocus;
                            }

                            private _toggleAutoFocus(): void {
                            this._autoFocus = !this._autoFocus;
                            }

                            private _toggleKeyClose(): void {
                            this._keyClose = !this._keyClose;
                            }

                            private _onOpenStart(): void {
                            }

                            private _onOpened(): void {
                            }

                            private _onCloseStart(): void {
                            }

                            private _onClosed(): void {
                            }

                            constructor(public http: HttpClient,private user: UserService, private route: ActivatedRoute, private router: Router) {
                            this.http=http;
                            }

                            ngOnInit(): void {
                            const body = localStorage.getItem('currentUser');
                            console.log(body);
                            this.http
                            .post('http://localhost:8080/api/service/getName', body)
                            .subscribe(data => {
                            console.log("getName"+data);
                            this.username = JSON.stringify(data).replace("\"","").replace("\"","");
                            });
                            this.http
                            .post('http://localhost:8080/api/service/isEditor', body)
                            .subscribe(data => {
                            this.isEditor = (data === true);
                            });
                            this.http
                            .post('http://localhost:8080/api/service/getJRKEntitaet', body)
                            .subscribe(data => {
                            this.jrkEntitaet = data;
                            });
                            this.fetchEvents();
                            }

                            changeView(message: string){
                            this.view = 'month';
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
                            this.events$ = this.http
                            .post('http://localhost:8080/api/service/getUserTermine', body)
                            .map(json => {
                            console.log('JSON:' , json);

                            return this.convertEvents(json as Termin[]);
                            });
                            }

                            convertEvents(events: Array<Termin>): Array<any>{
                                    const calendarEvents = [];
                                    events.forEach(function(event){
                                    calendarEvents.push({
                                    title: 'Titel: ' + event.title + '<br>Beschreibung: ' + event.beschreibung + '<br>Ort: ' + event.ort,
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


