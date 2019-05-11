import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  RecorridoComponent,
  RecorridoDetailComponent,
  RecorridoUpdateComponent,
  RecorridoDeletePopupComponent,
  RecorridoDeleteDialogComponent,
  recorridoRoute,
  recorridoPopupRoute
} from './';

const ENTITY_STATES = [...recorridoRoute, ...recorridoPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RecorridoComponent,
    RecorridoDetailComponent,
    RecorridoUpdateComponent,
    RecorridoDeleteDialogComponent,
    RecorridoDeletePopupComponent
  ],
  entryComponents: [RecorridoComponent, RecorridoUpdateComponent, RecorridoDeleteDialogComponent, RecorridoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuRecorridoModule {}
