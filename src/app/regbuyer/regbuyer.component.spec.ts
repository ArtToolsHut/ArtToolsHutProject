import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegbuyerComponent } from './regbuyer.component';

describe('RegbuyerComponent', () => {
  let component: RegbuyerComponent;
  let fixture: ComponentFixture<RegbuyerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegbuyerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegbuyerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
