import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DateconfirmComponent } from './dateconfirm.component';

describe('DateconfirmComponent', () => {
  let component: DateconfirmComponent;
  let fixture: ComponentFixture<DateconfirmComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DateconfirmComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DateconfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
