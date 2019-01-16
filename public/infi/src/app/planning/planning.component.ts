import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RestService } from '../rest.service';

/**
 * Component for writing Planning
 */
@Component({
  selector: 'app-planning',
  templateUrl: './planning.component.html',
  styleUrls: ['./planning.component.css']
})
export class PlanningComponent implements OnInit {
  @Input() calendarEntry;
  @Output() changeView: EventEmitter<string> = new EventEmitter();
  text;
  share = false;
  constructor(public rest: RestService) { }

  ngOnInit() {
  }

  /**
   * save the planning
   */
  save() {
    const body = this.calendarEntry.id;
    this.rest.insertPlannungsText(body, { "plannung": this.text, "share": this.share }).subscribe();
    this.changeView.emit("month");
    this.rest.showSuccessMessage("Erfolg", "Plannung eingefügt!");
  }
}
