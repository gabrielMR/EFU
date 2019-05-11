import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recorrido } from 'app/shared/model/recorrido.model';
import { RecorridoService } from './recorrido.service';
import { RecorridoComponent } from './recorrido.component';
import { RecorridoDetailComponent } from './recorrido-detail.component';
import { RecorridoUpdateComponent } from './recorrido-update.component';
import { RecorridoDeletePopupComponent } from './recorrido-delete-dialog.component';
import { IRecorrido } from 'app/shared/model/recorrido.model';

@Injectable({ providedIn: 'root' })
export class RecorridoResolve implements Resolve<IRecorrido> {
  constructor(private service: RecorridoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecorrido> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Recorrido>) => response.ok),
        map((recorrido: HttpResponse<Recorrido>) => recorrido.body)
      );
    }
    return of(new Recorrido());
  }
}

export const recorridoRoute: Routes = [
  {
    path: '',
    component: RecorridoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Recorridos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecorridoDetailComponent,
    resolve: {
      recorrido: RecorridoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Recorridos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecorridoUpdateComponent,
    resolve: {
      recorrido: RecorridoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Recorridos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecorridoUpdateComponent,
    resolve: {
      recorrido: RecorridoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Recorridos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const recorridoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RecorridoDeletePopupComponent,
    resolve: {
      recorrido: RecorridoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Recorridos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
