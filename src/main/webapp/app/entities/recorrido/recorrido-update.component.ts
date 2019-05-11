import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRecorrido, Recorrido } from 'app/shared/model/recorrido.model';
import { RecorridoService } from './recorrido.service';

@Component({
  selector: 'jhi-recorrido-update',
  templateUrl: './recorrido-update.component.html'
})
export class RecorridoUpdateComponent implements OnInit {
  recorrido: IRecorrido;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    inicio: [],
    fin: [],
    descripcion: [],
    gestion: []
  });

  constructor(protected recorridoService: RecorridoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ recorrido }) => {
      this.updateForm(recorrido);
      this.recorrido = recorrido;
    });
  }

  updateForm(recorrido: IRecorrido) {
    this.editForm.patchValue({
      id: recorrido.id,
      nombre: recorrido.nombre,
      inicio: recorrido.inicio,
      fin: recorrido.fin,
      descripcion: recorrido.descripcion,
      gestion: recorrido.gestion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const recorrido = this.createFromForm();
    if (recorrido.id !== undefined) {
      this.subscribeToSaveResponse(this.recorridoService.update(recorrido));
    } else {
      this.subscribeToSaveResponse(this.recorridoService.create(recorrido));
    }
  }

  private createFromForm(): IRecorrido {
    const entity = {
      ...new Recorrido(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      inicio: this.editForm.get(['inicio']).value,
      fin: this.editForm.get(['fin']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      gestion: this.editForm.get(['gestion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecorrido>>) {
    result.subscribe((res: HttpResponse<IRecorrido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
