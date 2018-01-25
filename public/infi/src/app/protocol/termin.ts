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
        public doko:Protokoll={vzeit: 0,kinderliste:[],betreuer:[],taetigkeiten:'',kategorie:'Soziales'}
    )
    {}
}