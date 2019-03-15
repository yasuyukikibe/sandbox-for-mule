import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAccountSF } from 'app/shared/model/account-sf.model';
import { AccountSFService } from './account-sf.service';

@Component({
    selector: 'jhi-account-sf-update',
    templateUrl: './account-sf-update.component.html'
})
export class AccountSFUpdateComponent implements OnInit {
    accountSF: IAccountSF;
    isSaving: boolean;

    constructor(protected accountSFService: AccountSFService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountSF }) => {
            this.accountSF = accountSF;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.accountSF.id !== undefined) {
            this.subscribeToSaveResponse(this.accountSFService.update(this.accountSF));
        } else {
            this.subscribeToSaveResponse(this.accountSFService.create(this.accountSF));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountSF>>) {
        result.subscribe((res: HttpResponse<IAccountSF>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
