import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactSF } from 'app/shared/model/contact-sf.model';

@Component({
    selector: 'jhi-contact-sf-detail',
    templateUrl: './contact-sf-detail.component.html'
})
export class ContactSFDetailComponent implements OnInit {
    contactSF: IContactSF;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactSF }) => {
            this.contactSF = contactSF;
        });
    }

    previousState() {
        window.history.back();
    }
}
