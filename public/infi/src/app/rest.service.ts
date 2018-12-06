import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ToastrService } from "ngx-toastr";

@Injectable()
export class RestService {
  getPersonenstunden(body): any {
    return this.http.post(
      "http://localhost:8080/api/service/getPersonenstunden",
      body
    );
  }
  private http;

  constructor(http: HttpClient, private toastr: ToastrService) {
    this.http = http;
  }

  //Alle Rollen vom Server holen
  getAllRoles() {
    return this.http.get("http://localhost:8080/api/service/getAllRoles");
  }

  //Eine neue Person in die Datenbanm einfügen
  insertPerson(person, jrk) {
    //insert Person machen
    return this.http.post(
      "http://localhost:8080/api/service/insertPerson/" + jrk,
      person
    );
  }

  //Speichern von einer abgeänderten Person
  savePerson(person) {
    return this.http.post(
      "http://localhost:8080/api/service/savePerson",
      person
    );
  }

  //Person löschen
  deletePerson(id) {
    return this.http.post("http://localhost:8080/api/service/deletePerson", id);
  }

  //Die untergeordneten Personen
  getUsersLayerDown(id) {
    return this.http.post(
      "http://localhost:8080/api/service/getUsersLayerDown",
      id
    );
  }

  //Die untergeordneten User mit JRKEntitätenID erhalten
  getUsersLayerDownJRK(id) {
    return this.http.post(
      "http://localhost:8080/api/service/getUsersLayerDownJRK",
      id
    );
  }

  //Einloggen eines Benutzers
  login(body) {
    return this.http.post("http://localhost:8080/api/service/login", body);
  }

  //Die untergeordneten User mit PersonenID erhalten
  getJRKEntitaetdown(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getJRKEntitaetdown",
      body
    );
  }

  //Untergeordneten Stundenliste erhalten
  getLowerEntityHourList(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getLowerEntityHourList",
      body
    );
  }

  //Jährliche Stundenanzahl pro Person
  getYearlyHoursPerPeople(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getYearlyHoursPerPeople",
      body
    );
  }

  //Die Werte für das Zeitleistendiagramm
  getTimelineValues(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getTimelineValues",
      body
    );
  }

  //Werte für Diagramm
  getChartValues(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getChartValues",
      body
    );
  }

  //Termin einfügen
  insertTermin(jrkEntitaet, actTermin) {
    return this.http.post(
      "http://localhost:8080/api/service/insertTermin/" + jrkEntitaet.id,
      actTermin
    );
  }
  //Termin ändern
  changeTermin(actTermin) {
    return this.http.put(
      "http://localhost:8080/api/service/changeTermin",
      actTermin
    );
  }

  //Information einfügen
  insertInfo(jrkEntitaet, actInfo) {
    return this.http.post(
      "http://localhost:8080/api/service/insertInsfo/" + jrkEntitaet.id,
      actInfo
    );
  }
  //Information ändern
  changeInfo(actInfo) {
    return this.http.put(
      "http://localhost:8080/api/service/changeInfo",
      actInfo
    );
  }

  //offene Dokumentation erhalten
  getOpenDoko(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getOpenDoko",
      body
    );
  }

  //Neue Dokumentation einfügen
  insertDoku(actTermin) {
    return this.http.post(
      "http://localhost:8080/api/service/insertDoko",
      actTermin
    );
  }

  //Informationen zu Benutzer holen
  getUserInfos(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getUserInfos",
      body
    );
  }

  //Name des Benutzers der gerade angemeldet ist
  getName(body) {
    return this.http.post("http://localhost:8080/api/service/getName", body);
  }

  //True oder False je nach dem ob der angemeldete Benutzer berechtigt ist
  //zu editieren oder nicht
  isEditor(body) {
    return this.http.post("http://localhost:8080/api/service/isEditor", body);
  }

  //True oder False je nach dem ob der angemeldete Benutzer Gruppenleiter ist
  isGruppenleiter(body) {
    return this.http.post(
      "http://localhost:8080/api/service/isGruppenleiter",
      body
    );
  }

  //True oder False ob Admin oder nicht
  isAdmin(body) {
    return this.http.post("http://localhost:8080/api/service/isAdmin", body);
  }

  //JRKEntität des aktuell angemeldeten Benutzers
  getJRKEntitaet(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getJRKEntitaet",
      body
    );
  }

  //Terminde für User
  getUserTermine(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getUserTermine",
      body
    );
  }

  //Passwort ändern
  changePassword(body) {
    return this.http.post(
      "http://localhost:8080/api/service/changePassword",
      body
    );
  }

  //Ob Passwort noch geändert werden muss
  needPwdChange(body) {
    return this.http.post(
      "http://localhost:8080/api/service/needPwdChange",
      body
    );
  }

  //Bild hochladen
  uploadImage(body, filename) {
    return this.http.post(
      "http://localhost:8080/upload?filename=" + filename,
      body
    );
  }

  //Bild hochladen
  insertPlannungsText(body, text) {
    console.log(text);
    return this.http.post(
      "http://localhost:8080/api/service/insertPlanung/" + body,
      text
    );
  }

  showprotocol(jrkEntitaet) {
    return this.http.get(
      "http://localhost:8080/api/service/getProtokollDetails/" + jrkEntitaet.id
    );
  }

  sendToken(body, token) {
    console.log("id: " + body);
    console.log("token: " + token);
    return this.http.post(
      "http://localhost:8080/api/service/saveFCMToken/" + body,
      token
    );
  }

  showErrorMessage(text1, text2) {
    this.toastr.error(text1, text2);
  }

  showSuccessMessage(text1, text2) {
    this.toastr.success(text1, text2);
  }

  getActTermin(id) {
    return this.http.post(
      "http://localhost:8080/api/service/getNextIncomingAppointment/" + id
    );
  }

  setComing(id, id2) {
    console.log(id);
    console.log(id2);
    return this.http.post(
      "http://localhost:8080/api/service/registerAttendee/" + id,
      id2
    );
  }
  deleteTermin(item) {
    return this.http.post(
      "http://localhost:8080/api/service/deleteTermin/",
      item
    );
  }

  deleteInfo(item) {
    return this.http.post(
      "http://localhost:8080/api/service/deleteInfo/",
      item
    );
  }
  sharePlanning(body) {
    return this.http.post(
      "http://localhost:8080/api/service/sharePlanning/",
      body
    );
  }
}
