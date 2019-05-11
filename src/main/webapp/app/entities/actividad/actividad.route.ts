import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Actividad } from 'app/shared/model/actividad.model';
import { ActividadService } from './actividad.service';
import { ActividadComponent } from './actividad.component';
import { ActividadDetailComponent } from './actividad-detail.component';
import { ActividadUpdateComponent } from './actividad-update.component';
import { ActividadDeletePopupComponent } from './actividad-delete-dialog.component';
import { IActividad } from 'app/shared/model/actividad.model';

@Injectable({ providedIn: 'root' })
export class ActividadResolve implements Resolve<IActividad> {
  constructor(private service: ActividadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IActividad> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Actividad>) => response.ok),
        map((actividad: HttpResponse<Actividad>) => actividad.body)
      );
    }
    return of(new Actividad());
  }
}

export const actividadRoute: Routes = [
  {
    path: '',
    component: ActividadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Actividads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActividadDetailComponent,
    resolve: {
      actividad: ActividadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Actividads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActividadUpdateComponent,
    resolve: {
      actividad: ActividadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Actividads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActividadUpdateComponent,
    resolve: {
      actividad: ActividadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Actividads'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const actividadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ActividadDeletePopupComponent,
    resolve: {
      actividad: ActividadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Actividads'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
