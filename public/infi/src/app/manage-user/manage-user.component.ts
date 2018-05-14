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
private JRKEntitaeten;
data: any;
isEditable:boolean = false;
persons;
@Output() changeView: EventEmitter<string> = new EventEmitter()

constructor(private rest: RestService) {
  this.rest = rest;  
}

ngOnInit(){
  var body = localStorage.getItem('currentUser');
  this.jrkEnitaet = JSON.parse(body);

  this.rest.getJRKEntitaetdown(body)
    .subscribe(data => {
      this.JRKEntitaeten=data;
  });

  this.rest.getUsersLayerDown(body).subscribe(data => {
    this.persons = data;
  });
}

edit(i:number){
  this.persons[i].isEditable = true;
}

done(i:number){
  this.persons[i].isEditable = false;
  this.rest.savePerson(this.persons[i]);
}

deletePerson(i:number){
alert("ID vom gelöschten Benutzer: " + this.persons[i].id);
this.rest.deletePerson(this.persons[i].id).subscribe(()=>{console.log("gelöscht")});
this.persons.splice(i,1);
}

addUser(){
this.changeView.emit("add-user");
}

}
