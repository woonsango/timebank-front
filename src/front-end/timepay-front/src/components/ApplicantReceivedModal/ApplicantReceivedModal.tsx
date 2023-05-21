import { Button, Form, Input, message, Modal, Space, Table } from 'antd';
import type { ColumnType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import { useQueryClient } from 'react-query';
import {
  cssModalFooter,
  cssRegisterModal,
} from './ApplicantReceivedModalstyle';
import useFontSize from '../../hooks/useFontSize';
import { IApplicant } from '../../api/interfaces/IApplicant';

export interface AgentModalProps {
  applicants?: IApplicant[];
  isOpen: boolean;
  onCancel: () => void;
}

interface DataType {
  key: React.Key;
  appliUid: number;
  appliName: string;
}

const ApplicantReceivedModal = ({
  applicants,
  isOpen,
  onCancel,
}: AgentModalProps) => {
  const queryClient = useQueryClient();
  const { scaleValue } = useFontSize();

  const [form] = Form.useForm();

  const myUID = '#54b6bd';

  const onFinish = useCallback(() => {
    console.log('test');
  }, []);

  const onRegisterClick = useCallback(() => {
    console.log('test');
  }, []);

  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: IApplicant[]) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        'selectedRows: ',
        selectedRows,
      );
    },
  };

  const columns = [
    {
      title: 'appliUid',
      dataIndex: 'appliUid',
      key: 'appliUid',
      width: 30,
    },
    {
      title: 'appliName',
      dataIndex: 'appliName',
      key: 'appliName',
      width: 30,
    },
  ];

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
              수락
            </Button>
            <Button
              className="agentReject"
              onClick={onRegisterClick}
              type="primary"
            >
              거절
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
      title={'받은 신청 목록'}
      onCancel={onCancel}
      open={isOpen}
      footer={footer}
      width={700}
      closable={false}
      css={cssRegisterModal(scaleValue)}
    >
      <Table
        rowSelection={{
          type: 'radio',
          ...rowSelection,
        }}
        dataSource={applicants}
        rowKey="appliUid"
        columns={columns}
        showHeader={false}
        pagination={false}
        scroll={{ y: 240 }}
      />
    </Modal>
  );
};
export default ApplicantReceivedModal;
