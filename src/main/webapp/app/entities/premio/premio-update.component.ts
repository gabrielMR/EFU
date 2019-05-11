import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPremio, Premio } from 'app/shared/model/premio.model';
import { PremioService } from './premio.service';
import { IFraternidad } from 'app/shared/model/fraternidad.model';
import { FraternidadService } from 'app/entities/fraternidad';

@Component({
  selector: 'jhi-premio-update',
  templateUrl: './premio-update.component.html'
})
export class PremioUpdateComponent implements OnInit {
  premio: IPremio;
  isSaving: boolean;

  fraternidads: IFraternidad[];

  editForm = this.fb.group({
    id: [],
    tituloPremio: [],
    descripcion: [],
    categoria: [],
    puesto: [],
    gestion: [],
    idFraternidad: [],
    nombres: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected premioService: PremioService,
    protected fraternidadService: FraternidadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ premio }) => {
      this.updateForm(premio);
      this.premio = premio;
    });
    this.fraternidadService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFraternidad[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFraternidad[]>) => response.body)
      )
      .subscribe((res: IFraternidad[]) => (this.fraternidads = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(premio: IPremio) {
    this.editForm.patchValue({
      id: premio.id,
      tituloPremio: premio.tituloPremio,
      descripcion: premio.descripcion,
      categoria: premio.categoria,
      puesto: premio.puesto,
      gestion: premio.gestion,
      idFraternidad: premio.idFraternidad,
      nombres: premio.nombres
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const premio = this.createFromForm();
    if (premio.id !== undefined) {
      this.subscribeToSaveResponse(this.premioService.update(premio));
    } else {
      this.subscribeToSaveResponse(this.premioService.create(premio));
    }
  }

  private createFromForm(): IPremio {
    const entity = {
      ...new Premio(),
      id: this.editForm.get(['id']).value,
      tituloPremio: this.editForm.get(['tituloPremio']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      categoria: this.editForm.get(['categoria']).value,
      puesto: this.editForm.get(['puesto']).value,
      gestion: this.editForm.get(['gestion']).value,
      idFraternidad: this.editForm.get(['idFraternidad']).value,
      nombres: this.editForm.get(['nombres']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPremio>>) {
    result.subscribe((res: HttpResponse<IPremio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
