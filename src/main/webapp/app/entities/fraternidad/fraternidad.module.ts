import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  FraternidadComponent,
  FraternidadDetailComponent,
  FraternidadUpdateComponent,
  FraternidadDeletePopupComponent,
  FraternidadDeleteDialogComponent,
  fraternidadRoute,
  fraternidadPopupRoute
} from './';

const ENTITY_STATES = [...fraternidadRoute, ...fraternidadPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FraternidadComponent,
    FraternidadDetailComponent,
    FraternidadUpdateComponent,
    FraternidadDeleteDialogComponent,
    FraternidadDeletePopupComponent
  ],
  entryComponents: [FraternidadComponent, FraternidadUpdateComponent, FraternidadDeleteDialogComponent, FraternidadDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuFraternidadModule {}
