import { Button, Card, Input, Modal } from 'antd';
import { useMemo } from 'react';
import { IInquiry } from '../../api/interfaces/IInquiry';
import { cssQnADetailModalStyle } from './QnADetailModal.styles';

interface QnADetailModalProps {
  qna?: IInquiry;
  isOpen: boolean;
  onCancel: () => void;
}
const { TextArea } = Input;
const QnADetailModal = ({ qna, isOpen, onCancel }: QnADetailModalProps) => {
  const footer = useMemo(() => {
    return (
      <div>
        <Button type="primary">답변완료</Button>
        <Button type="primary" onClick={onCancel}>
          닫기
        </Button>
      </div>
    );
  }, [onCancel]);

  return (
    <Modal
      title="문의 관리"
      open={isOpen}
      onOk={onCancel}
      onCancel={onCancel}
      footer={footer}
      width={700}
      css={cssQnADetailModalStyle}
    >
      <div className="QnATitle">
        <Card title={qna?.title}>
          <div className="QnAInnerContent">{qna?.content}</div>
        </Card>
        <div className="answerInput">
          <TextArea
            className="textInput"
            placeholder="답변을 입력해주세요"
            autoSize={{ minRows: 5, maxRows: 5 }}
          />
        </div>
      </div>
    </Modal>
  );
};
export default QnADetailModal;
