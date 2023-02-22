import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultarTodosClientesComponent } from './consultar-todos-clientes.component';

describe('ConsultarTodosClientesComponent', () => {
  let component: ConsultarTodosClientesComponent;
  let fixture: ComponentFixture<ConsultarTodosClientesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultarTodosClientesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultarTodosClientesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
