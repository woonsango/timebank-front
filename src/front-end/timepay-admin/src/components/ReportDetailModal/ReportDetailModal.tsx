import { Button, Modal } from 'antd';
import { useMemo } from 'react';
import { ReportItem } from '../../interfaces/ReportItem';
import { cssReportDetailModalStyle } from './ReportDetailModal.styles';

interface ReportDetailModalProps {
  post?: ReportItem;
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
        <Button type="primary"> 신고처리</Button>
        <Button type="primary"> 신고반려</Button>
        <Button type="primary" onClick={onCancel}>
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
        <div className="reportInnerContent">{post?.content}</div>
        <span className="title">신고 대상 내용</span>
        <div className="reportInnerContent">{post?.postContent}</div>
      </div>
    </Modal>
  );
};
export default ReportDetailModal;
