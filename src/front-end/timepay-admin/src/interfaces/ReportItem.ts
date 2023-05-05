export interface ReportItem {
  reportId: number; //신고번호
  reporterId: number; //신고자 회원번호
  reporterName: string; //신고자 이름
  type: string; //신고글타입
  reason: string; //신고사유
  targetId: number; //신고대상 글번호
  reportedAt: string; //신고일시
  targetReportId: number; //신고대상자 회원번호
  process: string; //신고처리여부
}
