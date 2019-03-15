/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SandboxForMuleTestModule } from '../../../test.module';
import { ContactSFComponent } from 'app/entities/contact-sf/contact-sf.component';
import { ContactSFService } from 'app/entities/contact-sf/contact-sf.service';
import { ContactSF } from 'app/shared/model/contact-sf.model';

describe('Component Tests', () => {
    describe('ContactSF Management Component', () => {
        let comp: ContactSFComponent;
        let fixture: ComponentFixture<ContactSFComponent>;
        let service: ContactSFService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [ContactSFComponent],
                providers: []
            })
                .overrideTemplate(ContactSFComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactSFComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSFService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ContactSF(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contactSFS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
