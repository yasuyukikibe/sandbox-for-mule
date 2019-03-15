import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountSF } from 'app/shared/model/account-sf.model';
import { AccountSFService } from './account-sf.service';
import { AccountSFComponent } from './account-sf.component';
import { AccountSFDetailComponent } from './account-sf-detail.component';
import { AccountSFUpdateComponent } from './account-sf-update.component';
import { AccountSFDeletePopupComponent } from './account-sf-delete-dialog.component';
import { IAccountSF } from 'app/shared/model/account-sf.model';

@Injectable({ providedIn: 'root' })
export class AccountSFResolve implements Resolve<IAccountSF> {
    constructor(private service: AccountSFService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountSF> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AccountSF>) => response.ok),
                map((accountSF: HttpResponse<AccountSF>) => accountSF.body)
            );
        }
        return of(new AccountSF());
    }
}

export const accountSFRoute: Routes = [
    {
        path: '',
        component: AccountSFComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountSFS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AccountSFDetailComponent,
        resolve: {
            accountSF: AccountSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountSFS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AccountSFUpdateComponent,
        resolve: {
            accountSF: AccountSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountSFS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AccountSFUpdateComponent,
        resolve: {
            accountSF: AccountSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountSFS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountSFPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AccountSFDeletePopupComponent,
        resolve: {
            accountSF: AccountSFResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountSFS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
