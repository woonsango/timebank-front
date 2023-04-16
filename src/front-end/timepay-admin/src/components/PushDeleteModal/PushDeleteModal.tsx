import { Button, message, Modal, Table } from 'antd';
import type { ColumnType } from 'antd/es/table';
import { useCallback, useMemo } from 'react';
import dayjs from 'dayjs';
import { INotification } from '../../api/interfaces/INotification';
import { useQueryClient } from 'react-query';
import { useDeleteNotifications } from '../../api/hooks/notification';

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
  const queryClient = useQueryClient();
  const deleteNotificationMutation = useDeleteNotifications();

  const [messageApi, contextHolder] = message.useMessage();

  const handleOnDeleteNotifications = useCallback(() => {
    if (pushes) {
      deleteNotificationMutation.mutateAsync(
        pushes?.map((push) => push.notificationId),
        {
          onSuccess: (result) => {
            messageApi.open({
              type: 'success',
              content: '공지가 삭제되었습니다.',
            });
          },
          onError: (err) => {
            messageApi.open({
              type: 'error',
              content: (
                <>
                  오류 발생: <br />
                  {err}
                </>
              ),
            });
          },
          onSettled: async (data) => {
            onCancel();
            await queryClient.invalidateQueries({
              queryKey: ['useGetNotifications'],
            });
          },
        },
      );
    }
  }, [pushes, onCancel, messageApi, queryClient, deleteNotificationMutation]);

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
        render: (_userName: string, record: INotification) =>
          record.admin.adminName,
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 100,
        align: 'center',
        render: (createAt: string) =>
          (createAt || '').split('.')[0].replaceAll('T', ' '),
        sorter: (a: INotification, b: INotification) =>
          dayjs(a.createdAt, 'YYYY-MM-DDTHH:mm:ss').unix() -
          dayjs(b.createdAt, 'YYYY-MM-DDTHH:mm:ss').unix(),
      },
    ];
  }, []);

  const footer = useMemo(() => {
    return (
      <>
        <Button onClick={onCancel}>취소</Button>
        <Button onClick={handleOnDeleteNotifications} type="primary">
          삭제
        </Button>
      </>
    );
  }, [onCancel, handleOnDeleteNotifications]);

  return (
    <Modal
      title={'공지 삭제'}
      onCancel={onCancel}
      open={isOpen}
      footer={footer}
      width={1100}
    >
      {contextHolder}
      <div className="pushes-info">
        <span>총 {pushes?.length || 0} 개</span>
      </div>
      <Table
        //@ts-ignore
        columns={columns}
        rowKey="notificationId"
        dataSource={pushes}
        scroll={{ x: 300, y: 300 }}
        pagination={false}
      />
    </Modal>
  );
};
export default PushDeleteModal;
