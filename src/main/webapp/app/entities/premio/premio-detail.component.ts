import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPremio } from 'app/shared/model/premio.model';

@Component({
  selector: 'jhi-premio-detail',
  templateUrl: './premio-detail.component.html'
})
export class PremioDetailComponent implements OnInit {
  premio: IPremio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ premio }) => {
      this.premio = premio;
    });
  }

  previousState() {
    window.history.back();
  }
}
