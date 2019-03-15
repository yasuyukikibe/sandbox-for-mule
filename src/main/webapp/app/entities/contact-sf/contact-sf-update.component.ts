import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContactSF } from 'app/shared/model/contact-sf.model';
import { ContactSFService } from './contact-sf.service';
import { IAccountSF } from 'app/shared/model/account-sf.model';
import { AccountSFService } from 'app/entities/account-sf';

@Component({
    selector: 'jhi-contact-sf-update',
    templateUrl: './contact-sf-update.component.html'
})
export class ContactSFUpdateComponent implements OnInit {
    contactSF: IContactSF;
    isSaving: boolean;

    accountsfs: IAccountSF[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contactSFService: ContactSFService,
        protected accountSFService: AccountSFService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactSF }) => {
            this.contactSF = contactSF;
        });
        this.accountSFService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAccountSF[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAccountSF[]>) => response.body)
            )
            .subscribe((res: IAccountSF[]) => (this.accountsfs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contactSF.id !== undefined) {
            this.subscribeToSaveResponse(this.contactSFService.update(this.contactSF));
        } else {
            this.subscribeToSaveResponse(this.contactSFService.create(this.contactSF));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactSF>>) {
        result.subscribe((res: HttpResponse<IContactSF>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAccountSFById(index: number, item: IAccountSF) {
        return item.id;
    }
}
