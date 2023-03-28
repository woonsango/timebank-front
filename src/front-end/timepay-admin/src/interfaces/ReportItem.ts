export interface ReportItem {
  reportId: number; //신고번호
  name: string; //신고자 이름
  number: number; //신고자 회원번호
  nickname: string; //신고자 닉네임
  type: '댓글' | '게시글'; //신고글타입
  content: string; //신고사유
  postNumber: number; //신고대상 글번호
  postContent: string; //신고대상 글내용
  reportAt: string; //신고일시
  authorNumber: number; //신고대상자 회원번호
  reportStatus: 'Y' | 'N'; //신고처리여부
}
