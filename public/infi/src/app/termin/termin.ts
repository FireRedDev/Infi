import { jrkEntitaet } from './jrkEntitaet.model';

export class Termin {
    constructor(
        public id=0,
        public s_date='12-12-2017 00:00',
        public e_date='12-12-2017 00:00',
        public title='',
        public beschreibung='',
        public ort=''
    )
    {}
}