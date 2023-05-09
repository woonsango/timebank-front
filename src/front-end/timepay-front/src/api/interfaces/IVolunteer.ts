export interface IVolunteerBoard {
  boardId: number;
  title: string;
  organizationName: string;
  managerName: string;
  managerPhone: string;
  volunteerTime: number;
  state: boolean;
  certificationUrl?: string | null;
}
