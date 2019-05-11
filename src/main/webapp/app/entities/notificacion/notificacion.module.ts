import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  NotificacionComponent,
  NotificacionDetailComponent,
  NotificacionUpdateComponent,
  NotificacionDeletePopupComponent,
  NotificacionDeleteDialogComponent,
  notificacionRoute,
  notificacionPopupRoute
} from './';

const ENTITY_STATES = [...notificacionRoute, ...notificacionPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NotificacionComponent,
    NotificacionDetailComponent,
    NotificacionUpdateComponent,
    NotificacionDeleteDialogComponent,
    NotificacionDeletePopupComponent
  ],
  entryComponents: [
    NotificacionComponent,
    NotificacionUpdateComponent,
    NotificacionDeleteDialogComponent,
    NotificacionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuNotificacionModule {}
