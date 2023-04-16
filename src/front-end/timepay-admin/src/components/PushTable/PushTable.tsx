import { Button, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import {
  cssPushTableRowCountStyle,
  cssPushTableStyle,
} from './PushTable.styles';
import PushDetailModal from '../PushDetailModal';
import { useGetNotifications } from '../../api/hooks/notification';
import { useRecoilValue } from 'recoil';
import { pushSearchState } from '../../states/pushSearchState';
import { INotification } from '../../api/interfaces/INotification';

interface PushTableProps {
  selectedPushIds?: React.Key[];
  setSelectedPushIds: (args?: React.Key[]) => void;
  setSelectedPushes: (args?: INotification[]) => void;
}

const PushTable = ({
  selectedPushIds,
  setSelectedPushIds,
  setSelectedPushes,
}: PushTableProps) => {
  const pushSearchValues = useRecoilValue(pushSearchState);
  const { data, isLoading } = useGetNotifications(pushSearchValues);

  const [isOpen, setIsOpen] = useState(false);
  const [currentPush, setCurrentPush] = useState<INotification>();

  const handleOnShowDetailPush = useCallback((push: INotification) => {
    setCurrentPush(push);
    setIsOpen(true);
  }, []);

  const handleOnCloseDetailPush = useCallback(() => {
    setCurrentPush(undefined);
    setIsOpen(false);
  }, []);

  const dataSource = useMemo(() => {
    if (pushSearchValues) {
      return data?.data.content || [];
    }
    return [];
  }, [pushSearchValues, data]);

  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: INotification[]) => {
      setSelectedPushIds(selectedRowKeys);
      setSelectedPushes(selectedRows);
    },
  };

  // @ts-ignore
  const columns: ColumnsType<INotification> = useMemo(() => {
    return [
      {
        title: '공지 번호',
        key: 'notificationId',
        dataIndex: 'notificationId',
        width: 80,
        sorter: (a: INotification, b: INotification) =>
          a.notificationId - b.notificationId,
      },
      {
        title: '작성자',
        key: 'name',
        dataIndex: 'name',
        width: 100,
        align: 'center',
        render: (_userName: string, record: INotification) =>
          record.admin.adminName,
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 140,
        align: 'center',
        sorter: (a: INotification, b: INotification) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },
      {
        title: '공지 제목',
        key: 'title',
        dataIndex: 'title',
        width: 150,
        align: 'center',
      },
      {
        title: '공지 내용',
        key: 'content',
        dataIndex: 'content',
        width: 150,
        align: 'center',
        render: (_: string, record: INotification) => (
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
        총 {dataSource.length} 개
      </div>
      <Table
        css={cssPushTableStyle}
        rowSelection={rowSelection}
        columns={columns}
        scroll={{ x: 1200 }}
        dataSource={dataSource}
        rowKey="notificationId"
        loading={isLoading}
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
