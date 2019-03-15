/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SandboxForMuleTestModule } from '../../../test.module';
import { ContactSFDeleteDialogComponent } from 'app/entities/contact-sf/contact-sf-delete-dialog.component';
import { ContactSFService } from 'app/entities/contact-sf/contact-sf.service';

describe('Component Tests', () => {
    describe('ContactSF Management Delete Component', () => {
        let comp: ContactSFDeleteDialogComponent;
        let fixture: ComponentFixture<ContactSFDeleteDialogComponent>;
        let service: ContactSFService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SandboxForMuleTestModule],
                declarations: [ContactSFDeleteDialogComponent]
            })
                .overrideTemplate(ContactSFDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactSFDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactSFService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
