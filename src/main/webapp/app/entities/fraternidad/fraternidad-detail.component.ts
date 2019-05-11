import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFraternidad } from 'app/shared/model/fraternidad.model';

@Component({
  selector: 'jhi-fraternidad-detail',
  templateUrl: './fraternidad-detail.component.html'
})
export class FraternidadDetailComponent implements OnInit {
  fraternidad: IFraternidad;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fraternidad }) => {
      this.fraternidad = fraternidad;
    });
  }

  previousState() {
    window.history.back();
  }
}
