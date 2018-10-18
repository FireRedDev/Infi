import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-planning',
  templateUrl: './planning.component.html',
  styleUrls: ['./planning.component.css']
})
export class PlanningComponent implements OnInit {
  @Input() calendarEntry;
  @Output() changeView: EventEmitter<string> = new EventEmitter();
  text;
  constructor(public rest: RestService) { }

  ngOnInit() {
  }

  save() {
    const body = this.calendarEntry.id;
    this.rest.insertPlannungsText(body, this.text).subscribe();
    this.changeView.emit("month");
    this.rest.showSuccessMessage("Erfolg", "Plannung eingefügt!");
  }
}
