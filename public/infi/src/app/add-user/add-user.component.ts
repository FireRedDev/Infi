import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { RestService } from '../rest.service';
import { Person } from '../models/person';
import { Role } from '../models/role.enum';
import { jrkEntitaet } from '../models/jrkEntitaet.model';

/**
 * Page which gives the opportunity to add Users
 * 
 * You can choose the role and JRKEntity of the User which is to add.
 * A User has a firstname, a lastname, a email and a password.
 * 
 */
@Component({
  selector: 'add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  /** Output to change view */
  @Output() changeView: EventEmitter<string> = new EventEmitter()
  /** Input to know the jrkEntität */
  @Input() jrkEntitaet: jrkEntitaet;

  /** current jrkEnitaet */
  private jrkEnitaet = 2;
  /** all JRKEnitytis */
  public JRKEntitaeten;

  /**
   * Constructor
   * @param rest RestService
   */
  constructor(private rest: RestService) {
    this.rest = rest;
  }

  /**
   * restructor data for dropdown
   */
  keys(): Array<string> {
    var keys = Object.keys(this.role);
    return keys.slice(keys.length / 2);
  }

  /** Role */
  role = Role
  /** JRK-Entity number */
  jrk: number
  /** current Role */
  actRole: Role
  /** is email correct */
  correctEmail = false

  /**
   * ngInit
   * 
   * get current User and his layerdown Jrkentities
   */
  ngOnInit() {
    var body = localStorage.getItem('currentUser');
    this.jrkEnitaet = JSON.parse(body);

    //rest post
    this.rest.getJRKEntitaetdown(body)
      .subscribe(data => {
        this.JRKEntitaeten = data;
        this.jrk = this.JRKEntitaeten[0].id
      });
  }

  /**
   * save new User
   */
  save() {
    this.actPerson.rolle = this.actRole
    this.rest.insertPerson(this.actPerson, this.jrk)
      .subscribe(data => {
        debugger;
        this.changeView.emit('manage-user');
      });

  }

  /**
   * set Role
   */
  setRole(r) {
    this.actRole = r
  }

  /**
   * set JRK
   */
  setJRK(id) {
    this.jrk = id
  }

  /**
   * current Person
   */
  actPerson: Person = new Person(0, '', '', '', '', Role.KIND);
  /** is submitted */
  submitted = false;

  /**
   * when submitted then submitted is true
   */
  onSubmit() { this.submitted = true; }

  /**
   * is this email correct?
   */
  checkEmail() {
    this.correctEmail = false;
    if (this.validateEmail(this.actPerson.email)) {
      this.correctEmail = true;
    }
  }

  /**
   * check this email
   */
  validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
  }
}
