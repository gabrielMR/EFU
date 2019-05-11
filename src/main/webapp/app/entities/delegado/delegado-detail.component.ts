import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDelegado } from 'app/shared/model/delegado.model';

@Component({
  selector: 'jhi-delegado-detail',
  templateUrl: './delegado-detail.component.html'
})
export class DelegadoDetailComponent implements OnInit {
  delegado: IDelegado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ delegado }) => {
      this.delegado = delegado;
    });
  }

  previousState() {
    window.history.back();
  }
}
