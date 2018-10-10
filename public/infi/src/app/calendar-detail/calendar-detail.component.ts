import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-calendar-detail',
  templateUrl: './calendar-detail.component.html',
  styleUrls: ['./calendar-detail.component.css']
})
export class CalendarDetailComponent implements OnInit {
  @Input() calendarEntry;
  constructor() { }

  ngOnInit() {

    console.log(this.calendarEntry);
  }

}
