import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fraternidad } from 'app/shared/model/fraternidad.model';
import { FraternidadService } from './fraternidad.service';
import { FraternidadComponent } from './fraternidad.component';
import { FraternidadDetailComponent } from './fraternidad-detail.component';
import { FraternidadUpdateComponent } from './fraternidad-update.component';
import { FraternidadDeletePopupComponent } from './fraternidad-delete-dialog.component';
import { IFraternidad } from 'app/shared/model/fraternidad.model';

@Injectable({ providedIn: 'root' })
export class FraternidadResolve implements Resolve<IFraternidad> {
  constructor(private service: FraternidadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFraternidad> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Fraternidad>) => response.ok),
        map((fraternidad: HttpResponse<Fraternidad>) => fraternidad.body)
      );
    }
    return of(new Fraternidad());
  }
}

export const fraternidadRoute: Routes = [
  {
    path: '',
    component: FraternidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FraternidadDetailComponent,
    resolve: {
      fraternidad: FraternidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FraternidadUpdateComponent,
    resolve: {
      fraternidad: FraternidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FraternidadUpdateComponent,
    resolve: {
      fraternidad: FraternidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternidads'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fraternidadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FraternidadDeletePopupComponent,
    resolve: {
      fraternidad: FraternidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Fraternidads'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
