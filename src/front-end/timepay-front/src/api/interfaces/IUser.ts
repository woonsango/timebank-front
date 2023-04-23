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

export interface IGetUserInformationResponse {
  id: number;
  image_url: any;
  nick_name: string;
  time_pay: number;
  free_register: any[];
  deal_register: any[];
  free_board_comment: any[];
  deal_board_comment: any[];
}

export interface IPostUserInformationResponse {
  id: number;
  image_url: string;
  name: string;
  nick_name: string;
  location: string;
  phone: string;
  introduction: string;
  birthday: string;
}
