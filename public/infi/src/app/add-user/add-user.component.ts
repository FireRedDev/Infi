import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { RestService } from '../rest.service';
import { Person } from './person';


@Component({
  selector: 'add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  @Output() changeView: EventEmitter<string> = new EventEmitter()

  constructor(private rest: RestService) {
    this.rest=rest;
   }

  
  ngOnInit() {
  }

  save(){
      this.rest.insertPerson(this.actPerson)
        .subscribe(data => {
          this.changeView.emit('month');
      });

  }
  
  actPerson: Person = new Person(0,'','','','');
  submitted = false;
 
  onSubmit() { this.submitted = true; }

}
