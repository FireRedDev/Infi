import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { RestService } from '../rest.service';
import { Person } from '../models/person';
import { Role } from '../models/role.enum';


@Component({
  selector: 'add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  @Output() changeView: EventEmitter<string> = new EventEmitter()

  constructor(private rest: RestService) {
    this.rest=rest;
    debugger

   }

   keys() : Array<string> {
    var keys = Object.keys(this.role);
    return keys.slice(keys.length / 2);
  }

   role = Role

  ngOnInit() {
  }

  save(){
      this.rest.insertPerson(this.actPerson)
        .subscribe(data => {
          this.changeView.emit('manage-user');
      });

  }
  
  actPerson: Person = new Person(0,'','','','',Role.KIND);
  submitted = false;
 
  onSubmit() { this.submitted = true; }

}
