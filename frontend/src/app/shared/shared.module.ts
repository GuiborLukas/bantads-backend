import { NgModule } from '@angular/core';
import { NumericoDirective } from './directives';


@NgModule({
  declarations: [NumericoDirective],
  exports: [NumericoDirective],
})
export class SharedModule {}
