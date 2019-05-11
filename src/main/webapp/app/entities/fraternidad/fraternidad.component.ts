import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFraternidad } from 'app/shared/model/fraternidad.model';
import { AccountService } from 'app/core';
import { FraternidadService } from './fraternidad.service';

@Component({
  selector: 'jhi-fraternidad',
  templateUrl: './fraternidad.component.html'
})
export class FraternidadComponent implements OnInit, OnDestroy {
  fraternidads: IFraternidad[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fraternidadService: FraternidadService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fraternidadService
      .query()
      .pipe(
        filter((res: HttpResponse<IFraternidad[]>) => res.ok),
        map((res: HttpResponse<IFraternidad[]>) => res.body)
      )
      .subscribe(
        (res: IFraternidad[]) => {
          this.fraternidads = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFraternidads();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFraternidad) {
    return item.id;
  }

  registerChangeInFraternidads() {
    this.eventSubscriber = this.eventManager.subscribe('fraternidadListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
