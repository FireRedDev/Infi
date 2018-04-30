export class Benutzer {
id: number;
personalnr: string;
password: string;
  
constructor(personalnr: string, password: string) {
this.personalnr = personalnr;
this.password = password;
}

}