import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  ActividadComponent,
  ActividadDetailComponent,
  ActividadUpdateComponent,
  ActividadDeletePopupComponent,
  ActividadDeleteDialogComponent,
  actividadRoute,
  actividadPopupRoute
} from './';

const ENTITY_STATES = [...actividadRoute, ...actividadPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ActividadComponent,
    ActividadDetailComponent,
    ActividadUpdateComponent,
    ActividadDeleteDialogComponent,
    ActividadDeletePopupComponent
  ],
  entryComponents: [ActividadComponent, ActividadUpdateComponent, ActividadDeleteDialogComponent, ActividadDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuActividadModule {}
