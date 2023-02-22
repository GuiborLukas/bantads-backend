import { ComponentFixture, TestBed } from '@angular/core/testing';

import { depositarComponent } from './depositar.component';

describe('depositarComponent', () => {
  let component: depositarComponent;
  let fixture: ComponentFixture<depositarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ depositarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(depositarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
