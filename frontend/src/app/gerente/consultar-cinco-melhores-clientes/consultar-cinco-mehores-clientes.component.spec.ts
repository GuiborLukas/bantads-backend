import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultarCincoMelhoresClientesComponent } from './consultar-cinco-melhore-clientes.component';

describe('ConsultarCincoMelhoresClientesComponent', () => {
  let component: ConsultarCincoMelhoresClientesComponent;
  let fixture: ComponentFixture<ConsultarCincoMelhoresClientesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsultarCincoMelhoresClientesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultarCincoMelhoresClientesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
