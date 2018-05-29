import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RestService {
  private http;

  constructor(http:HttpClient) { 
    this.http=http;
  }

  getAllRoles(){
    return this.http.get('http://localhost:8080/api/service/getAllRoles');
  }
  insertPerson(person,jrk){
    //insert Person machen
    debugger
    return this.http.post('http://localhost:8080/api/service/insertPerson/'+jrk, person);
  }

  savePerson(person){
    debugger
    return this.http.post('http://localhost:8080/api/service/savePerson', person);
  }

  deletePerson(id){
    return this.http.post('http://localhost:8080/api/service/deletePerson', id);
  }

  getUsersLayerDown(id){
    return this.http.post('http://localhost:8080/api/service/getUsersLayerDown',id);
  }

  getUsersLayerDownJRK(id){
    return this.http.post('http://localhost:8080/api/service/getUsersLayerDownJRK',id);
  }

  login(body){
    return this.http.post('http://localhost:8080/api/service/login', body);
  }
  
  getJRKEntitaetdown(body){
    return this.http.post('http://localhost:8080/api/service/getJRKEntitaetdown', body);
  }

  getLowerEntityHourList(body){
    return this.http
    .post('http://localhost:8080/api/service/getLowerEntityHourList', body);
  }

  getYearlyHoursPerPeople(body){
    return this.http
    .post('http://localhost:8080/api/service/getYearlyHoursPerPeople', body);
  }

  getTimelineValues(body){
    return this.http
    .post('http://localhost:8080/api/service/getTimelineValues', body);
  }

  getChartValues(body){
    return this.http
    .post('http://localhost:8080/api/service/getChartValues', body);
  }

  insertTermin(jrkEntitaet,actTermin){
    return this.http
    .post('http://localhost:8080/api/service/insertTermin/'+jrkEntitaet.id, actTermin);
  }

  insertInfo(jrkEntitaet,actInfo){
    return this.http
    .post('http://localhost:8080/api/service/insertInfo/'+jrkEntitaet.id, actInfo);
  }

  getOpenDoko(body){
    return this.http
    .post('http://localhost:8080/api/service/getOpenDoko', body);
  }

  insertDoku(actTermin){
    return this.http
    .post('http://localhost:8080/api/service/insertDoko', actTermin);
  }

  getUserInfos(body){
    return this.http
    .post('http://localhost:8080/api/service/getUserInfos', body);
  }

  getName(body){
    return this.http
    .post('http://localhost:8080/api/service/getName', body);
  }

  isEditor(body){
    return this.http
    .post('http://localhost:8080/api/service/isEditor', body);
  }

  isAdmin(body){
    return this.http
    .post('http://localhost:8080/api/service/isAdmin', body);
  }

  getJRKEntitaet(body){
    return this.http
    .post('http://localhost:8080/api/service/getJRKEntitaet', body);
  }

  getUserTermine(body){
    return this.http
    .post('http://localhost:8080/api/service/getUserTermine', body);
  }

  changePassword(body){
    return this.http
    .post('http://localhost:8080/api/service/changePassword', body);
  }

  needPwdChange(body){
    return this.http
    .post('http://localhost:8080/api/service/needPwdChange', body);
  }
  uploadImage(body,filename){
    return this.http.post("http://localhost:8080/upload?filename="+filename,body);
  }
}
