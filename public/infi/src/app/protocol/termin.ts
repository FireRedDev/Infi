import { Protokoll } from '../protocol/protocol';
import { jrkEntitaet } from '../termin/jrkEntitaet.model';
export class Termin {
    constructor(
        public id=0,
        public s_date='',
        public e_date='',
        public title='',
        public beschreibung='',
        public ort='',
        public jrkEntitaet:jrkEntitaet={id:0,name:'',ort:'',typ:null,termine :null,jrkEntitaet :null,jrkentitaet1:null, persons : null,persons1:null},
        public doko:Protokoll={vzeit: 0,kinderliste:'',taetigkeiten:'',kategorie:'Soziales'}
    )
    {}
}