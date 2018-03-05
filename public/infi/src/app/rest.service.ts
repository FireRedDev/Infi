import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RestService {
  private http;

  constructor(http:HttpClient) { 
    this.http=http;
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

  getJRKEntitaet(body){
    return this.http
    .post('http://localhost:8080/api/service/getJRKEntitaet', body);
  }

  getUserTermine(body){
    return this.http
    .post('http://localhost:8080/api/service/getUserTermine', body);
  }
}
