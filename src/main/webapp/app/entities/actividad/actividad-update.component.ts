import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IActividad, Actividad } from 'app/shared/model/actividad.model';
import { ActividadService } from './actividad.service';

@Component({
  selector: 'jhi-actividad-update',
  templateUrl: './actividad-update.component.html'
})
export class ActividadUpdateComponent implements OnInit {
  actividad: IActividad;
  isSaving: boolean;
  fechainiDp: any;
  fechafinDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descripcion: [],
    fechaini: [],
    fechafin: [],
    horaini: [],
    horafin: [],
    contenido: [],
    estado: [],
    gestion: []
  });

  constructor(protected actividadService: ActividadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ actividad }) => {
      this.updateForm(actividad);
      this.actividad = actividad;
    });
  }

  updateForm(actividad: IActividad) {
    this.editForm.patchValue({
      id: actividad.id,
      titulo: actividad.titulo,
      descripcion: actividad.descripcion,
      fechaini: actividad.fechaini,
      fechafin: actividad.fechafin,
      horaini: actividad.horaini != null ? actividad.horaini.format(DATE_TIME_FORMAT) : null,
      horafin: actividad.horafin != null ? actividad.horafin.format(DATE_TIME_FORMAT) : null,
      contenido: actividad.contenido,
      estado: actividad.estado,
      gestion: actividad.gestion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const actividad = this.createFromForm();
    if (actividad.id !== undefined) {
      this.subscribeToSaveResponse(this.actividadService.update(actividad));
    } else {
      this.subscribeToSaveResponse(this.actividadService.create(actividad));
    }
  }

  private createFromForm(): IActividad {
    const entity = {
      ...new Actividad(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fechaini: this.editForm.get(['fechaini']).value,
      fechafin: this.editForm.get(['fechafin']).value,
      horaini: this.editForm.get(['horaini']).value != null ? moment(this.editForm.get(['horaini']).value, DATE_TIME_FORMAT) : undefined,
      horafin: this.editForm.get(['horafin']).value != null ? moment(this.editForm.get(['horafin']).value, DATE_TIME_FORMAT) : undefined,
      contenido: this.editForm.get(['contenido']).value,
      estado: this.editForm.get(['estado']).value,
      gestion: this.editForm.get(['gestion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActividad>>) {
    result.subscribe((res: HttpResponse<IActividad>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
