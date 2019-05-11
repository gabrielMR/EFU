import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecorrido } from 'app/shared/model/recorrido.model';

@Component({
  selector: 'jhi-recorrido-detail',
  templateUrl: './recorrido-detail.component.html'
})
export class RecorridoDetailComponent implements OnInit {
  recorrido: IRecorrido;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recorrido }) => {
      this.recorrido = recorrido;
    });
  }

  previousState() {
    window.history.back();
  }
}
