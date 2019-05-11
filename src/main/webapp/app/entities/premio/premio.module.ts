import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  PremioComponent,
  PremioDetailComponent,
  PremioUpdateComponent,
  PremioDeletePopupComponent,
  PremioDeleteDialogComponent,
  premioRoute,
  premioPopupRoute
} from './';

const ENTITY_STATES = [...premioRoute, ...premioPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PremioComponent, PremioDetailComponent, PremioUpdateComponent, PremioDeleteDialogComponent, PremioDeletePopupComponent],
  entryComponents: [PremioComponent, PremioUpdateComponent, PremioDeleteDialogComponent, PremioDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuPremioModule {}
