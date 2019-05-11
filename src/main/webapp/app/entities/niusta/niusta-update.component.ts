import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INiusta, Niusta } from 'app/shared/model/niusta.model';
import { NiustaService } from './niusta.service';

@Component({
  selector: 'jhi-niusta-update',
  templateUrl: './niusta-update.component.html'
})
export class NiustaUpdateComponent implements OnInit {
  niusta: INiusta;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ci: [],
    nombreNiusta: [],
    ru: [],
    item: [],
    edad: [],
    gustos: [],
    estatura: [],
    titulo: []
  });

  constructor(protected niustaService: NiustaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ niusta }) => {
      this.updateForm(niusta);
      this.niusta = niusta;
    });
  }

  updateForm(niusta: INiusta) {
    this.editForm.patchValue({
      id: niusta.id,
      ci: niusta.ci,
      nombreNiusta: niusta.nombreNiusta,
      ru: niusta.ru,
      item: niusta.item,
      edad: niusta.edad,
      gustos: niusta.gustos,
      estatura: niusta.estatura,
      titulo: niusta.titulo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const niusta = this.createFromForm();
    if (niusta.id !== undefined) {
      this.subscribeToSaveResponse(this.niustaService.update(niusta));
    } else {
      this.subscribeToSaveResponse(this.niustaService.create(niusta));
    }
  }

  private createFromForm(): INiusta {
    const entity = {
      ...new Niusta(),
      id: this.editForm.get(['id']).value,
      ci: this.editForm.get(['ci']).value,
      nombreNiusta: this.editForm.get(['nombreNiusta']).value,
      ru: this.editForm.get(['ru']).value,
      item: this.editForm.get(['item']).value,
      edad: this.editForm.get(['edad']).value,
      gustos: this.editForm.get(['gustos']).value,
      estatura: this.editForm.get(['estatura']).value,
      titulo: this.editForm.get(['titulo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INiusta>>) {
    result.subscribe((res: HttpResponse<INiusta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
