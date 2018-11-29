import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowplanningComponent } from './showplanning.component';

describe('ShowplanningComponent', () => {
  let component: ShowplanningComponent;
  let fixture: ComponentFixture<ShowplanningComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowplanningComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowplanningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
