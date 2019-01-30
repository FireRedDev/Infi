import { Component, OnInit, Output } from '@angular/core';
import { RestService } from '../rest.service';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {

  private jrkEnitaet = 2;
  public JRKEntitaeten;
  data: any;
  isEditable: boolean = false;
  persons;
  @Output() changeView: EventEmitter<string> = new EventEmitter()

  constructor(private rest: RestService) {
    this.rest = rest;
  }

  ngOnInit() {
    var body = localStorage.getItem('currentUser');
    this.jrkEnitaet = JSON.parse(body);

    this.rest.getJRKEntitaetdown(body)
      .subscribe(data => {
        this.JRKEntitaeten = data;
      });

    this.rest.getUsersLayerDown(body).subscribe(data => {
      this.persons = data;
    });
  }

  /**
   * edit User
   */
  edit(i: number) {
    this.persons[i].isEditable = true;
  }

  /** 
   * save User
   */
  done(i: number) {
    this.persons[i].isEditable = false;
    var person = this.persons[i]
    delete person.isEditable
    this.rest.savePerson(person).subscribe(data => {
      this.rest.showSuccessMessage("Erfolg", "Benutzer upgedated!");
    });
  }

  /**
   * delete a person
   * @param i 
   */
  deletePerson(i: number) {
    this.rest.deletePerson(this.persons[i].id).subscribe(() => {
      this.rest.showSuccessMessage("Erfolg", "Person erfolgreich gelöscht!");
    });
    this.persons.splice(i, 1);
  }

  /**
   * new User
   */
  addUser() {
    this.changeView.emit("add-user");
  }

  /**
   * set JRKEntity
   * @param id 
   */
  set(id) {
    this.rest.getUsersLayerDownJRK(id).subscribe(data => {
      this.persons = data;
    });
  }
}
