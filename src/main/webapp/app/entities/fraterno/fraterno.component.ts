import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFraterno } from 'app/shared/model/fraterno.model';
import { AccountService } from 'app/core';
import { FraternoService } from './fraterno.service';

@Component({
  selector: 'jhi-fraterno',
  templateUrl: './fraterno.component.html'
})
export class FraternoComponent implements OnInit, OnDestroy {
  fraternos: IFraterno[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fraternoService: FraternoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fraternoService
      .query()
      .pipe(
        filter((res: HttpResponse<IFraterno[]>) => res.ok),
        map((res: HttpResponse<IFraterno[]>) => res.body)
      )
      .subscribe(
        (res: IFraterno[]) => {
          this.fraternos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFraternos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFraterno) {
    return item.id;
  }

  registerChangeInFraternos() {
    this.eventSubscriber = this.eventManager.subscribe('fraternoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
