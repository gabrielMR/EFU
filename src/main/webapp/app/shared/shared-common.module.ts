import { NgModule } from '@angular/core';

import { EfuSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [EfuSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [EfuSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EfuSharedCommonModule {}
