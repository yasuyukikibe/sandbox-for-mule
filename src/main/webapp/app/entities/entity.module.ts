import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'account-sf',
                loadChildren: './account-sf/account-sf.module#SandboxForMuleAccountSFModule'
            },
            {
                path: 'contact-sf',
                loadChildren: './contact-sf/contact-sf.module#SandboxForMuleContactSFModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SandboxForMuleEntityModule {}
