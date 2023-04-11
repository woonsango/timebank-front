import { Button, message, Modal, Select, Table } from 'antd';
import type { ColumnType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import { IPush } from '../../api/interfaces/IPush';

export interface PushDeleteModalProps {
  pushes?: IPush[];
  isOpen: boolean;
  onCancel: () => void;
}
const PushDeleteModal = ({
  pushes,
  isOpen,
  onCancel,
}: PushDeleteModalProps) => {
  // @ts-ignore
  const columns: ColumnType<IPush> = useMemo(() => {
    return [
      {
        title: '공지 번호',
        key: 'pushId',
        dataIndex: 'pushId',
        width: 50,
        sorter: (a: IPush, b: IPush) => a.pushId - b.pushId,
      },
      {
        title: '공지 유형',
        key: 'type',
        dataIndex: 'type',
        width: 80,
        align: 'center',
        filters: [
          { text: 'notice', value: 'notice' },
          { text: 'activity', value: 'activity' },
          { text: 'comment', value: 'comment' },
          { text: 'bookmark', value: 'bookmark' },
        ],
        onFilter: (value: string, record: IPush) =>
          record.type.indexOf(value) === 0,
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
        sorter: (a: IPush, b: IPush) =>
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
        rowKey="pushId"
        dataSource={pushes}
        scroll={{ x: 1000, y: 300 }}
        pagination={false}
      />
    </Modal>
  );
};
export default PushDeleteModal;
