import { PageableData } from './ICommon';

export interface IGetAgencyRequest {
  pageIndex: number;
  pageSize: number;
  query?: string;
  value?: string;
  volunteer?: string | null;
  volunteerCheck?: boolean;
  queryValue: string;
}

export interface IAgency {
  userId: number;
  id: string;
  organizationName: string;
  imageUrl: string;
  businessNumber?: string;
  managerName: string;
  managerPhone: string;
  certificationUrl?: string;
  authority: string;
  employeeNum: number;
  timepay: number;
}

export interface IGetAgenciesResponse extends PageableData {
  content: IAgency[];
}
