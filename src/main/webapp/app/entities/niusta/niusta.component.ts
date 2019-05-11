import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INiusta } from 'app/shared/model/niusta.model';
import { AccountService } from 'app/core';
import { NiustaService } from './niusta.service';

@Component({
  selector: 'jhi-niusta',
  templateUrl: './niusta.component.html'
})
export class NiustaComponent implements OnInit, OnDestroy {
  niustas: INiusta[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected niustaService: NiustaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.niustaService
      .query()
      .pipe(
        filter((res: HttpResponse<INiusta[]>) => res.ok),
        map((res: HttpResponse<INiusta[]>) => res.body)
      )
      .subscribe(
        (res: INiusta[]) => {
          this.niustas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNiustas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INiusta) {
    return item.id;
  }

  registerChangeInNiustas() {
    this.eventSubscriber = this.eventManager.subscribe('niustaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
