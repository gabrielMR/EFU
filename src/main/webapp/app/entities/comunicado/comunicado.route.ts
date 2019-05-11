import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Comunicado } from 'app/shared/model/comunicado.model';
import { ComunicadoService } from './comunicado.service';
import { ComunicadoComponent } from './comunicado.component';
import { ComunicadoDetailComponent } from './comunicado-detail.component';
import { ComunicadoUpdateComponent } from './comunicado-update.component';
import { ComunicadoDeletePopupComponent } from './comunicado-delete-dialog.component';
import { IComunicado } from 'app/shared/model/comunicado.model';

@Injectable({ providedIn: 'root' })
export class ComunicadoResolve implements Resolve<IComunicado> {
  constructor(private service: ComunicadoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComunicado> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Comunicado>) => response.ok),
        map((comunicado: HttpResponse<Comunicado>) => comunicado.body)
      );
    }
    return of(new Comunicado());
  }
}

export const comunicadoRoute: Routes = [
  {
    path: '',
    component: ComunicadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComunicadoDetailComponent,
    resolve: {
      comunicado: ComunicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComunicadoUpdateComponent,
    resolve: {
      comunicado: ComunicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComunicadoUpdateComponent,
    resolve: {
      comunicado: ComunicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunicados'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const comunicadoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComunicadoDeletePopupComponent,
    resolve: {
      comunicado: ComunicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunicados'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
