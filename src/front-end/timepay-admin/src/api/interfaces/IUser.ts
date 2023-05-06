import { GetPageableData, PageableData } from './ICommon';

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

// export interface IUser {
//   userId: number;
//   userName: string;
//   nickName: string;
//   sex: string;
//   birth: string;
//   region: string;
//   timepay: number;
// }

export interface IGetUserInfoRequest extends GetPageableData {
  nickName?: string;
}
export interface IGetUserInfoResponse extends PageableData {
  content: IUser[];
}
