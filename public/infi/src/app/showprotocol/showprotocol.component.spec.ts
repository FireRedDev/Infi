import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowprotocolComponent } from './showprotocol.component';

describe('ShowprotocolComponent', () => {
  let component: ShowprotocolComponent;
  let fixture: ComponentFixture<ShowprotocolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowprotocolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowprotocolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
