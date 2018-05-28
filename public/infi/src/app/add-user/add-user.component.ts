import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { RestService } from '../rest.service';
import { Person } from '../models/person';
import { Role } from '../models/role.enum';
import { jrkEntitaet } from '../models/jrkEntitaet.model';


@Component({
  selector: 'add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  @Output() changeView: EventEmitter<string> = new EventEmitter()
  @Input() jrkEntitaet: jrkEntitaet;

  private jrkEnitaet = 2;
  private JRKEntitaeten;

  constructor(private rest: RestService) {
    this.rest=rest;
   }

   keys() : Array<string> {
    var keys = Object.keys(this.role);
    return keys.slice(keys.length / 2);
  }

   role = Role
   jrk:number
   actRole:Role

  ngOnInit() {
    var body = localStorage.getItem('currentUser');
    this.jrkEnitaet = JSON.parse(body);
  
    this.rest.getJRKEntitaetdown(body)
      .subscribe(data => {
        this.JRKEntitaeten=data;
        this.jrk=this.JRKEntitaeten[0].id
    });
  }

  save(){
    this.actPerson.rolle=this.actRole
      this.rest.insertPerson(this.actPerson, this.jrk)
        .subscribe(data => {
          this.changeView.emit('manage-user');
      });

  }

  setRole(r){
    this.actRole=r
  }
  setJRK(id){
    this.jrk=id
  }
  actPerson: Person = new Person(0,'','','','',Role.KIND);
  submitted = false;
 
  onSubmit() { this.submitted = true; }

}
