import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { INotificacion, Notificacion } from 'app/shared/model/notificacion.model';
import { NotificacionService } from './notificacion.service';
import { IFraternidad } from 'app/shared/model/fraternidad.model';
import { FraternidadService } from 'app/entities/fraternidad';

@Component({
  selector: 'jhi-notificacion-update',
  templateUrl: './notificacion-update.component.html'
})
export class NotificacionUpdateComponent implements OnInit {
  notificacion: INotificacion;
  isSaving: boolean;

  fraternidads: IFraternidad[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    tituloNotificacion: [],
    descripcion: [],
    contenido: [],
    estado: [],
    fecha: [],
    hora: [],
    idFraternidad: [],
    nombres: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected notificacionService: NotificacionService,
    protected fraternidadService: FraternidadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ notificacion }) => {
      this.updateForm(notificacion);
      this.notificacion = notificacion;
    });
    this.fraternidadService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFraternidad[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFraternidad[]>) => response.body)
      )
      .subscribe((res: IFraternidad[]) => (this.fraternidads = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(notificacion: INotificacion) {
    this.editForm.patchValue({
      id: notificacion.id,
      tituloNotificacion: notificacion.tituloNotificacion,
      descripcion: notificacion.descripcion,
      contenido: notificacion.contenido,
      estado: notificacion.estado,
      fecha: notificacion.fecha,
      hora: notificacion.hora != null ? notificacion.hora.format(DATE_TIME_FORMAT) : null,
      idFraternidad: notificacion.idFraternidad,
      nombres: notificacion.nombres
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const notificacion = this.createFromForm();
    if (notificacion.id !== undefined) {
      this.subscribeToSaveResponse(this.notificacionService.update(notificacion));
    } else {
      this.subscribeToSaveResponse(this.notificacionService.create(notificacion));
    }
  }

  private createFromForm(): INotificacion {
    const entity = {
      ...new Notificacion(),
      id: this.editForm.get(['id']).value,
      tituloNotificacion: this.editForm.get(['tituloNotificacion']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      contenido: this.editForm.get(['contenido']).value,
      estado: this.editForm.get(['estado']).value,
      fecha: this.editForm.get(['fecha']).value,
      hora: this.editForm.get(['hora']).value != null ? moment(this.editForm.get(['hora']).value, DATE_TIME_FORMAT) : undefined,
      idFraternidad: this.editForm.get(['idFraternidad']).value,
      nombres: this.editForm.get(['nombres']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificacion>>) {
    result.subscribe((res: HttpResponse<INotificacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackFraternidadById(index: number, item: IFraternidad) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
