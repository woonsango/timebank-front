import { Button, Modal, Table } from 'antd';
import type { ColumnType } from 'antd/es/table';
import { useMemo } from 'react';
import dayjs from 'dayjs';
import { IPush } from '../../api/interfaces/IPush';
import { INotification } from '../../api/interfaces/INotification';

export interface PushDeleteModalProps {
  pushes?: INotification[];
  isOpen: boolean;
  onCancel: () => void;
}
const PushDeleteModal = ({
  pushes,
  isOpen,
  onCancel,
}: PushDeleteModalProps) => {
  //@ts-ignore
  const columns: ColumnType<INotification> = useMemo(() => {
    return [
      {
        title: '공지 번호',
        key: 'notificationId',
        dataIndex: 'notificationId',
        width: 50,
        sorter: (a: INotification, b: INotification) =>
          a.notificationId - b.notificationId,
      },

      {
        title: '작성자',
        key: 'name',
        dataIndex: 'name',
        width: 80,
        align: 'center',
        render: (_userName: string, record: IPush) => record.name,
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 100,
        align: 'center',
        sorter: (a: INotification, b: INotification) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },
    ];
  }, []);

  const footer = useMemo(() => {
    return (
      <>
        <Button onClick={onCancel}>취소</Button>
        <Button onClick={onCancel} type="primary">
          삭제
        </Button>
      </>
    );
  }, [onCancel]);

  return (
    <Modal
      title={'공지 삭제'}
      onCancel={onCancel}
      open={isOpen}
      footer={footer}
      width={1100}
    >
      <div className="pushes-info">
        <span>총 {pushes?.length} 개</span>
      </div>
      <Table
        //@ts-ignore
        columns={columns}
        rowKey="notificationId"
        dataSource={pushes}
        scroll={{ x: 1000, y: 300 }}
        pagination={false}
      />
    </Modal>
  );
};
export default PushDeleteModal;
