import { jrkEntitaet } from './jrkEntitaet.model';

export class Info {
    constructor(
    public id=0,
    public ueberschrift='',
    public beschreibung='',
    public mediapath=[],
    public datum=''
    )
    {}
}