import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountSF } from 'app/shared/model/account-sf.model';

@Component({
    selector: 'jhi-account-sf-detail',
    templateUrl: './account-sf-detail.component.html'
})
export class AccountSFDetailComponent implements OnInit {
    accountSF: IAccountSF;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountSF }) => {
            this.accountSF = accountSF;
        });
    }

    previousState() {
        window.history.back();
    }
}
