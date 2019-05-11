import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Delegado } from 'app/shared/model/delegado.model';
import { DelegadoService } from './delegado.service';
import { DelegadoComponent } from './delegado.component';
import { DelegadoDetailComponent } from './delegado-detail.component';
import { DelegadoUpdateComponent } from './delegado-update.component';
import { DelegadoDeletePopupComponent } from './delegado-delete-dialog.component';
import { IDelegado } from 'app/shared/model/delegado.model';

@Injectable({ providedIn: 'root' })
export class DelegadoResolve implements Resolve<IDelegado> {
  constructor(private service: DelegadoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDelegado> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Delegado>) => response.ok),
        map((delegado: HttpResponse<Delegado>) => delegado.body)
      );
    }
    return of(new Delegado());
  }
}

export const delegadoRoute: Routes = [
  {
    path: '',
    component: DelegadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Delegados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DelegadoDetailComponent,
    resolve: {
      delegado: DelegadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Delegados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DelegadoUpdateComponent,
    resolve: {
      delegado: DelegadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Delegados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DelegadoUpdateComponent,
    resolve: {
      delegado: DelegadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Delegados'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const delegadoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DelegadoDeletePopupComponent,
    resolve: {
      delegado: DelegadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Delegados'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
