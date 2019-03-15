import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountSF } from 'app/shared/model/account-sf.model';
import { AccountService } from 'app/core';
import { AccountSFService } from './account-sf.service';

@Component({
    selector: 'jhi-account-sf',
    templateUrl: './account-sf.component.html'
})
export class AccountSFComponent implements OnInit, OnDestroy {
    accountSFS: IAccountSF[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected accountSFService: AccountSFService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.accountSFService
            .query()
            .pipe(
                filter((res: HttpResponse<IAccountSF[]>) => res.ok),
                map((res: HttpResponse<IAccountSF[]>) => res.body)
            )
            .subscribe(
                (res: IAccountSF[]) => {
                    this.accountSFS = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAccountSFS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAccountSF) {
        return item.id;
    }

    registerChangeInAccountSFS() {
        this.eventSubscriber = this.eventManager.subscribe('accountSFListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
