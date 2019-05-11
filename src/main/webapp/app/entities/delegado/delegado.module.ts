import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  DelegadoComponent,
  DelegadoDetailComponent,
  DelegadoUpdateComponent,
  DelegadoDeletePopupComponent,
  DelegadoDeleteDialogComponent,
  delegadoRoute,
  delegadoPopupRoute
} from './';

const ENTITY_STATES = [...delegadoRoute, ...delegadoPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DelegadoComponent,
    DelegadoDetailComponent,
    DelegadoUpdateComponent,
    DelegadoDeleteDialogComponent,
    DelegadoDeletePopupComponent
  ],
  entryComponents: [DelegadoComponent, DelegadoUpdateComponent, DelegadoDeleteDialogComponent, DelegadoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuDelegadoModule {}
