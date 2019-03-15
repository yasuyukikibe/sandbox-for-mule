import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SandboxForMuleSharedModule } from 'app/shared';
import {
    ContactSFComponent,
    ContactSFDetailComponent,
    ContactSFUpdateComponent,
    ContactSFDeletePopupComponent,
    ContactSFDeleteDialogComponent,
    contactSFRoute,
    contactSFPopupRoute
} from './';

const ENTITY_STATES = [...contactSFRoute, ...contactSFPopupRoute];

@NgModule({
    imports: [SandboxForMuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactSFComponent,
        ContactSFDetailComponent,
        ContactSFUpdateComponent,
        ContactSFDeleteDialogComponent,
        ContactSFDeletePopupComponent
    ],
    entryComponents: [ContactSFComponent, ContactSFUpdateComponent, ContactSFDeleteDialogComponent, ContactSFDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SandboxForMuleContactSFModule {}
