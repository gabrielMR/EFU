import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IFraternidad, Fraternidad } from 'app/shared/model/fraternidad.model';
import { FraternidadService } from './fraternidad.service';
import { INiusta } from 'app/shared/model/niusta.model';
import { NiustaService } from 'app/entities/niusta';
import { IDelegado } from 'app/shared/model/delegado.model';
import { DelegadoService } from 'app/entities/delegado';
import { IFraterno } from 'app/shared/model/fraterno.model';
import { FraternoService } from 'app/entities/fraterno';
import { IPremio } from 'app/shared/model/premio.model';
import { PremioService } from 'app/entities/premio';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { NotificacionService } from 'app/entities/notificacion';

@Component({
  selector: 'jhi-fraternidad-update',
  templateUrl: './fraternidad-update.component.html'
})
export class FraternidadUpdateComponent implements OnInit {
  fraternidad: IFraternidad;
  isSaving: boolean;

  nombreniustas: INiusta[];

  nombredelegados: IDelegado[];

  fraternos: IFraterno[];

  premios: IPremio[];

  notificacions: INotificacion[];
  fundacionDp: any;

  editForm = this.fb.group({
    id: [],
    idFraternidad: [],
    nombre: [],
    danza: [],
    instancia: [],
    fundacion: [],
    descripcion: [],
    estado: [],
    nombreNiusta: [],
    nombreDelegado: [],
    nombres: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fraternidadService: FraternidadService,
    protected niustaService: NiustaService,
    protected delegadoService: DelegadoService,
    protected fraternoService: FraternoService,
    protected premioService: PremioService,
    protected notificacionService: NotificacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fraternidad }) => {
      this.updateForm(fraternidad);
      this.fraternidad = fraternidad;
    });
    this.niustaService
      .query({ filter: 'fraternidad-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<INiusta[]>) => mayBeOk.ok),
        map((response: HttpResponse<INiusta[]>) => response.body)
      )
      .subscribe(
        (res: INiusta[]) => {
          if (!this.fraternidad.nombreNiusta || !this.fraternidad.nombreNiusta.id) {
            this.nombreniustas = res;
          } else {
            this.niustaService
              .find(this.fraternidad.nombreNiusta.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<INiusta>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<INiusta>) => subResponse.body)
              )
              .subscribe(
                (subRes: INiusta) => (this.nombreniustas = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.delegadoService
      .query({ filter: 'fraternidad-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IDelegado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDelegado[]>) => response.body)
      )
      .subscribe(
        (res: IDelegado[]) => {
          if (!this.fraternidad.nombreDelegado || !this.fraternidad.nombreDelegado.id) {
            this.nombredelegados = res;
          } else {
            this.delegadoService
              .find(this.fraternidad.nombreDelegado.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IDelegado>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IDelegado>) => subResponse.body)
              )
              .subscribe(
                (subRes: IDelegado) => (this.nombredelegados = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.fraternoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFraterno[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFraterno[]>) => response.body)
      )
      .subscribe((res: IFraterno[]) => (this.fraternos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.premioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPremio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPremio[]>) => response.body)
      )
      .subscribe((res: IPremio[]) => (this.premios = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.notificacionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INotificacion[]>) => mayBeOk.ok),
        map((response: HttpResponse<INotificacion[]>) => response.body)
      )
      .subscribe((res: INotificacion[]) => (this.notificacions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fraternidad: IFraternidad) {
    this.editForm.patchValue({
      id: fraternidad.id,
      idFraternidad: fraternidad.idFraternidad,
      nombre: fraternidad.nombre,
      danza: fraternidad.danza,
      instancia: fraternidad.instancia,
      fundacion: fraternidad.fundacion,
      descripcion: fraternidad.descripcion,
      estado: fraternidad.estado,
      nombreNiusta: fraternidad.nombreNiusta,
      nombreDelegado: fraternidad.nombreDelegado,
      nombres: fraternidad.nombres
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fraternidad = this.createFromForm();
    if (fraternidad.id !== undefined) {
      this.subscribeToSaveResponse(this.fraternidadService.update(fraternidad));
    } else {
      this.subscribeToSaveResponse(this.fraternidadService.create(fraternidad));
    }
  }

  private createFromForm(): IFraternidad {
    const entity = {
      ...new Fraternidad(),
      id: this.editForm.get(['id']).value,
      idFraternidad: this.editForm.get(['idFraternidad']).value,
      nombre: this.editForm.get(['nombre']).value,
      danza: this.editForm.get(['danza']).value,
      instancia: this.editForm.get(['instancia']).value,
      fundacion: this.editForm.get(['fundacion']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      estado: this.editForm.get(['estado']).value,
      nombreNiusta: this.editForm.get(['nombreNiusta']).value,
      nombreDelegado: this.editForm.get(['nombreDelegado']).value,
      nombres: this.editForm.get(['nombres']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFraternidad>>) {
    result.subscribe((res: HttpResponse<IFraternidad>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackNiustaById(index: number, item: INiusta) {
    return item.id;
  }

  trackDelegadoById(index: number, item: IDelegado) {
    return item.id;
  }

  trackFraternoById(index: number, item: IFraterno) {
    return item.id;
  }

  trackPremioById(index: number, item: IPremio) {
    return item.id;
  }

  trackNotificacionById(index: number, item: INotificacion) {
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
