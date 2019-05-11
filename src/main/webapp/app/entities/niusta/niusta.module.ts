import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  NiustaComponent,
  NiustaDetailComponent,
  NiustaUpdateComponent,
  NiustaDeletePopupComponent,
  NiustaDeleteDialogComponent,
  niustaRoute,
  niustaPopupRoute
} from './';

const ENTITY_STATES = [...niustaRoute, ...niustaPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NiustaComponent, NiustaDetailComponent, NiustaUpdateComponent, NiustaDeleteDialogComponent, NiustaDeletePopupComponent],
  entryComponents: [NiustaComponent, NiustaUpdateComponent, NiustaDeleteDialogComponent, NiustaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuNiustaModule {}
