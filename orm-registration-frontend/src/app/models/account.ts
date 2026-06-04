export interface Account {
    officeId: number;
    clAccSl: number;
    clientId: number;
    accountTitle?: string;
    accountOpenDt?: string;
    effectiveDt?: string;
    expiryDt?: string;
    limitAmt?: number;
    entityId?: string;
}