import { Button, Form, Input, message, Modal, Space, Table } from 'antd';
import type { ColumnType } from 'antd/es/table';
import { useCallback, useMemo } from 'react';
import dayjs from 'dayjs';
import { useQueryClient } from 'react-query';
import { cssModalFooter, cssRegisterModal } from './AgentModal.style';
import useFontSize from '../../hooks/useFontSize';

export interface AgentModalProps {
  isOpen: boolean;
  onCancel: () => void;
}
const AgentModal = ({ isOpen, onCancel }: AgentModalProps) => {
  const queryClient = useQueryClient();
  const { scaleValue } = useFontSize();

  const [messageApi, contextHolder] = message.useMessage();
  const [form] = Form.useForm();

  const myUID = '#54b6bd';

  const onFinish = useCallback(() => {
    console.log('test');
  }, []);

  const onRegisterClick = useCallback(() => {
    console.log('test');
  }, []);

  const footer = useMemo(() => {
    return (
      <>
        <div className="space-align-block" css={cssModalFooter(scaleValue)}>
          <Space align="center" size={10}>
            <Button
              className="agentRegister"
              onClick={onRegisterClick}
              type="primary"
            >
              신청하기
            </Button>
            <Button className="agentDelete" onClick={onCancel} type="primary">
              취소
            </Button>
          </Space>
        </div>
      </>
    );
  }, [onCancel, onRegisterClick, scaleValue]);

  return (
    <Modal
      title={'대리인 등록'}
      onCancel={onCancel}
      open={isOpen}
      footer={footer}
      width={700}
      closable={false}
      css={cssRegisterModal(scaleValue)}
    >
      <p />
      나의 UID : <span className="myUIDnumber">{myUID}</span>
      <p />
      <Form form={form} layout="horizontal" onFinish={onFinish}>
        <Form.Item name="agentUID">
          <Input
            style={{ width: 210 }}
            placeholder="대리인의 UID를 입력해주세요"
          />
        </Form.Item>
      </Form>
      <span className="guide">
        대리인 신청시 대리인에게
        <br />
        개인정보가 노출될수 있습니다.
        <br />
        대리인 선정에 주의하세요.
      </span>
      <p />
    </Modal>
  );
};
export default AgentModal;
