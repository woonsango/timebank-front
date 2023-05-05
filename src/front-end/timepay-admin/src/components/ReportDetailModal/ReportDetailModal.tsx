import { Button, Modal } from 'antd';
import { useMemo } from 'react';
import { IReport } from '../../api/interfaces/IReport';
import { cssReportDetailModalStyle } from './ReportDetailModal.styles';

interface ReportDetailModalProps {
  post?: IReport;
  isOpen: boolean;
  onCancel: () => void;
}
const ReportDetailModal = ({
  post,
  isOpen,
  onCancel,
}: ReportDetailModalProps) => {
  const footer = useMemo(() => {
    return (
      <div>
        <Button type="primary">신고처리</Button>
        <Button type="primary" danger>
          신고반려
        </Button>
        <Button type="primary" ghost onClick={onCancel}>
          닫기
        </Button>
      </div>
    );
  }, [onCancel]);

  return (
    <Modal
      title="신고 처리"
      open={isOpen}
      onOk={onCancel}
      onCancel={onCancel}
      footer={footer}
      width={700}
      css={cssReportDetailModalStyle}
    >
      <div className="reportContent">
        <span className="title">신고 사유</span>
        <div className="reportInnerContent">{post?.reason}</div>
        {/* 게시글 조회 api 수정되면 추가 */}
        {/* <span className="title">신고 대상 내용</span>
        <div className="reportInnerContent">{post?.postContent}</div> */}
      </div>
    </Modal>
  );
};
export default ReportDetailModal;
