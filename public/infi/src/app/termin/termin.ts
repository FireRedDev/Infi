import { jrkEntitaet } from "./jrkEntitaet.model";

export class Termin {
    constructor(
        private id=0,
        private s_date='12-12-2017',
        private e_date='12-12-2017',
        private title='',
        private beschreibung='',
        private ort='',
        public jrkEntitaet:jrkEntitaet={id:0,name:'',ort:'',typ:null,termine :null,jrkEntitaet :null,jrkentitaet1:null, persons : null,persons1:null}
    )
    {}
}