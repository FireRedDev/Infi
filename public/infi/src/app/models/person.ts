import { jrkEntitaet } from './jrkEntitaet.model';
import { Role } from './role.enum';
export class Person {
constructor(
public id=0,
public email='',
public password='',
public vorname='',
public nachname='',
public rolle:Role
)
{}
}
