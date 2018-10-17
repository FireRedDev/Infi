import { Component, OnInit, Input } from '@angular/core';
import { RestService } from 'src/app/rest.service';

@Component({
  selector: 'app-calendar-detail',
  templateUrl: './calendar-detail.component.html',
  styleUrls: ['./calendar-detail.component.css']
})
export class CalendarDetailComponent implements OnInit {
  @Input() calendarEntry;
  constructor(private rest: RestService) { }

  ngOnInit() {

    console.log(this.calendarEntry);
  }

   /* Plannung speichern */
   private textModel;
   
     save(){
       this.rest.insertPlannungsText(this.textModel).subscribe();
       this.textModel = "";
     }

}
