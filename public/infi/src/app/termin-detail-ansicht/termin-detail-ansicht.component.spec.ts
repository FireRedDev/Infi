import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminDetailAnsichtComponent } from './termin-detail-ansicht.component';

describe('TerminDetailAnsichtComponent', () => {
  let component: TerminDetailAnsichtComponent;
  let fixture: ComponentFixture<TerminDetailAnsichtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TerminDetailAnsichtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminDetailAnsichtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
