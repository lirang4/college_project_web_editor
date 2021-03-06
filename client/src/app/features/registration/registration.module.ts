import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '@shared/shared.module';
import { RegistrationComponent } from './registration.component';
import { RegistrationRoutingModule } from './registration-routing.module';

@NgModule({
  declarations: [
    RegistrationComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RegistrationRoutingModule,
  ]
})
export class RegistrationModule { }
