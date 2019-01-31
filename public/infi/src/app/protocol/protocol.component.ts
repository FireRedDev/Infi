import { Component, OnInit, Output, Input } from '@angular/core';
import { RestService } from '../rest.service';
import { Protokoll } from './protocol';
import { Termin } from './termin';
import { EventEmitter } from '@angular/core';
import { DateTimeAdapter, OWL_DATE_TIME_LOCALE, OwlDateTimeIntl } from 'ng-pick-datetime';
import { NativeDateTimeAdapter } from 'ng-pick-datetime/date-time/adapter/native-date-time-adapter.class';
import { Platform } from '@angular/cdk/platform';

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

/**
 * Component for writing Protocols
 */
@Component({
  selector: 'app-protocol',
  templateUrl: './protocol.component.html',
  styleUrls: ['./protocol.component.css'],
  providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    { provide: OWL_DATE_TIME_LOCALE, useValue: 'de' },
    { provide: DateTimeAdapter, useClass: NativeDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE, Platform] },
    { provide: OwlDateTimeIntl, useClass: GermanItl },
  ],
})
export class ProtocolComponent implements OnInit {
  private childrens;
  constructor(public rest: RestService) {
    this.newChild = '';
    this.children = [];
    this.supervisor = [];
    this.newBetreuer = '';
    this.betreuer = [];
    this.array = [];
    this.rest = rest;
  }

  @Output() changeView: EventEmitter<string> = new EventEmitter();
  de: any;
  public term: Termin[];
  @Input() calendarEntry;
  dateTime: Date;

  /**
   * get the Appointments where no Documentation is writen
   * get all Children of this JRKEntiy
   * get all Supervisors of this JRKEnity
   */
  ngOnInit() {
    const body = localStorage.getItem('currentUser');
    this.rest.getOpenDoko(body)
      .subscribe(data => {
        this.term = data as Termin[];
        this.actTermin = this.getFirstDate();
        this.s_date = new Date(this.actTermin.s_date);
        this.e_date = new Date(this.actTermin.e_date);
        this.rest.showSuccessMessage("Erfolg", "Termine geladen");
      });

    this.rest.getChildren(body)
      .subscribe(data => {
        this.children = data;
      });

    this.rest.getSupervisor(body)
      .subscribe(data => {
        this.supervisor = data;
      });
  }

  /**
   * save the Documentation
   */
  save() {
    var actTermin = this.actTermin
    //set startdate and enddate of termin
    actTermin.s_date = new Date(this.s_date).toISOString().substr(0, 19).replace('T', ' ');
    actTermin.e_date = new Date(this.e_date).toISOString().substr(0, 19).replace('T', ' ');

    //set child list and supervisor lsit
    this.actProtokol.kinderliste = this.getListe("check");
    this.actProtokol.betreuer = this.getListe("check2");
    
    //set doco
    actTermin.doko = this.actProtokol;
    console.log(this.actProtokol)

    //insert and change view
    this.rest.insertDoku(actTermin)
      .subscribe(data => {
        this.changeView.emit('month');
      });

  }

  actProtokol: Protokoll = new Protokoll(0, null, null, '', '','','','','Soziales');
  actTermin: Termin = new Termin();
  submitted = false;

  onSubmit() { this.submitted = true; }

  /**
   * Choose Appointment
   */
  setTermin(id: any): void {
    var index;
    for (index = 0; index < this.term.length; ++index) {
      //check if term id is the equals the passed id
      if (this.term[index].id == id) {
        //set actTermin
        this.actTermin = this.term[index];
        //set the startdate and the enddate
        this.s_date = new Date(this.actTermin.s_date);
        this.e_date = new Date(this.actTermin.e_date);

        //get Members() and save in array by spliting the string
        this.rest.getTerminTeilnehmer(this.actTermin.id)
          .subscribe(data => {
            this.array = data.split(";");
          });
      }
    }
  }
  newChild: string;
  children: any;
  supervisor: any;
  array;
  listArray;
  newBetreuer: string;
  betreuer: any;
  s_date;
  e_date;

  /**
   * Method to rewrite the html-lists for the saving
   * @param name 
   */
  getListe(name) {
    var list = [];
    //gets an HTMLCollection, uses the class name to reference
    let htmlOption = (document.getElementsByClassName(name) as HTMLCollection);

    //go through the HTMLCollecton
    for (var i = 0; i < htmlOption.length; i++) {
      // save the elments of a collection
      let variable = (htmlOption[i] as HTMLInputElement);

      // look if the variable is checked
      if (variable.checked) {
        //if - add it to the list
        list.push(variable.value);
      }
    }

    return list;
  }

  /**
   * Is the Checkbox checked?
   * @param vorname Firstname
   * @param nachname Lastname
   */
  getValueChecked(vorname, nachname) {
    //look if the user is in the list of comfirmed users
    for (var i = 0; i < this.array.length; i++) {
      // check if the names are equals
      if ((vorname + " " + nachname) == this.array[i]) {
        // set the checked input
        return "checked";
      }
    }
  }

  /**
   * add a children to the dokumentation
   * @param event 
   */
  addChild(event) {
    //split and save in array
    this.listArray = this.newChild.split(" ");
    //add to children array
    this.children.push({ vorname: this.listArray[0], nachname: this.listArray[1] });
    //add to array - to check
    this.array.push(this.listArray[0] + " " + this.listArray[1]);
    this.newChild = '';
    event.preventDefault();
  }

  /**
   * delete a children
   */
  deleteChild(index) {
    this.children.splice(index, 1);
  }

  /**
   * add supervisor
   * 
   * @param event 
   */
  addBetreuer(event) {
    //split and save in array
    this.listArray = this.newBetreuer.split(" ");
    //add to supervisor array
    this.supervisor.push({ vorname: this.listArray[0], nachname: this.listArray[1] });
    //add to array - to check
    this.array.push(this.listArray[0] + " " + this.listArray[1]);
    this.newBetreuer = '';
    event.preventDefault();
  }

  /**
   * delete supervisor
   * @param index 
   */
  deleteBetreuer(index) {
    this.betreuer.splice(index, 1);
  }

  /**
   * get the first Termin
   */
  getFirstDate(): Termin {
    if (this.calendarEntry != null && this.calendarEntry != {}) {
      return this.calendarEntry.termin;
    }
    return this.term[0];
  }

}
