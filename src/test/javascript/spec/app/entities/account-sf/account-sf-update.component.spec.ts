/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SandboxForMuleTestModule } from '../../../test.module';
import { AccountSFUpdateComponent } from 'app/entities/account-sf/account-sf-update.component';
import { AccountSFService } from 'app/entities/account-sf/account-sf.service';
import { AccountSF } from 'app/shared/model/account-sf.model';

describe('Component Tests', () => {
    describe('AccountSF Management Update Component', () => {
        let comp: AccountSFUpdateComponent;
        let fixture: ComponentFixture<AccountSFUpdateComponent>;
        let service: AccountSFService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [AccountSFUpdateComponent]
            })
                .overrideTemplate(AccountSFUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountSFUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountSFService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AccountSF(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountSF = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AccountSF();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountSF = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
