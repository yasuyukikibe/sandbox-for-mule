import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SandboxForMuleSharedModule } from 'app/shared';
import {
    AccountSFComponent,
    AccountSFDetailComponent,
    AccountSFUpdateComponent,
    AccountSFDeletePopupComponent,
    AccountSFDeleteDialogComponent,
    accountSFRoute,
    accountSFPopupRoute
} from './';

const ENTITY_STATES = [...accountSFRoute, ...accountSFPopupRoute];

@NgModule({
    imports: [SandboxForMuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountSFComponent,
        AccountSFDetailComponent,
        AccountSFUpdateComponent,
        AccountSFDeleteDialogComponent,
        AccountSFDeletePopupComponent
    ],
    entryComponents: [AccountSFComponent, AccountSFUpdateComponent, AccountSFDeleteDialogComponent, AccountSFDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SandboxForMuleAccountSFModule {}
