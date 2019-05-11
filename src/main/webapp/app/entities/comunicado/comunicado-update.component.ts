import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IComunicado, Comunicado } from 'app/shared/model/comunicado.model';
import { ComunicadoService } from './comunicado.service';

@Component({
  selector: 'jhi-comunicado-update',
  templateUrl: './comunicado-update.component.html'
})
export class ComunicadoUpdateComponent implements OnInit {
  comunicado: IComunicado;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descripcion: [],
    contenido: [],
    gestion: []
  });

  constructor(protected comunicadoService: ComunicadoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comunicado }) => {
      this.updateForm(comunicado);
      this.comunicado = comunicado;
    });
  }

  updateForm(comunicado: IComunicado) {
    this.editForm.patchValue({
      id: comunicado.id,
      titulo: comunicado.titulo,
      descripcion: comunicado.descripcion,
      contenido: comunicado.contenido,
      gestion: comunicado.gestion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const comunicado = this.createFromForm();
    if (comunicado.id !== undefined) {
      this.subscribeToSaveResponse(this.comunicadoService.update(comunicado));
    } else {
      this.subscribeToSaveResponse(this.comunicadoService.create(comunicado));
    }
  }

  private createFromForm(): IComunicado {
    const entity = {
      ...new Comunicado(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      contenido: this.editForm.get(['contenido']).value,
      gestion: this.editForm.get(['gestion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComunicado>>) {
    result.subscribe((res: HttpResponse<IComunicado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
