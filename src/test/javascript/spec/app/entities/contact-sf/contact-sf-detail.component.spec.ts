/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SandboxForMuleTestModule } from '../../../test.module';
import { ContactSFDetailComponent } from 'app/entities/contact-sf/contact-sf-detail.component';
import { ContactSF } from 'app/shared/model/contact-sf.model';

describe('Component Tests', () => {
    describe('ContactSF Management Detail Component', () => {
        let comp: ContactSFDetailComponent;
        let fixture: ComponentFixture<ContactSFDetailComponent>;
        const route = ({ data: of({ contactSF: new ContactSF(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [ContactSFDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactSFDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactSFDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactSF).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
