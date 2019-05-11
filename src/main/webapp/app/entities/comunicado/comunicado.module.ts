import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EfuSharedModule } from 'app/shared';
import {
  ComunicadoComponent,
  ComunicadoDetailComponent,
  ComunicadoUpdateComponent,
  ComunicadoDeletePopupComponent,
  ComunicadoDeleteDialogComponent,
  comunicadoRoute,
  comunicadoPopupRoute
} from './';

const ENTITY_STATES = [...comunicadoRoute, ...comunicadoPopupRoute];

@NgModule({
  imports: [EfuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ComunicadoComponent,
    ComunicadoDetailComponent,
    ComunicadoUpdateComponent,
    ComunicadoDeleteDialogComponent,
    ComunicadoDeletePopupComponent
  ],
  entryComponents: [ComunicadoComponent, ComunicadoUpdateComponent, ComunicadoDeleteDialogComponent, ComunicadoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuComunicadoModule {}
