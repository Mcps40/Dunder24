import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { JefaturasService } from '../service/jefaturas.service';
import { IJefaturas } from '../jefaturas.model';
import { JefaturasFormService } from './jefaturas-form.service';

import { JefaturasUpdateComponent } from './jefaturas-update.component';

describe('Jefaturas Management Update Component', () => {
  let comp: JefaturasUpdateComponent;
  let fixture: ComponentFixture<JefaturasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jefaturasFormService: JefaturasFormService;
  let jefaturasService: JefaturasService;
  let departamentoService: DepartamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [JefaturasUpdateComponent],
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
      .overrideTemplate(JefaturasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JefaturasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jefaturasFormService = TestBed.inject(JefaturasFormService);
    jefaturasService = TestBed.inject(JefaturasService);
    departamentoService = TestBed.inject(DepartamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Departamento query and add missing value', () => {
      const jefaturas: IJefaturas = { id: 456 };
      const departamento: IDepartamento = { id: 11629 };
      jefaturas.departamento = departamento;

      const departamentoCollection: IDepartamento[] = [{ id: 20797 }];
      jest.spyOn(departamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: departamentoCollection })));
      const additionalDepartamentos = [departamento];
      const expectedCollection: IDepartamento[] = [...additionalDepartamentos, ...departamentoCollection];
      jest.spyOn(departamentoService, 'addDepartamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jefaturas });
      comp.ngOnInit();

      expect(departamentoService.query).toHaveBeenCalled();
      expect(departamentoService.addDepartamentoToCollectionIfMissing).toHaveBeenCalledWith(
        departamentoCollection,
        ...additionalDepartamentos.map(expect.objectContaining),
      );
      expect(comp.departamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const jefaturas: IJefaturas = { id: 456 };
      const departamento: IDepartamento = { id: 31982 };
      jefaturas.departamento = departamento;

      activatedRoute.data = of({ jefaturas });
      comp.ngOnInit();

      expect(comp.departamentosSharedCollection).toContain(departamento);
      expect(comp.jefaturas).toEqual(jefaturas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJefaturas>>();
      const jefaturas = { id: 123 };
      jest.spyOn(jefaturasFormService, 'getJefaturas').mockReturnValue(jefaturas);
      jest.spyOn(jefaturasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jefaturas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jefaturas }));
      saveSubject.complete();

      // THEN
      expect(jefaturasFormService.getJefaturas).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(jefaturasService.update).toHaveBeenCalledWith(expect.objectContaining(jefaturas));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJefaturas>>();
      const jefaturas = { id: 123 };
      jest.spyOn(jefaturasFormService, 'getJefaturas').mockReturnValue({ id: null });
      jest.spyOn(jefaturasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jefaturas: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jefaturas }));
      saveSubject.complete();

      // THEN
      expect(jefaturasFormService.getJefaturas).toHaveBeenCalled();
      expect(jefaturasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJefaturas>>();
      const jefaturas = { id: 123 };
      jest.spyOn(jefaturasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jefaturas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jefaturasService.update).toHaveBeenCalled();
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
  });
});
