import { IUser } from './IUser';

export interface IComment {
  postId: number; // 게시글 번호
  commentId: number; // 댓글 번호
  user: IUser; // 작성자 정보
  parentCommentId?: number | null; // 부모 댓글 번호
  isApply: boolean; // 지원 여부
  isSelected: boolean; // 선정 여부
  isAuthorOfPost: boolean; // 게시글 작성자 여부
  isHidden: boolean; // 댓글 숨김 여부
  createdAt: string; // 작성 날짜
  updatedAt?: string; // 수정 날짜
  content: string; // 댓글 내용
}

export interface ICommentActivity extends IComment {
  postTitle: string; // 게시글 제목
}
