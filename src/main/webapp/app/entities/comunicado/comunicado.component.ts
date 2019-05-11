import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IComunicado } from 'app/shared/model/comunicado.model';
import { AccountService } from 'app/core';
import { ComunicadoService } from './comunicado.service';

@Component({
  selector: 'jhi-comunicado',
  templateUrl: './comunicado.component.html'
})
export class ComunicadoComponent implements OnInit, OnDestroy {
  comunicados: IComunicado[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected comunicadoService: ComunicadoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.comunicadoService
      .query()
      .pipe(
        filter((res: HttpResponse<IComunicado[]>) => res.ok),
        map((res: HttpResponse<IComunicado[]>) => res.body)
      )
      .subscribe(
        (res: IComunicado[]) => {
          this.comunicados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInComunicados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IComunicado) {
    return item.id;
  }

  registerChangeInComunicados() {
    this.eventSubscriber = this.eventManager.subscribe('comunicadoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
