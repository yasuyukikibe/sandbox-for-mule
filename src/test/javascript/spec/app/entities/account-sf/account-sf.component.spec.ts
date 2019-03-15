/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SandboxForMuleTestModule } from '../../../test.module';
import { AccountSFComponent } from 'app/entities/account-sf/account-sf.component';
import { AccountSFService } from 'app/entities/account-sf/account-sf.service';
import { AccountSF } from 'app/shared/model/account-sf.model';

describe('Component Tests', () => {
    describe('AccountSF Management Component', () => {
        let comp: AccountSFComponent;
        let fixture: ComponentFixture<AccountSFComponent>;
        let service: AccountSFService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [AccountSFComponent],
                providers: []
            })
                .overrideTemplate(AccountSFComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountSFComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountSFService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AccountSF(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.accountSFS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
