import { NgModule } from '@angular/core';

import { SandboxForMuleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SandboxForMuleSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SandboxForMuleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SandboxForMuleSharedCommonModule {}
