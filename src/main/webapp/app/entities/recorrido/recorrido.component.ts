import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecorrido } from 'app/shared/model/recorrido.model';
import { AccountService } from 'app/core';
import { RecorridoService } from './recorrido.service';

@Component({
  selector: 'jhi-recorrido',
  templateUrl: './recorrido.component.html'
})
export class RecorridoComponent implements OnInit, OnDestroy {
  recorridos: IRecorrido[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected recorridoService: RecorridoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.recorridoService
      .query()
      .pipe(
        filter((res: HttpResponse<IRecorrido[]>) => res.ok),
        map((res: HttpResponse<IRecorrido[]>) => res.body)
      )
      .subscribe(
        (res: IRecorrido[]) => {
          this.recorridos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRecorridos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRecorrido) {
    return item.id;
  }

  registerChangeInRecorridos() {
    this.eventSubscriber = this.eventManager.subscribe('recorridoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
