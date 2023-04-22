import { PageableData } from './ICommon';

export const PUSH_TYPE = {
  ALL: '전체알림',
  ACTIVITY: '활동관련',
} as const;

export interface IPush {
  pushId: number; // 푸시 번호
  type: IPushType; // 푸시 유형
  title: string; // 푸시 제목
  content?: string; // 푸시 내용
  link?: string; // 푸시 클릭 시 연결될 링크
  isAlreadyRead: boolean; // 이미 확인한 푸시인지 여부
}

export type IPushType = 'notice' | 'activity' | 'comment' | 'bookmark';

export interface INotification {
  createdAt: string;
  updatedAt: string;
  pushId: null;
  type: string;
  isAlreadyRead?: boolean;
  notificationId: number;
  title: string;
  imageUrl: any;
  content: string;
  adminId: number;
  adminName: string;
  notice: boolean;
  link: null;
}
export interface IGetNotificationResponse extends PageableData {
  content: INotification[];
}
