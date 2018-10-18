import { Component, OnInit, Input, Output, ChangeDetectionStrategy } from '@angular/core';
import { Termin } from '../models/termin';
import { RestService } from '../rest.service';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
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
  selector: 'app-termin',
  templateUrl: './termin.component.html',
  styleUrls: ['./termin.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    { provide: OWL_DATE_TIME_LOCALE, useValue: 'de' },
    { provide: DateTimeAdapter, useClass: NativeDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE, Platform] },
    { provide: OwlDateTimeIntl, useClass: GermanItl },
  ],
})


export class TerminComponent implements OnInit {
  @Input() jrkEntitaet: jrkEntitaet;
  @Output() changeView: EventEmitter<string> = new EventEmitter();

  success = false;
  fileInput;
  fileDisplayArea;
  fileerror = false;
  imgpath;
  file;
  uploaded = false
  e_date;
  s_date;

  constructor(private rest: RestService, dateTimeAdapter: DateTimeAdapter<any>) {
    this.rest = rest;
    dateTimeAdapter.setLocale('de-De');
  }

  ngOnInit() {

  }
  save() {
    var actTermin = this.actTermin
    actTermin.s_date = new Date(this.s_date).toISOString().substr(0, 19).replace('T', ' ');
    actTermin.e_date = new Date(this.e_date).toISOString().substr(0, 19).replace('T', ' ');
    if (this.file !== undefined) {
      actTermin.imgpath = "http://localhost:8080/upload_image/" + this.file[0].name;
    }
    if (!this.fileerror) {
      this.rest.insertTermin(this.jrkEntitaet, this.actTermin)
        .subscribe(data => {
          this.rest.showSuccessMessage("Erfolg","Termin eingefügt");
          this.changeView.emit("month");
        });
      this.success = true;
    }
  }

  actTermin: Termin = new Termin(0, '', '', '', '', '', '');
  submitted = false;

  onSubmit() { this.submitted = true; }

  fileUpload(e) {
    this.fileerror = false;
    this.fileInput = document.getElementById('fileInput');
    this.file = this.fileInput.files
    var file = this.fileInput.files[0]
    console.log("File" + file)
    var rest = this.rest;
    var imageType = /image.*/;
    if (file.type.match(imageType) && this.file[0].size < 1097152) {
      var reader = new FileReader();

      reader.onload = function (e) {
        var dataURI = reader.result;
        console.log(dataURI);
        //https://stackoverflow.com/questions/12168909/blob-from-dataurl
        // convert base64 to raw binary data held in a string
        // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
        var byteString = atob(dataURI.split(',')[1]);

        // separate out the mime component
        var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

        // write the bytes of the string to an ArrayBuffer
        var ab = new ArrayBuffer(byteString.length);

        // create a view into the buffer
        var ia = new Uint8Array(ab);

        // set the bytes of the buffer to the correct values
        for (var i = 0; i < byteString.length; i++) {
          ia[i] = byteString.charCodeAt(i);
        }

        // write the ArrayBuffer to a blob, and you're done
        var blob = new Blob([ab], { type: mimeString });

        console.log(blob);
        rest.uploadImage(blob, file.name)
          .subscribe(data => {
            console.log("insertImage")
            alert("Bild hochgeladen")
          });

      }

      reader.readAsDataURL(file);
    } else {
      this.fileDisplayArea = "File not supported!";
      this.fileerror = true;
    }
  }
}
