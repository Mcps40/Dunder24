import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IJefaturas } from '../jefaturas.model';
import { JefaturasService } from '../service/jefaturas.service';
import { JefaturasFormGroup, JefaturasFormService } from './jefaturas-form.service';

@Component({
  standalone: true,
  selector: 'jhi-jefaturas-update',
  templateUrl: './jefaturas-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class JefaturasUpdateComponent implements OnInit {
  isSaving = false;
  jefaturas: IJefaturas | null = null;

  departamentosSharedCollection: IDepartamento[] = [];

  protected jefaturasService = inject(JefaturasService);
  protected jefaturasFormService = inject(JefaturasFormService);
  protected departamentoService = inject(DepartamentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: JefaturasFormGroup = this.jefaturasFormService.createJefaturasFormGroup();

  compareDepartamento = (o1: IDepartamento | null, o2: IDepartamento | null): boolean =>
    this.departamentoService.compareDepartamento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jefaturas }) => {
      this.jefaturas = jefaturas;
      if (jefaturas) {
        this.updateForm(jefaturas);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jefaturas = this.jefaturasFormService.getJefaturas(this.editForm);
    if (jefaturas.id !== null) {
      this.subscribeToSaveResponse(this.jefaturasService.update(jefaturas));
    } else {
      this.subscribeToSaveResponse(this.jefaturasService.create(jefaturas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJefaturas>>): void {
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

  protected updateForm(jefaturas: IJefaturas): void {
    this.jefaturas = jefaturas;
    this.jefaturasFormService.resetForm(this.editForm, jefaturas);

    this.departamentosSharedCollection = this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(
      this.departamentosSharedCollection,
      jefaturas.departamento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.departamentoService
      .query()
      .pipe(map((res: HttpResponse<IDepartamento[]>) => res.body ?? []))
      .pipe(
        map((departamentos: IDepartamento[]) =>
          this.departamentoService.addDepartamentoToCollectionIfMissing<IDepartamento>(departamentos, this.jefaturas?.departamento),
        ),
      )
      .subscribe((departamentos: IDepartamento[]) => (this.departamentosSharedCollection = departamentos));
  }
}
