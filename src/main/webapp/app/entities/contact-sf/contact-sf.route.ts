import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactSF } from 'app/shared/model/contact-sf.model';
import { ContactSFService } from './contact-sf.service';
import { ContactSFComponent } from './contact-sf.component';
import { ContactSFDetailComponent } from './contact-sf-detail.component';
import { ContactSFUpdateComponent } from './contact-sf-update.component';
import { ContactSFDeletePopupComponent } from './contact-sf-delete-dialog.component';
import { IContactSF } from 'app/shared/model/contact-sf.model';

@Injectable({ providedIn: 'root' })
export class ContactSFResolve implements Resolve<IContactSF> {
    constructor(private service: ContactSFService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContactSF> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactSF>) => response.ok),
                map((contactSF: HttpResponse<ContactSF>) => contactSF.body)
            );
        }
        return of(new ContactSF());
    }
}

export const contactSFRoute: Routes = [
    {
        path: '',
        component: ContactSFComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactSFS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ContactSFDetailComponent,
        resolve: {
            contactSF: ContactSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactSFS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ContactSFUpdateComponent,
        resolve: {
            contactSF: ContactSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactSFS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ContactSFUpdateComponent,
        resolve: {
            contactSF: ContactSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactSFS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactSFPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ContactSFDeletePopupComponent,
        resolve: {
            contactSF: ContactSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactSFS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
