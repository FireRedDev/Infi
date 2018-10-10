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
  public JRKEntitaeten;

  constructor(private rest: RestService) {
    this.rest=rest;
   }

   //Aufbereiten des Enums für Drowdown
   keys() : Array<string> {
    var keys = Object.keys(this.role);
    return keys.slice(keys.length / 2);
  }

   role = Role
   jrk:number
   actRole:Role
   correctEmail=false

  
  ngOnInit() {
    //Aktuellen Benutzer holen, seine untergeordneten JRK Entitäten nachladen zum Darstellen
    var body = localStorage.getItem('currentUser');
    this.jrkEnitaet = JSON.parse(body);
  
    //rest post
    this.rest.getJRKEntitaetdown(body)
      .subscribe(data => {
        this.JRKEntitaeten=data;
        this.jrk=this.JRKEntitaeten[0].id
    });
  }

  //Person speichern und in die Datenbank speichern
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

  //Ist die E-Mail korrekt?
  checkEmail(){
    this.correctEmail=false;
    if (this.validateEmail(this.actPerson.email)) {
      this.correctEmail=true;
    }
  }

  //checkt die E-Mail
  validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
  }
  

}
