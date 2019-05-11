import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFraterno } from 'app/shared/model/fraterno.model';

@Component({
  selector: 'jhi-fraterno-detail',
  templateUrl: './fraterno-detail.component.html'
})
export class FraternoDetailComponent implements OnInit {
  fraterno: IFraterno;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fraterno }) => {
      this.fraterno = fraterno;
    });
  }

  previousState() {
    window.history.back();
  }
}
