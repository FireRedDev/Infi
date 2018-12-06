import { RestService } from '../rest.service';
import {
  Component,
  OnInit,
  Output,
  EventEmitter
} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public infos;
  public t;
  showInvite = true;
  isEditor = false;
  @Output() changeViewInfo = new EventEmitter();

  constructor(private rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {

    const body = localStorage.getItem('currentUser');

    this.rest.getUserInfos(body)
      .subscribe(data => {
        this.infos = data;
        this.rest.showSuccessMessage("Erfolg", "Informationen geladen!");
      });


    this.rest.getActTermin(body).subscribe(data => {
      this.t = data;
    });

    //Abfragen ob dieser User Editor ist
    this.rest.isEditor(body)
      .subscribe(data => {
        this.isEditor = (data === true);
      });
  }

  setComing() {
    const body = localStorage.getItem('currentUser');
    this.rest.setComing(this.t.id, body).subscribe();
    this.showInvite = false;
  }

  deleteInfo(item) {
    this.rest.deleteInfo(item).subscribe(data => {
      console.log(data);
      this.infos.splice(this.infos.indexOf(item), 1);
    });
  }
  changeInfo(item) {
    this.changeViewInfo.emit({ "view": "info", "item": item });
  }
}
