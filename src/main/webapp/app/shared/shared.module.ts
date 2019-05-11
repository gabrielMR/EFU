import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EfuSharedLibsModule, EfuSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [EfuSharedLibsModule, EfuSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [EfuSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EfuSharedModule {
  static forRoot() {
    return {
      ngModule: EfuSharedModule
    };
  }
}
