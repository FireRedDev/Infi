export class jrkEntitaet {
    id:number;
    name:String;
    ort:String;
    typ:any;
    termine:Object;
    jrkEntitaet:Object;
    jrkentitaet1:Object;
    persons:Object;
    persons1:Object;
  
    constructor(id: number, name: string,ort:string) {
      this.id = id;
      this.name = name;
      this.ort=ort;
    }
}