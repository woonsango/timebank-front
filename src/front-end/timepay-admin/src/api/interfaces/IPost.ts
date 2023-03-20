import { IUser } from './IUser';

export interface IPost {
  postId: number; // 게시글 번호
  type: IPostType; // 게시글 유형
  createdAt: string; // 작성 날짜
  updatedAt?: string; // 수정 날짜
  title: string; // 제목
  status: IPostState; // 게시글 상태
  category: string; // 게시글 카테고리
  pay: number; // 타임페이 교환량
  startTime: string; //활동 시작 시간
  endTime: string; // 종료 시간
  region: string; // 지역
  attachment?: string; // 첨부파일
  user: IUser; // 작성자 정보
  content: string; // 게시글 내용
  originPostId?: number; //원본 게시글 번호
}

export type IPostState =
  | '매칭중'
  | '매칭완료'
  | '활동시작'
  | '활동완료'
  | '활동취소'
  | '활동지연';

export type IPostType = '도움요청' | '도움주기' | '자유' | '후기';
