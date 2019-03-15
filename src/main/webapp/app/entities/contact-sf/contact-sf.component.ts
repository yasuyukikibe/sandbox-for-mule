import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContactSF } from 'app/shared/model/contact-sf.model';
import { AccountService } from 'app/core';
import { ContactSFService } from './contact-sf.service';

@Component({
    selector: 'jhi-contact-sf',
    templateUrl: './contact-sf.component.html'
})
export class ContactSFComponent implements OnInit, OnDestroy {
    contactSFS: IContactSF[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected contactSFService: ContactSFService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.contactSFService
            .query()
            .pipe(
                filter((res: HttpResponse<IContactSF[]>) => res.ok),
                map((res: HttpResponse<IContactSF[]>) => res.body)
            )
            .subscribe(
                (res: IContactSF[]) => {
                    this.contactSFS = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInContactSFS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IContactSF) {
        return item.id;
    }

    registerChangeInContactSFS() {
        this.eventSubscriber = this.eventManager.subscribe('contactSFListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
