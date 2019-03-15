import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountSF } from 'app/shared/model/account-sf.model';

type EntityResponseType = HttpResponse<IAccountSF>;
type EntityArrayResponseType = HttpResponse<IAccountSF[]>;

@Injectable({ providedIn: 'root' })
export class AccountSFService {
    public resourceUrl = SERVER_API_URL + 'api/account-sfs';

    constructor(protected http: HttpClient) {}

    create(accountSF: IAccountSF): Observable<EntityResponseType> {
        return this.http.post<IAccountSF>(this.resourceUrl, accountSF, { observe: 'response' });
    }

    update(accountSF: IAccountSF): Observable<EntityResponseType> {
        return this.http.put<IAccountSF>(this.resourceUrl, accountSF, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAccountSF>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAccountSF[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
