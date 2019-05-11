import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Niusta } from 'app/shared/model/niusta.model';
import { NiustaService } from './niusta.service';
import { NiustaComponent } from './niusta.component';
import { NiustaDetailComponent } from './niusta-detail.component';
import { NiustaUpdateComponent } from './niusta-update.component';
import { NiustaDeletePopupComponent } from './niusta-delete-dialog.component';
import { INiusta } from 'app/shared/model/niusta.model';

@Injectable({ providedIn: 'root' })
export class NiustaResolve implements Resolve<INiusta> {
  constructor(private service: NiustaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INiusta> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Niusta>) => response.ok),
        map((niusta: HttpResponse<Niusta>) => niusta.body)
      );
    }
    return of(new Niusta());
  }
}

export const niustaRoute: Routes = [
  {
    path: '',
    component: NiustaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Niustas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NiustaDetailComponent,
    resolve: {
      niusta: NiustaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Niustas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NiustaUpdateComponent,
    resolve: {
      niusta: NiustaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Niustas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NiustaUpdateComponent,
    resolve: {
      niusta: NiustaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Niustas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const niustaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NiustaDeletePopupComponent,
    resolve: {
      niusta: NiustaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Niustas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
