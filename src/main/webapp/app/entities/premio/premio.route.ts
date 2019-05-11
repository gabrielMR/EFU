import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Premio } from 'app/shared/model/premio.model';
import { PremioService } from './premio.service';
import { PremioComponent } from './premio.component';
import { PremioDetailComponent } from './premio-detail.component';
import { PremioUpdateComponent } from './premio-update.component';
import { PremioDeletePopupComponent } from './premio-delete-dialog.component';
import { IPremio } from 'app/shared/model/premio.model';

@Injectable({ providedIn: 'root' })
export class PremioResolve implements Resolve<IPremio> {
  constructor(private service: PremioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPremio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Premio>) => response.ok),
        map((premio: HttpResponse<Premio>) => premio.body)
      );
    }
    return of(new Premio());
  }
}

export const premioRoute: Routes = [
  {
    path: '',
    component: PremioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Premios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PremioDetailComponent,
    resolve: {
      premio: PremioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Premios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PremioUpdateComponent,
    resolve: {
      premio: PremioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Premios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PremioUpdateComponent,
    resolve: {
      premio: PremioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Premios'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const premioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PremioDeletePopupComponent,
    resolve: {
      premio: PremioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Premios'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
