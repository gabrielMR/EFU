import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  FraternoComponent,
  FraternoDetailComponent,
  FraternoUpdateComponent,
  FraternoDeletePopupComponent,
  FraternoDeleteDialogComponent,
  fraternoRoute,
  fraternoPopupRoute
} from './';

const ENTITY_STATES = [...fraternoRoute, ...fraternoPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FraternoComponent,
    FraternoDetailComponent,
    FraternoUpdateComponent,
    FraternoDeleteDialogComponent,
    FraternoDeletePopupComponent
  ],
  entryComponents: [FraternoComponent, FraternoUpdateComponent, FraternoDeleteDialogComponent, FraternoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuFraternoModule {}
