import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFraterno, Fraterno } from 'app/shared/model/fraterno.model';
import { FraternoService } from './fraterno.service';
import { IFraternidad } from 'app/shared/model/fraternidad.model';
import { FraternidadService } from 'app/entities/fraternidad';

@Component({
  selector: 'jhi-fraterno-update',
  templateUrl: './fraterno-update.component.html'
})
export class FraternoUpdateComponent implements OnInit {
  fraterno: IFraterno;
  isSaving: boolean;

  fraternidads: IFraternidad[];

  editForm = this.fb.group({
    id: [],
    ci: [],
    nombreFraterno: [],
    ru: [],
    item: [],
    observaciones: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fraternoService: FraternoService,
    protected fraternidadService: FraternidadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fraterno }) => {
      this.updateForm(fraterno);
      this.fraterno = fraterno;
    });
    this.fraternidadService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFraternidad[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFraternidad[]>) => response.body)
      )
      .subscribe((res: IFraternidad[]) => (this.fraternidads = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fraterno: IFraterno) {
    this.editForm.patchValue({
      id: fraterno.id,
      ci: fraterno.ci,
      nombreFraterno: fraterno.nombreFraterno,
      ru: fraterno.ru,
      item: fraterno.item,
      observaciones: fraterno.observaciones
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fraterno = this.createFromForm();
    if (fraterno.id !== undefined) {
      this.subscribeToSaveResponse(this.fraternoService.update(fraterno));
    } else {
      this.subscribeToSaveResponse(this.fraternoService.create(fraterno));
    }
  }

  private createFromForm(): IFraterno {
    const entity = {
      ...new Fraterno(),
      id: this.editForm.get(['id']).value,
      ci: this.editForm.get(['ci']).value,
      nombreFraterno: this.editForm.get(['nombreFraterno']).value,
      ru: this.editForm.get(['ru']).value,
      item: this.editForm.get(['item']).value,
      observaciones: this.editForm.get(['observaciones']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFraterno>>) {
    result.subscribe((res: HttpResponse<IFraterno>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
