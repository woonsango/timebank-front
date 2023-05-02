export interface IAgencyLoginRequest {
  id: string;
  pw: string;
}

export interface IAgency {
  createdAt: string;
  updatedAt?: string;
  organizationId: number;
  organizationName: string;
  businessCode?: string;
  employeeNum: number;
  timepay: number;
  account: string;
  pw: string;
  authority: string;
  imageUrl?: string;
  certificationUrl?: string;
  roles: any[];
  authorities: any[];
}
