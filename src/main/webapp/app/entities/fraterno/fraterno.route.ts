import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fraterno } from 'app/shared/model/fraterno.model';
import { FraternoService } from './fraterno.service';
import { FraternoComponent } from './fraterno.component';
import { FraternoDetailComponent } from './fraterno-detail.component';
import { FraternoUpdateComponent } from './fraterno-update.component';
import { FraternoDeletePopupComponent } from './fraterno-delete-dialog.component';
import { IFraterno } from 'app/shared/model/fraterno.model';

@Injectable({ providedIn: 'root' })
export class FraternoResolve implements Resolve<IFraterno> {
  constructor(private service: FraternoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFraterno> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Fraterno>) => response.ok),
        map((fraterno: HttpResponse<Fraterno>) => fraterno.body)
      );
    }
    return of(new Fraterno());
  }
}

export const fraternoRoute: Routes = [
  {
    path: '',
    component: FraternoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FraternoDetailComponent,
    resolve: {
      fraterno: FraternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FraternoUpdateComponent,
    resolve: {
      fraterno: FraternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FraternoUpdateComponent,
    resolve: {
      fraterno: FraternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fraternoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FraternoDeletePopupComponent,
    resolve: {
      fraterno: FraternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
