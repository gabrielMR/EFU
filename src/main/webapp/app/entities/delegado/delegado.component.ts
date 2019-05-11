import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDelegado } from 'app/shared/model/delegado.model';
import { AccountService } from 'app/core';
import { DelegadoService } from './delegado.service';

@Component({
  selector: 'jhi-delegado',
  templateUrl: './delegado.component.html'
})
export class DelegadoComponent implements OnInit, OnDestroy {
  delegados: IDelegado[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected delegadoService: DelegadoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.delegadoService
      .query()
      .pipe(
        filter((res: HttpResponse<IDelegado[]>) => res.ok),
        map((res: HttpResponse<IDelegado[]>) => res.body)
      )
      .subscribe(
        (res: IDelegado[]) => {
          this.delegados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDelegados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDelegado) {
    return item.id;
  }

  registerChangeInDelegados() {
    this.eventSubscriber = this.eventManager.subscribe('delegadoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
