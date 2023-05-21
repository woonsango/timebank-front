export interface IApplicant {
  appliUid: number;
  appliName: string;
}

export interface IGetApplicantResponse {
  status: boolean;
  applicant: IApplicant[];
  myUid: number;
  myName: string;
}
