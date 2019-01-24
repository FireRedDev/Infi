import { RestService } from '../rest.service';
import { Planning } from 'src/app/planning/Planning';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Termin } from '../protocol/termin';

/**
 * show all Plannings which can be used from other Users
 */
@Component({
  selector: 'app-showplanning',
  templateUrl: './showplanning.component.html',
  styleUrls: ['./showplanning.component.css']
})
export class ShowplanningComponent implements OnInit {
  @Input() jrkEntitaet;
  @Output() changeView: EventEmitter<string> = new EventEmitter();
  @Output() showProtocol: EventEmitter<any> = new EventEmitter();

  actTermin: Termin = new Termin();

  constructor(
    public rest: RestService
  ) { }

  /**
   * get All Plannings
   */
  ngOnInit() {
    this.rest.getAllPlanning().subscribe(data => {
      this.termins = data;
      for (var i = 0; i < this.termins.length; i++) {
        var plan = this.termins[i].planning;
        this.isEdit[i] = false;
        const myObj = { plannung: plan.plannung };
        this.records.push(myObj);
      }
    });
    const body = localStorage.getItem('currentUser');
    this.rest.getOpenPlanning(body).subscribe(data => {
      this.terminsOpenPlaning = data;
    });
  }
  private isEdit = [];
  private termins = [];
  private terminsOpenPlaning = [];
  records = [];

  /**
   * choose Apointment
   * @param id Termin id
   */
  setTermin(id: any): void {
    var index;
    for (index = 0; index < this.terminsOpenPlaning.length; ++index) {
      if (this.terminsOpenPlaning[index].id == id) {
        this.actTermin = this.terminsOpenPlaning[index];
      }
    }
  }

  /**
   * show details
   * @param id 
   */
  showDetail(id) {
    this.showProtocol.emit(id);
  }

  /**
   * Save the Planing
   * @param plan 
   */
  savePlaning(plan) {
    const body = this.actTermin.id;
    this.rest.insertPlannungsText(body, plan).subscribe();
    this.changeView.emit("month");
    this.rest.showSuccessMessage("Erfolg", "Plannung eingefügt!");
  }
}
