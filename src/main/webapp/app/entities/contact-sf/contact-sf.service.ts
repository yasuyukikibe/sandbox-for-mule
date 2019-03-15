import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactSF } from 'app/shared/model/contact-sf.model';

type EntityResponseType = HttpResponse<IContactSF>;
type EntityArrayResponseType = HttpResponse<IContactSF[]>;

@Injectable({ providedIn: 'root' })
export class ContactSFService {
    public resourceUrl = SERVER_API_URL + 'api/contact-sfs';

    constructor(protected http: HttpClient) {}

    create(contactSF: IContactSF): Observable<EntityResponseType> {
        return this.http.post<IContactSF>(this.resourceUrl, contactSF, { observe: 'response' });
    }

    update(contactSF: IContactSF): Observable<EntityResponseType> {
        return this.http.put<IContactSF>(this.resourceUrl, contactSF, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContactSF>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactSF[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
