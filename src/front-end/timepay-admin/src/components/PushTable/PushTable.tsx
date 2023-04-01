import { Button, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import {
  cssPushTableRowCountStyle,
  cssPushTableStyle,
} from './PushTable.styles';
import PushDetailModal from '../PushDetailModal';
import { IPush, IPushType } from '../../api/interfaces/IPush';

interface PushTableProps {
  selectedPushIds?: React.Key[];
  setSelectedPushIds: (args?: React.Key[]) => void;
  setSelectedPushs: (args?: IPush[]) => void;
}

const PushTable = ({
  selectedPushIds,
  setSelectedPushIds,
  setSelectedPushs,
}: PushTableProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const [currentPush, setCurrentPush] = useState<IPush>();

  const handleOnShowDetailPush = useCallback((push: IPush) => {
    setCurrentPush(push);
    setIsOpen(true);
  }, []);

  const handleOnCloseDetailPush = useCallback(() => {
    setCurrentPush(undefined);
    setIsOpen(false);
  }, []);
  const pushTypes: IPushType[] = ['notice', 'activity', 'comment', 'bookmark'];

  const dummyDataSource: IPush[] = [];
  for (let i = 0; i < 100; i++) {
    dummyDataSource.push({
      pushId: i,
      type: pushTypes[i % 4],
      name: `일반관리자1`,
      createdAt: `2023-12-34 12:34`,
      title: `공지 제목 ${i}`,
      content: `공지 내용 ${i}`,
    });
  }
  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: IPush[]) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        'selectedRows: ',
        selectedRows,
      );
      setSelectedPushIds(selectedRowKeys);
      setSelectedPushs(selectedRows);
    },
  };

  // @ts-ignore
  const columns: ColumnsType<IPush> = useMemo(() => {
    return [
      {
        title: '공지 번호',
        key: 'pushId',
        dataIndex: 'pushId',
        width: 90,
        sorter: (a: IPush, b: IPush) => a.pushId - b.pushId,
      },
      {
        title: '공지 유형',
        key: 'type',
        dataIndex: 'type',
        width: 100,
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
        width: 100,
        align: 'center',
        render: (_userName: string, record: IPush) => record.name,
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 140,
        align: 'center',
        sorter: (a: IPush, b: IPush) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },
      {
        title: '공지 제목',
        key: 'title',
        dataIndex: 'title',
        width: 150,
        align: 'center',

        render: (_: string, record: IPush) => (
          <Button type="link" onClick={() => handleOnShowDetailPush(record)}>
            더보기
          </Button>
        ),
      },
      {
        title: '공지 내용',
        key: 'content',
        dataIndex: 'content',
        width: 150,
        align: 'center',
        render: (_: string, record: IPush) => (
          <Button type="link" onClick={() => handleOnShowDetailPush(record)}>
            더보기
          </Button>
        ),
      },
    ];
  }, [handleOnShowDetailPush]);

  return (
    <>
      <div css={cssPushTableRowCountStyle}>
        {selectedPushIds && selectedPushIds.length > 0
          ? `${selectedPushIds.length} 개 선택 / `
          : ''}
        총 {dummyDataSource.length} 개
      </div>
      <Table
        css={cssPushTableStyle}
        rowSelection={rowSelection}
        columns={columns}
        scroll={{ x: 1500 }}
        dataSource={dummyDataSource}
        rowKey="pushId"
      />
      <PushDetailModal
        isOpen={isOpen}
        onCancel={handleOnCloseDetailPush}
        push={currentPush}
      />
    </>
  );
};

export default PushTable;
