import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INiusta } from 'app/shared/model/niusta.model';

@Component({
  selector: 'jhi-niusta-detail',
  templateUrl: './niusta-detail.component.html'
})
export class NiustaDetailComponent implements OnInit {
  niusta: INiusta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ niusta }) => {
      this.niusta = niusta;
    });
  }

  previousState() {
    window.history.back();
  }
}
