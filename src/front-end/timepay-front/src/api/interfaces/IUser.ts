export interface IUser {
  userPk: number;
  name: string; // 이름
  sex: string; // 성별
  birthday: string; // 생년월일
  profileImage?: string;
  nickname: string;
  region: string;
  profileMessage?: string;
  phoneNumber: string;
  createdAt: string; // 계정생성일
  accountEmail: string;
  isAdmin: boolean; // 관리자 여부
}
