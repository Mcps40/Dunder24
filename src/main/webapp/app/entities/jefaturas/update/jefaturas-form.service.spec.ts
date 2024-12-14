import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../jefaturas.test-samples';

import { JefaturasFormService } from './jefaturas-form.service';

describe('Jefaturas Form Service', () => {
  let service: JefaturasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JefaturasFormService);
  });

  describe('Service methods', () => {
    describe('createJefaturasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJefaturasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre_jefe: expect.any(Object),
            departamento: expect.any(Object),
          }),
        );
      });

      it('passing IJefaturas should create a new form with FormGroup', () => {
        const formGroup = service.createJefaturasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre_jefe: expect.any(Object),
            departamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getJefaturas', () => {
      it('should return NewJefaturas for default Jefaturas initial value', () => {
        const formGroup = service.createJefaturasFormGroup(sampleWithNewData);

        const jefaturas = service.getJefaturas(formGroup) as any;

        expect(jefaturas).toMatchObject(sampleWithNewData);
      });

      it('should return NewJefaturas for empty Jefaturas initial value', () => {
        const formGroup = service.createJefaturasFormGroup();

        const jefaturas = service.getJefaturas(formGroup) as any;

        expect(jefaturas).toMatchObject({});
      });

      it('should return IJefaturas', () => {
        const formGroup = service.createJefaturasFormGroup(sampleWithRequiredData);

        const jefaturas = service.getJefaturas(formGroup) as any;

        expect(jefaturas).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJefaturas should not enable id FormControl', () => {
        const formGroup = service.createJefaturasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJefaturas should disable id FormControl', () => {
        const formGroup = service.createJefaturasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
