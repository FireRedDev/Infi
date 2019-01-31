import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ToastrService } from "ngx-toastr";

/**
 * RestService
 */
@Injectable()
export class RestService {

  private url = "http://localhost:8080/api/service/";
  getPersonenstunden(body): any {
    return this.http.post(
      this.url + "getPersonenstunden",
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
    return this.http.get(this.url + "getAllRoles");
  }

  /**
   * insert a Person
   * @param person Person
   * @param jrk JRK
   */
  insertPerson(person, jrk) {
    //insert Person machen
    return this.http.post(
      this.url + "insertPerson/" + jrk,
      person
    );
  }

  /**
   * save the Person
   * @param person Person
   */
  savePerson(person) {
    return this.http.post(
      this.url + "savePerson",
      person
    );
  }

  /**
   * delete Person
   * @param id id of the User
   */
  deletePerson(id) {
    return this.http.post(this.url + "deletePerson", id);
  }

  /**
   * get Users Layer down
   * @param id id of the User
   */
  getUsersLayerDown(id) {
    return this.http.post(
      this.url + "getUsersLayerDown",
      id
    );
  }

  /**
   * get Users Layer down
   * @param id Id of a JRKEntity
   */
  getUsersLayerDownJRK(id) {
    return this.http.post(
      this.url + "getUsersLayerDownJRK",
      id
    );
  }

  /**
   * login
   * @param body Username and Password
   */
  login(body) {
    return this.http.post(this.url + "login", body);
  }

  //Die untergeordneten User mit PersonenID erhalten
  getJRKEntitaetdown(body) {
    return this.http.post(
      this.url + "getJRKEntitaetdown",
      body
    );
  }

  //Untergeordneten Stundenliste erhalten
  getLowerEntityHourList(body) {
    return this.http.post(
      this.url + "getLowerEntityHourList",
      body
    );
  }

  //Jährliche Stundenanzahl pro Person
  getYearlyHoursPerPeople(body) {
    return this.http.post(
      this.url + "getYearlyHoursPerPeople",
      body
    );
  }

  //Die Werte für das Zeitleistendiagramm
  getTimelineValues(body) {
    return this.http.post(
      this.url + "getTimelineValues",
      body
    );
  }

  //Werte für Diagramm
  getChartValues(body) {
    return this.http.post(
      this.url + "getChartValues",
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
      this.url + "insertTermin/" + jrkEntitaet.id,
      actTermin
    );
  }

  /**
   * change Appointment
   * @param actTermin 
   */
  changeTermin(actTermin) {
    return this.http.put(
      this.url + "changeTermin",
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
      this.url + "insertInfo/" + jrkEntitaet.id,
      actInfo
    );
  }

  /**
   * change Info
   * @param actInfo 
   */
  changeInfo(actInfo) {
    return this.http.put(
      this.url + "changeInfo",
      actInfo
    );
  }

  /**
   * get Appointments which are not dokumentated and are in the past
   * @param body 
   */
  getOpenDoko(body) {
    return this.http.post(
      this.url + "getOpenDoko",
      body
    );
  }

  /**
   * Insert new Doku
   * @param actTermin 
   */
  insertDoku(actTermin) {
    return this.http.post(
      this.url + "insertDoko",
      actTermin
    );
  }

  /**
   * Informations of current User
   * @param body 
   */
  getUserInfos(body) {
    return this.http.post(
      this.url + "getUserInfos",
      body
    );
  }

  /**
   * Name of current User
   * @param body 
   */
  getName(body) {
    return this.http.post(this.url + "getName", body);
  }

  /**
   * true if User is Editor
   * @param body 
   */
  isEditor(body) {
    return this.http.post(this.url + "isEditor", body);
  }

  /**
   * true if supervisor
   * @param body 
   */
  isGruppenleiter(body) {
    return this.http.post(
      this.url + "isGruppenleiter",
      body
    );
  }

  /**
   * true if admin
   * @param body 
   */
  isAdmin(body) {
    return this.http.post(this.url + "isAdmin", body);
  }

  /**
   * JRK-Entity of current User
   * @param body 
   */
  getJRKEntitaet(body) {
    return this.http.post(
      this.url + "getJRKEntitaet",
      body
    );
  }

  /**
   * get Appointments from this user
   * @param body 
   */
  getUserTermine(body) {
    return this.http.post(
      this.url + "getUserTermine",
      body
    );
  }

  /**
   * change password
   * @param body 
   */
  changePassword(body) {
    return this.http.post(
      this.url + "changePassword",
      body
    );
  }

  /**
   * need this password beeing changed
   * @param body 
   */
  needPwdChange(body) {
    return this.http.post(
      this.url + "needPwdChange",
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
      this.url + "insertPlanung/" + body,
      text
    );
  }

  /**
   * show Protocol
   * @param jrkEntitaet 
   */
  showprotocol(jrkEntitaet) {
    return this.http.get(this.url + "getProtokollDetails/" + jrkEntitaet.id);
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
      this.url + "saveFCMToken/" + body,
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
      this.url + "getNextIncomingAppointment/" + id
    );
  }

  /**
   * get children
   * @param id 
   */
  getChildren(id) {
    return this.http.post(this.url + "getChildren/", id)
  }

  /**
   * get coming persons for appointment
   * @param id 
   */
  getTerminTeilnehmer(id) {
    return this.http.post(this.url + "getTerminTeilnehmer/", id)
  }

  /**
   * get supervisors
   * @param id 
   */
  getSupervisor(id) {
    return this.http.post(this.url + "getSupervisors/", id)
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
      this.url + "registerAttendee/" + id,
      id2
    );
  }

  /**
   * detlete Appointment
   * @param item 
   */
  deleteTermin(item) {
    return this.http.post(
      this.url + "deleteTermin/",
      item
    );
  }

  /**
   * delete Info
   */
  deleteInfo(item) {
    return this.http.post(
      this.url + "deleteInfo",
      item
    );
  }

  /**
   * mark this planning to be shared
   * @param id 
   */
  sharePlanning(id) {
    return this.http.post(this.url + "sharePlanning/" + id)
  }

  /**
   * get all plannings
   */
  getAllPlanning() {
    return this.http.post(this.url + "sharedPlanning")
  }

  /**
   * get open dokumentations
   * @param body 
   */
  getOpenPlanning(body) {
    return this.http.post(this.url + "getOpenPlanning/" + body)
  }
}
