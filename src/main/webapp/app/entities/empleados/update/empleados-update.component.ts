import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IJefaturas } from 'app/entities/jefaturas/jefaturas.model';
import { JefaturasService } from 'app/entities/jefaturas/service/jefaturas.service';
import { EmpleadosService } from '../service/empleados.service';
import { IEmpleados } from '../empleados.model';
import { EmpleadosFormGroup, EmpleadosFormService } from './empleados-form.service';

@Component({
  standalone: true,
  selector: 'jhi-empleados-update',
  templateUrl: './empleados-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmpleadosUpdateComponent implements OnInit {
  isSaving = false;
  empleados: IEmpleados | null = null;

  departamentosSharedCollection: IDepartamento[] = [];
  jefaturasSharedCollection: IJefaturas[] = [];

  protected empleadosService = inject(EmpleadosService);
  protected empleadosFormService = inject(EmpleadosFormService);
  protected departamentoService = inject(DepartamentoService);
  protected jefaturasService = inject(JefaturasService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpleadosFormGroup = this.empleadosFormService.createEmpleadosFormGroup();

  compareDepartamento = (o1: IDepartamento | null, o2: IDepartamento | null): boolean =>
    this.departamentoService.compareDepartamento(o1, o2);

  compareJefaturas = (o1: IJefaturas | null, o2: IJefaturas | null): boolean => this.jefaturasService.compareJefaturas(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empleados }) => {
      this.empleados = empleados;
      if (empleados) {
        this.updateForm(empleados);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empleados = this.empleadosFormService.getEmpleados(this.editForm);
    if (empleados.id !== null) {
      this.subscribeToSaveResponse(this.empleadosService.update(empleados));
    } else {
      this.subscribeToSaveResponse(this.empleadosService.create(empleados));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleados>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(empleados: IEmpleados): void {
    this.empleados = empleados;
    this.empleadosFormService.resetForm(this.editForm, empleados);

    this.departamentosSharedCollection = this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
      this.departamentosSharedCollection,
      empleados.departamento,
    );
    this.jefaturasSharedCollection = this.jefaturasService.addJefaturasToCollectionIfMissing<IJefaturas>(
      this.jefaturasSharedCollection,
      empleados.jefatura,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.departamentoService
      .query()
      .pipe(map((res: HttpResponse<IDepartamento[]>) => res.body ?? []))
      .pipe(
        map((departamentos: IDepartamento[]) =>
          this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(departamentos, this.empleados?.departamento),
        ),
      )
      .subscribe((departamentos: IDepartamento[]) => (this.departamentosSharedCollection = departamentos));

    this.jefaturasService
      .query()
      .pipe(map((res: HttpResponse<IJefaturas[]>) => res.body ?? []))
      .pipe(
        map((jefaturas: IJefaturas[]) =>
          this.jefaturasService.addJefaturasToCollectionIfMissing<IJefaturas>(jefaturas, this.empleados?.jefatura),
        ),
      )
      .subscribe((jefaturas: IJefaturas[]) => (this.jefaturasSharedCollection = jefaturas));
  }
}
