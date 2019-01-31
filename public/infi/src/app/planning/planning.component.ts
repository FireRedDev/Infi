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
  //data from parent
  @Input() calendarEntry;
  //send to parent
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
    //save the planning
    this.rest.insertPlannungsText(body, { "plannung": this.text, "share": this.share }).subscribe();
    //change view
    this.changeView.emit("month");
    //success message
    this.rest.showSuccessMessage("Erfolg", "Plannung eingefügt!");
  }
}
