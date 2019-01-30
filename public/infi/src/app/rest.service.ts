import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ToastrService } from "ngx-toastr";

/**
 * RestService
 */
@Injectable()
export class RestService {
  getPersonenstunden(body): any {
    return this.http.post(
      "http://localhost:8080/api/service/getPersonenstunden",
      body
    );
  }
  private http;

  /**
   * Constructor
   * @param http HttpClient
   * @param toastr Toast-Message
   */
  constructor(http: HttpClient, private toastr: ToastrService) {
    this.http = http;
  }

  /**
   * get all Roles from the Server
   */
  getAllRoles() {
    return this.http.get("http://localhost:8080/api/service/getAllRoles");
  }

  /**
   * insert a Person
   * @param person Person
   * @param jrk JRK
   */
  insertPerson(person, jrk) {
    //insert Person machen
    return this.http.post(
      "http://localhost:8080/api/service/insertPerson/" + jrk,
      person
    );
  }

  /**
   * save the Person
   * @param person Person
   */
  savePerson(person) {
    return this.http.post(
      "http://localhost:8080/api/service/savePerson",
      person
    );
  }

  /**
   * delete Person
   * @param id id of the User
   */
  deletePerson(id) {
    return this.http.post("http://localhost:8080/api/service/deletePerson", id);
  }

  /**
   * get Users Layer down
   * @param id id of the User
   */
  getUsersLayerDown(id) {
    return this.http.post(
      "http://localhost:8080/api/service/getUsersLayerDown",
      id
    );
  }

  /**
   * get Users Layer down
   * @param id Id of a JRKEntity
   */
  getUsersLayerDownJRK(id) {
    return this.http.post(
      "http://localhost:8080/api/service/getUsersLayerDownJRK",
      id
    );
  }

  /**
   * login
   * @param body Username and Password
   */
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

  /**
   * insert Appointment
   * @param jrkEntitaet 
   * @param actTermin 
   */
  insertTermin(jrkEntitaet, actTermin) {
    return this.http.post(
      "http://localhost:8080/api/service/insertTermin/" + jrkEntitaet.id,
      actTermin
    );
  }

  /**
   * change Appointment
   * @param actTermin 
   */
  changeTermin(actTermin) {
    return this.http.put(
      "http://localhost:8080/api/service/changeTermin",
      actTermin
    );
  }

  /**
   * insert Info
   * @param jrkEntitaet 
   * @param actInfo 
   */
  insertInfo(jrkEntitaet, actInfo) {
    return this.http.post(
      "http://localhost:8080/api/service/insertInfo/" + jrkEntitaet.id,
      actInfo
    );
  }

  /**
   * change Info
   * @param actInfo 
   */
  changeInfo(actInfo) {
    return this.http.put(
      "http://localhost:8080/api/service/changeInfo",
      actInfo
    );
  }

