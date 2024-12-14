import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IJefaturas } from 'app/entities/jefaturas/jefaturas.model';
import { JefaturasService } from 'app/entities/jefaturas/service/jefaturas.service';
import { IEmpleados } from '../empleados.model';
import { EmpleadosService } from '../service/empleados.service';
import { EmpleadosFormService } from './empleados-form.service';

import { EmpleadosUpdateComponent } from './empleados-update.component';

describe('Empleados Management Update Component', () => {
  let comp: EmpleadosUpdateComponent;
  let fixture: ComponentFixture<EmpleadosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empleadosFormService: EmpleadosFormService;
  let empleadosService: EmpleadosService;
  let departamentoService: DepartamentoService;
  let jefaturasService: JefaturasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpleadosUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmpleadosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpleadosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empleadosFormService = TestBed.inject(EmpleadosFormService);
    empleadosService = TestBed.inject(EmpleadosService);
    departamentoService = TestBed.inject(DepartamentoService);
    jefaturasService = TestBed.inject(JefaturasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Departamento query and add missing value', () => {
      const empleados: IEmpleados = { id: 456 };
      const departamento: IDepartamento = { id: 7731 };
      empleados.departamento = departamento;

      const departamentoCollection: IDepartamento[] = [{ id: 30084 }];
      jest.spyOn(departamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: departamentoCollection })));
      const additionalDepartamentos = [departamento];
      const expectedCollection: IDepartamento[] = [...additionalDepartamentos, ...departamentoCollection];
      jest.spyOn(departamentoService, 'addDepartamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empleados });
      comp.ngOnInit();

      expect(departamentoService.query).toHaveBeenCalled();
      expect(departamentoService.addDepartamentoToCollectionIfMissing).toHaveBeenCalledWith(
        departamentoCollection,
        ...additionalDepartamentos.map(expect.objectContaining),
      );
      expect(comp.departamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Jefaturas query and add missing value', () => {
      const empleados: IEmpleados = { id: 456 };
      const jefatura: IJefaturas = { id: 1730 };
      empleados.jefatura = jefatura;

      const jefaturasCollection: IJefaturas[] = [{ id: 13436 }];
      jest.spyOn(jefaturasService, 'query').mockReturnValue(of(new HttpResponse({ body: jefaturasCollection })));
      const additionalJefaturas = [jefatura];
      const expectedCollection: IJefaturas[] = [...additionalJefaturas, ...jefaturasCollection];
      jest.spyOn(jefaturasService, 'addJefaturasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empleados });
      comp.ngOnInit();

      expect(jefaturasService.query).toHaveBeenCalled();
      expect(jefaturasService.addJefaturasToCollectionIfMissing).toHaveBeenCalledWith(
        jefaturasCollection,
        ...additionalJefaturas.map(expect.objectContaining),
      );
      expect(comp.jefaturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empleados: IEmpleados = { id: 456 };
      const departamento: IDepartamento = { id: 29301 };
      empleados.departamento = departamento;
      const jefatura: IJefaturas = { id: 4882 };
      empleados.jefatura = jefatura;

      activatedRoute.data = of({ empleados });
      comp.ngOnInit();

      expect(comp.departamentosSharedCollection).toContain(departamento);
      expect(comp.jefaturasSharedCollection).toContain(jefatura);
      expect(comp.empleados).toEqual(empleados);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpleados>>();
      const empleados = { id: 123 };
      jest.spyOn(empleadosFormService, 'getEmpleados').mockReturnValue(empleados);
      jest.spyOn(empleadosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empleados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empleados }));
      saveSubject.complete();

      // THEN
      expect(empleadosFormService.getEmpleados).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empleadosService.update).toHaveBeenCalledWith(expect.objectContaining(empleados));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpleados>>();
      const empleados = { id: 123 };
      jest.spyOn(empleadosFormService, 'getEmpleados').mockReturnValue({ id: null });
      jest.spyOn(empleadosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empleados: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empleados }));
      saveSubject.complete();

      // THEN
      expect(empleadosFormService.getEmpleados).toHaveBeenCalled();
      expect(empleadosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpleados>>();
      const empleados = { id: 123 };
      jest.spyOn(empleadosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empleados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empleadosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDepartamento', () => {
      it('Should forward to departamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(departamentoService, 'compareDepartamento');
        comp.compareDepartamento(entity, entity2);
        expect(departamentoService.compareDepartamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareJefaturas', () => {
      it('Should forward to jefaturasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(jefaturasService, 'compareJefaturas');
        comp.compareJefaturas(entity, entity2);
        expect(jefaturasService.compareJefaturas).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
