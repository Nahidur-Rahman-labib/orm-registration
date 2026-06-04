export interface Address {
    addressId?: number;
    clientId: number;
    address: string;
    addressTypeId?: number;
    countryId?: number;
    divisionId?: number;
    districtId?: number;
    thanaId?: number;
    city?: string;
    zipCode?: string;
    mobileNo?: string;
    email?: string;
}