import { IContactSF } from 'app/shared/model/contact-sf.model';

export interface IAccountSF {
    id?: number;
    customExtIdField?: string;
    name?: string;
    contactSFS?: IContactSF[];
}

export class AccountSF implements IAccountSF {
    constructor(public id?: number, public customExtIdField?: string, public name?: string, public contactSFS?: IContactSF[]) {}
}
