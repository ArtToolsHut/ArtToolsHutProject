import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegsellerComponent } from './regseller.component';

describe('RegsellerComponent', () => {
  let component: RegsellerComponent;
  let fixture: ComponentFixture<RegsellerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegsellerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegsellerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
