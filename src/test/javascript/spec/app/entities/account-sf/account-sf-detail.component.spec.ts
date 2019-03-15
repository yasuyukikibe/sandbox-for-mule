/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SandboxForMuleTestModule } from '../../../test.module';
import { AccountSFDetailComponent } from 'app/entities/account-sf/account-sf-detail.component';
import { AccountSF } from 'app/shared/model/account-sf.model';

describe('Component Tests', () => {
    describe('AccountSF Management Detail Component', () => {
        let comp: AccountSFDetailComponent;
        let fixture: ComponentFixture<AccountSFDetailComponent>;
        const route = ({ data: of({ accountSF: new AccountSF(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [AccountSFDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccountSFDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountSFDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accountSF).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
