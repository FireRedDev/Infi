import { RestService } from '../rest.service';
import {
  Component,
  OnInit,
  Output,
  EventEmitter
} from '@angular/core';

/**
 * Component which shows the next Appointment, all Information Articles
 * 
 * The Password Pop-Up is shown in this component.
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public infos;
  public t;
  public jrk;
  showInvite = true;
  isEditor = false;
  @Output() changeViewInfo = new EventEmitter();

  constructor(private rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {
    this.jrk = localStorage.getItem('currentUser');
    this.rest.getUserInfos(this.jrk)
      .subscribe(data => {
        this.infos = data;
        this.rest.showSuccessMessage("Erfolg", "Informationen geladen!");
      });


    this.rest.getActTermin(this.jrk).subscribe(data => {
      this.t = data;
    });

    //Abfragen ob dieser User Editor ist
    this.rest.isEditor(this.jrk)
      .subscribe(data => {
        this.isEditor = (data === true);
      });
  }

  /**
   * this person is comming
   */
  setComing() {
    const body = localStorage.getItem('currentUser');
    this.rest.setComing(this.t.id, body).subscribe();
    this.showInvite = false;
  }

  /**
   * delete Info
   * @param item 
   */
  deleteInfo(item) {
    this.rest.deleteInfo(item).subscribe(data => {
      console.log(data);
      this.infos.splice(this.infos.indexOf(item), 1);
    });
  }
  /**
   * changeto Info view 
   * @param item 
   */
  changeInfo(item) {
    this.changeViewInfo.emit({ "view": "info", "item": item });
  }
}
