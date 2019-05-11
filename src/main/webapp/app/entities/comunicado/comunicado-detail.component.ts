import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComunicado } from 'app/shared/model/comunicado.model';

@Component({
  selector: 'jhi-comunicado-detail',
  templateUrl: './comunicado-detail.component.html'
})
export class ComunicadoDetailComponent implements OnInit {
  comunicado: IComunicado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comunicado }) => {
      this.comunicado = comunicado;
    });
  }

  previousState() {
    window.history.back();
  }
}
