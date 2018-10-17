import { Component, OnInit, Output, Input, ChangeDetectionStrategy } from '@angular/core';
import { RestService } from '../rest.service';
import { Protokoll } from './protocol';
import { Termin } from './termin';
import { EventEmitter } from '@angular/core';
import { DateTimeAdapter, OWL_DATE_TIME_LOCALE, OwlDateTimeIntl } from 'ng-pick-datetime';
import { NativeDateTimeAdapter } from 'ng-pick-datetime/date-time/adapter/native-date-time-adapter.class';
import { Platform } from '@angular/cdk/platform';

// here is the default text string
export class GermanItl extends OwlDateTimeIntl {
  /** A label for the up second button (used by screen readers).  */
  upSecondLabel = 'Sekunde mehr';

  /** A label for the down second button (used by screen readers).  */
  downSecondLabel = 'Sekunde weniger';

  /** A label for the up minute button (used by screen readers).  */
  upMinuteLabel = 'Minute mehr';

  /** A label for the down minute button (used by screen readers).  */
  downMinuteLabel = 'Minute weniger';

  /** A label for the up hour button (used by screen readers).  */
  upHourLabel = 'Stunde mehr';

  /** A label for the down hour button (used by screen readers).  */
  downHourLabel = 'Stunde weniger';

  /** A label for the previous month button (used by screen readers). */
  prevMonthLabel = 'Monat davor';

  /** A label for the next month button (used by screen readers). */
  nextMonthLabel = 'Monat danach';

  /** A label for the previous year button (used by screen readers). */
  prevYearLabel = 'Jahr davor';

  /** A label for the next year button (used by screen readers). */
  nextYearLabel = 'nächstes Jahr';

  /** A label for the previous multi-year button (used by screen readers). */
  prevMultiYearLabel = '21 Jahre davor';

  /** A label for the next multi-year button (used by screen readers). */
  nextMultiYearLabel = '21 Jahre danach';

  /** A label for the 'switch to month view' button (used by screen readers). */
  switchToMonthViewLabel = 'Zu Monatsansicht wechseln';

  /** A label for the 'switch to year view' button (used by screen readers). */
  switchToMultiYearViewLabel = 'Monat und Jahr auswählen';

  /** A label for the cancel button */
  cancelBtnLabel = 'Schließen';

  /** A label for the set button */
  setBtnLabel = 'Setzten';

  /** A label for the range 'from' in picker info */
  rangeFromLabel = 'von';

  /** A label for the range 'to' in picker info */
  rangeToLabel = 'bis';

  /** A label for the hour12 button (AM) */
  hour12AMLabel = 'AM';

  /** A label for the hour12 button (PM) */
  hour12PMLabel = 'PM';
}

@Component({
  selector: 'app-protocol',
  templateUrl: './protocol.component.html',
  styleUrls: ['./protocol.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    { provide: OWL_DATE_TIME_LOCALE, useValue: 'de' },
    { provide: DateTimeAdapter, useClass: NativeDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE, Platform] },
    { provide: OwlDateTimeIntl, useClass: GermanItl },
  ],
})
export class ProtocolComponent implements OnInit {
  constructor(public rest: RestService) {
    this.newChild = '';
    this.children = [];
    this.newBetreuer = '';
    this.betreuer = [];
    this.rest = rest;
    //dateTimeAdapter.setLocale('de-De');
  }

  @Output() changeView: EventEmitter<string> = new EventEmitter();
  de: any;
  public term: Termin[];
  @Input() protocolentry;
  dateTime: Date;

  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    console.log(this.protocolentry);
    this.rest.getOpenDoko(body)
      .subscribe(data => {
        this.term = data as Termin[];
        this.actTermin = this.getFirstDate();
        this.s_date = this.actTermin.s_date;
        this.e_date = this.actTermin.e_date;
        this.rest.showMessage("Termine geladen", "")
      });
  }
  save() {
    var actTermin = this.actTermin
    actTermin.s_date = new Date(this.s_date).toISOString().substr(0, 19).replace('T', ' ');
    actTermin.e_date = new Date(this.e_date).toISOString().substr(0, 19).replace('T', ' ');
    this.actProtokol.kinderliste = this.children;
    this.actProtokol.betreuer = this.betreuer;
    actTermin.doko = this.actProtokol;
    this.rest.insertDoku(actTermin)
      .subscribe(data => {
        this.changeView.emit('month');
      });

  }

  actProtokol: Protokoll = new Protokoll(0, null, null, '', 'Soziales');
  actTermin: Termin = new Termin();
  submitted = false;

  onSubmit() { this.submitted = true; }

  setTermin(id: any): void {
    var index;
    for (index = 0; index < this.term.length; ++index) {
      if (this.term[index].id == id) {
        this.actTermin = this.term[index];
        this.s_date = this.actTermin.s_date;
        this.e_date = this.actTermin.e_date;
      }
    }
  }
  newChild: string;
  children: any;

  newBetreuer: string;
  betreuer: any;
  s_date;
  e_date;

  addChild(event) {
    this.children.push(this.newChild);
    this.newChild = '';
    event.preventDefault();
  }

  deleteChild(index) {
    this.children.splice(index, 1);
  }

  addBetreuer(event) {
    this.betreuer.push(this.newBetreuer);
    this.newBetreuer = '';
    event.preventDefault();
  }

  deleteBetreuer(index) {
    this.betreuer.splice(index, 1);
  }

  getFirstDate(): Termin {
    if (this.protocolentry != null) {
      return this.protocolentry.termin;
    }
    return this.term[0];
  }

}

