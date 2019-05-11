import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'recorrido',
        loadChildren: './recorrido/recorrido.module#EfuRecorridoModule'
      },
      {
        path: 'actividad',
        loadChildren: './actividad/actividad.module#EfuActividadModule'
      },
      {
        path: 'comunicado',
        loadChildren: './comunicado/comunicado.module#EfuComunicadoModule'
      },
      {
        path: 'premio',
        loadChildren: './premio/premio.module#EfuPremioModule'
      },
      {
        path: 'notificacion',
        loadChildren: './notificacion/notificacion.module#EfuNotificacionModule'
      },
      {
        path: 'fraternidad',
        loadChildren: './fraternidad/fraternidad.module#EfuFraternidadModule'
      },
      {
        path: 'delegado',
        loadChildren: './delegado/delegado.module#EfuDelegadoModule'
      },
      {
        path: 'fraterno',
        loadChildren: './fraterno/fraterno.module#EfuFraternoModule'
      },
      {
        path: 'niusta',
        loadChildren: './niusta/niusta.module#EfuNiustaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuEntityModule {}
