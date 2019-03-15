import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountSF } from 'app/shared/model/account-sf.model';
import { AccountSFService } from './account-sf.service';

@Component({
    selector: 'jhi-account-sf-delete-dialog',
    templateUrl: './account-sf-delete-dialog.component.html'
})
export class AccountSFDeleteDialogComponent {
    accountSF: IAccountSF;

    constructor(
        protected accountSFService: AccountSFService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountSFService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'accountSFListModification',
                content: 'Deleted an accountSF'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-sf-delete-popup',
    template: ''
})
export class AccountSFDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountSF }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AccountSFDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.accountSF = accountSF;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/account-sf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/account-sf', { outlets: { popup: null } }]);
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
