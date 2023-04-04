export interface IQna {
  qnaId: number; // 문의 번호
  title: string; // 제목
  createdAt: string; // 작성 날짜
  status: IQnaState; // 게시글 상태
  category: string; // 게시글 카테고리
  content: string; // 내용
  attachment?: string; // 첨부파일
  user: string; // 문의 작성자 정보
  answer?: string; //문의 답변
}

export type IQnaState = '답변대기' | '답변완료';
