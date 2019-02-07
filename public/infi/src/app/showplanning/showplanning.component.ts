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
  private isEdit = [];
  private terminsOpenPlaning = [];
  private records = [];
  public isThereAPlanning = false;

  constructor(
    public rest: RestService
  ) { }

  /**
   * get All Plannings
   */
  ngOnInit() {
    this.rest.getAllPlanning().subscribe(data => {
      var termins = data;
      //go through the array of termins
      for (var i = 0; i < termins.length; i++) {
        // get the planning
        var plan = termins[i].planning;
        this.isEdit[i] = false;
        const myObj = { plannung: plan.plannung };
        // push it to the records
        this.records.push(myObj);
        this.isThereAPlanning = true;
      }
    });
    //get current user
    const body = localStorage.getItem('currentUser');
    //get termine where no planning exsits
    this.rest.getOpenPlanning(body).subscribe(data => {
      this.terminsOpenPlaning = data;
    });
  }
  /**
   * choose Apointment
   * @param id Termin id
   */
  setTermin(id: any): void {
    var index;
    for (index = 0; index < this.terminsOpenPlaning.length; ++index) {
      //check if termin has plaaning?
      if (this.terminsOpenPlaning[index].id == id) {
        // set the termin
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
    //insert planning
    this.rest.insertPlannungsText(body, plan).subscribe();
    //change view
    this.changeView.emit("month");
    //success message
    this.rest.showSuccessMessage("Erfolg", "Plannung eingefügt!");
  }
}
