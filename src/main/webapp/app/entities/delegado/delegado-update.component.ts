import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDelegado, Delegado } from 'app/shared/model/delegado.model';
import { DelegadoService } from './delegado.service';

@Component({
  selector: 'jhi-delegado-update',
  templateUrl: './delegado-update.component.html'
})
export class DelegadoUpdateComponent implements OnInit {
  delegado: IDelegado;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    idDelegado: [],
    ci: [],
    nombreDelegado: [],
    ru: [],
    item: [],
    telefono: [],
    correo: []
  });

  constructor(protected delegadoService: DelegadoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ delegado }) => {
      this.updateForm(delegado);
      this.delegado = delegado;
    });
  }

  updateForm(delegado: IDelegado) {
    this.editForm.patchValue({
      id: delegado.id,
      idDelegado: delegado.idDelegado,
      ci: delegado.ci,
      nombreDelegado: delegado.nombreDelegado,
      ru: delegado.ru,
      item: delegado.item,
      telefono: delegado.telefono,
      correo: delegado.correo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const delegado = this.createFromForm();
    if (delegado.id !== undefined) {
      this.subscribeToSaveResponse(this.delegadoService.update(delegado));
    } else {
      this.subscribeToSaveResponse(this.delegadoService.create(delegado));
    }
  }

  private createFromForm(): IDelegado {
    const entity = {
      ...new Delegado(),
      id: this.editForm.get(['id']).value,
      idDelegado: this.editForm.get(['idDelegado']).value,
      ci: this.editForm.get(['ci']).value,
      nombreDelegado: this.editForm.get(['nombreDelegado']).value,
      ru: this.editForm.get(['ru']).value,
      item: this.editForm.get(['item']).value,
      telefono: this.editForm.get(['telefono']).value,
      correo: this.editForm.get(['correo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelegado>>) {
    result.subscribe((res: HttpResponse<IDelegado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
