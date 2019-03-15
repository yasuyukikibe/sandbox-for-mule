import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactSF } from 'app/shared/model/contact-sf.model';
import { ContactSFService } from './contact-sf.service';

@Component({
    selector: 'jhi-contact-sf-delete-dialog',
    templateUrl: './contact-sf-delete-dialog.component.html'
})
export class ContactSFDeleteDialogComponent {
    contactSF: IContactSF;

    constructor(
        protected contactSFService: ContactSFService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactSFService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactSFListModification',
                content: 'Deleted an contactSF'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-sf-delete-popup',
    template: ''
})
export class ContactSFDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactSF }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactSFDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.contactSF = contactSF;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/contact-sf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/contact-sf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