  /**
   * get Appointments which are not dokumentated and are in the past
   * @param body 
   */
  getOpenDoko(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getOpenDoko",
      body
    );
  }

  /**
   * Insert new Doku
   * @param actTermin 
   */
  insertDoku(actTermin) {
    return this.http.post(
      "http://localhost:8080/api/service/insertDoko",
      actTermin
    );
  }

  /**
   * Informations of current User
   * @param body 
   */
  getUserInfos(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getUserInfos",
      body
    );
  }

  /**
   * Name of current User
   * @param body 
   */
  getName(body) {
    return this.http.post("http://localhost:8080/api/service/getName", body);
  }

  /**
   * true if User is Editor
   * @param body 
   */
  isEditor(body) {
    return this.http.post("http://localhost:8080/api/service/isEditor", body);
  }

  /**
   * true if supervisor
   * @param body 
   */
  isGruppenleiter(body) {
    return this.http.post(
      "http://localhost:8080/api/service/isGruppenleiter",
      body
    );
  }

  /**
   * true if admin
   * @param body 
   */
  isAdmin(body) {
    return this.http.post("http://localhost:8080/api/service/isAdmin", body);
  }

  /**
   * JRK-Entity of current User
   * @param body 
   */
  getJRKEntitaet(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getJRKEntitaet",
      body
    );
  }

  /**
   * get Appointments from this user
   * @param body 
   */
  getUserTermine(body) {
    return this.http.post(
      "http://localhost:8080/api/service/getUserTermine",
      body
    );
  }

  /**
   * change password
   * @param body 
   */
  changePassword(body) {
    return this.http.post(
      "http://localhost:8080/api/service/changePassword",
      body
    );
  }

  /**
   * need this password beeing changed
   * @param body 
   */
  needPwdChange(body) {
    return this.http.post(
      "http://localhost:8080/api/service/needPwdChange",
      body
    );
  }

  /**
   * upload image
   * @param body 
   * @param filename 
   */
  uploadImage(body, filename) {
    return this.http.post(
      "http://localhost:8080/upload?filename=" + filename,
      body
    );
  }

  /**
   * insert Planning
   * @param body 
   * @param text 
   */
  insertPlannungsText(body, text) {
    console.log(text);
    return this.http.post(
      "http://localhost:8080/api/service/insertPlanung/" + body,
      text
    );
  }

  /**
   * show Protocol
   * @param jrkEntitaet 
   */
  showprotocol(jrkEntitaet) {
    return this.http.get("http://localhost:8080/api/service/getProtokollDetails/" + jrkEntitaet.id);
  }

  /**
   * find Dokumention to id
   * @param id 
   */
  getDokuById(id) {
    console.log("Sending findByID Request to server");
    return this.http.get('http://localhost:8080/api/service/getDokuById/' + id);
  }

  /**
   * send Token for push messages
   * @param body 
   * @param token 
   */
  sendToken(body, token) {
    console.log("id: " + body);
    console.log("token: " + token);
    return this.http.post(
      "http://localhost:8080/api/service/saveFCMToken/" + body,
      token
    );
  }

  /**
   * show error Message
   * @param text1 Header
   * @param text2 Content
   */
  showErrorMessage(text1, text2) {
    this.toastr.error(text1, text2);
  }

  /**
   * show success Message
   * @param text1 Heaeder
   * @param text2 Content
   */
  showSuccessMessage(text1, text2) {
    this.toastr.success(text1, text2);
  }

  /**
   * get current appointment
   * @param id 
   */
  getActTermin(id) {
    return this.http.post(
      "http://localhost:8080/api/service/getNextIncomingAppointment/" + id
    );
  }

  /**
   * get children
   * @param id 
   */
  getChildren(id) {
    return this.http.post("http://localhost:8080/api/service/getChildren/", id)
  }

  /**
   * get coming persons for appointment
   * @param id 
   */
  getTerminTeilnehmer(id) {
    return this.http.post("http://localhost:8080/api/service/getTerminTeilnehmer/", id)
  }

  /**
   * get supervisors
   * @param id 
   */
  getSupervisor(id) {
    return this.http.post("http://localhost:8080/api/service/getSupervisors/", id)
  }

  /**
   * this user is coming
   * @param id 
   * @param id2 
   */
  setComing(id, id2) {
    console.log(id);
    console.log(id2);
    return this.http.post(
      "http://localhost:8080/api/service/registerAttendee/" + id,
      id2
    );
  }

  /**
   * detlete Appointment
   * @param item 
   */
  deleteTermin(item) {
    return this.http.post(
      "http://localhost:8080/api/service/deleteTermin/",
      item
    );
  }

  /**
   * delete Info
   */
  deleteInfo(item, id) {
    return this.http.post(
      "http://localhost:8080/api/service/deleteInfo/" + id,
      item
    );
  }

  /**
   * mark this planning to be shared
   * @param id 
   */
  sharePlanning(id) {
    return this.http.post("http://localhost:8080/api/service/sharePlanning/" + id)
  }

  /**
   * get all plannings
   */
  getAllPlanning() {
    return this.http.post("http://localhost:8080/api/service/sharedPlanning")
  }

  /**
   * get open dokumentations
   * @param body 
   */
  getOpenPlanning(body) {
    return this.http.post("http://localhost:8080/api/service/getOpenPlanning/" + body)
  }
}
