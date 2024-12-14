import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IJefaturas, NewJefaturas } from '../jefaturas.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJefaturas for edit and NewJefaturasFormGroupInput for create.
 */
type JefaturasFormGroupInput = IJefaturas | PartialWithRequiredKeyOf<NewJefaturas>;

type JefaturasFormDefaults = Pick<NewJefaturas, 'id'>;

type JefaturasFormGroupContent = {
  id: FormControl<IJefaturas['id'] | NewJefaturas['id']>;
  nombre_jefe: FormControl<IJefaturas['nombre_jefe']>;
  departamento: FormControl<IJefaturas['departamento']>;
};

export type JefaturasFormGroup = FormGroup<JefaturasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JefaturasFormService {
  createJefaturasFormGroup(jefaturas: JefaturasFormGroupInput = { id: null }): JefaturasFormGroup {
    const jefaturasRawValue = {
      ...this.getFormDefaults(),
      ...jefaturas,
    };
    return new FormGroup<JefaturasFormGroupContent>({
      id: new FormControl(
        { value: jefaturasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre_jefe: new FormControl(jefaturasRawValue.nombre_jefe),
      departamento: new FormControl(jefaturasRawValue.departamento),
    });
  }

  getJefaturas(form: JefaturasFormGroup): IJefaturas | NewJefaturas {
    return form.getRawValue() as IJefaturas | NewJefaturas;
  }

  resetForm(form: JefaturasFormGroup, jefaturas: JefaturasFormGroupInput): void {
    const jefaturasRawValue = { ...this.getFormDefaults(), ...jefaturas };
    form.reset(
      {
        ...jefaturasRawValue,
        id: { value: jefaturasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): JefaturasFormDefaults {
    return {
      id: null,
    };
  }
}
