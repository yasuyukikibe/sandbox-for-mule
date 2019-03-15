/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SandboxForMuleTestModule } from '../../../test.module';
import { ContactSFUpdateComponent } from 'app/entities/contact-sf/contact-sf-update.component';
import { ContactSFService } from 'app/entities/contact-sf/contact-sf.service';
import { ContactSF } from 'app/shared/model/contact-sf.model';

describe('Component Tests', () => {
    describe('ContactSF Management Update Component', () => {
        let comp: ContactSFUpdateComponent;
        let fixture: ComponentFixture<ContactSFUpdateComponent>;
        let service: ContactSFService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [ContactSFUpdateComponent]
            })
                .overrideTemplate(ContactSFUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactSFUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSFService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContactSF(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contactSF = entity;
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
                    const entity = new ContactSF();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contactSF = entity;
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
