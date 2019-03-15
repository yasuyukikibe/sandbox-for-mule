import { IAccountSF } from 'app/shared/model/account-sf.model';

export interface IContactSF {
    id?: number;
    customExtIdField?: string;
    firstName?: string;
    lastName?: string;
    accountSF?: IAccountSF;
}

export class ContactSF implements IContactSF {
    constructor(
        public id?: number,
        public customExtIdField?: string,
        public firstName?: string,
        public lastName?: string,
        public accountSF?: IAccountSF
    ) {}
}
