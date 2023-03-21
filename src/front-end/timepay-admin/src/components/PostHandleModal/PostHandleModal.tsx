import { Button, message, Modal, Select, Table } from 'antd';
import type { ColumnType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import { IPost } from '../../api/interfaces/IPost';
import dayjs from 'dayjs';
import { cssPostHandleModalStyle } from './PostHandleModal.styles';

export interface PostHandleModalProps {
  type: 'hide' | 'changeStatus';
  posts?: IPost[];
  isOpen: boolean;
  onCancel: () => void;
}
const PostHandleModal = ({
  type,
  posts,
  isOpen,
  onCancel,
}: PostHandleModalProps) => {
  const [messageApi, contextHolder] = message.useMessage();
  const [status, setStatus] = useState<string>();

  // @ts-ignore
  const columns: ColumnType<IPost> = useMemo(() => {
    return [
      {
        title: '게시글 번호',
        key: 'postId',
        dataIndex: 'postId',
        width: 70,
        sorter: (a: IPost, b: IPost) => b.postId - a.postId,
      },
      {
        title: '상태',
        key: 'status',
        dataIndex: 'status',
        width: 100,
        align: 'center',
        filters: [
          { text: '매칭중', value: '매칭중' },
          { text: '매칭완료', value: '매칭완료' },
          { text: '활동시작', value: '활동시작' },
          { text: '활동완료', value: '활동완료' },
          { text: '활동취소', value: '활동취소' },
          { text: '활동지연', value: '활동지연' },
        ],
        onFilter: (value: string, record: IPost) =>
          record.status.indexOf(value) === 0,
      },
      {
        title: '작성자 이름',
        key: 'userName',
        dataIndex: 'userName',
        width: 100,
        align: 'center',
        render: (_userName: string, record: IPost) => record.user.name,
      },
      {
        title: '작성자 회원번호',
        key: 'userPk',
        dataIndex: 'userPk',
        width: 110,
        sorter: (a: IPost, b: IPost) => a.user.userPk - b.user.userPk,
        render: (_userPk: string, record: IPost) => record.user.userPk,
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 140,
        align: 'center',
        sorter: (a: IPost, b: IPost) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },
      {
        title: '제목',
        key: 'title',
        dataIndex: 'title',
        width: 300,
        align: 'left',
      },
    ];
  }, []);

  const handleOnSubmit = useCallback(() => {
    if (type === 'changeStatus') {
      if (!!status)
        Modal.confirm({
          type: 'warning',
          content: '정말 상태를 변경하시겠습니까?',
          okText: '확인',
          cancelText: '취소',
          onOk: () => {
            setStatus(undefined);
            messageApi.open({
              type: 'success',
              content: '상태 변경이 완료되었습니다.',
            });
          },
        });
      else {
        Modal.error({
          content: '변경할 상태를 선택해주세요',
          okText: '확인',
        });
      }
    } else {
      Modal.confirm({
        content: '정말 숨김 처리하시겠습니까?',
        okText: '확인',
        cancelText: '취소',
        onOk: () =>
          messageApi.open({
            type: 'success',
            content: '숨김 처리 완료되었습니다.',
          }),
      });
    }
  }, [type, status, messageApi]);

  const footer = useMemo(() => {
    return (
      <>
        <Button onClick={onCancel}>취소</Button>
        <Button onClick={() => handleOnSubmit()} type="primary">
          {type === 'changeStatus' ? '상태 변경' : '숨김 처리'}
        </Button>
      </>
    );
  }, [type, onCancel, handleOnSubmit]);

  return (
    <Modal
      title={type === 'changeStatus' ? '게시글 상태 변경' : '게시글 숨김 처리'}
      onCancel={onCancel}
      open={isOpen}
      footer={footer}
      css={cssPostHandleModalStyle}
    >
      {contextHolder}
      <div className="posts-info">
        {type === 'changeStatus' ? (
          <Select
            value={status}
            onChange={(value: string) => setStatus(value)}
            placeholder="상태 선택"
          >
            <Select.Option value="매칭중">매칭중</Select.Option>
            <Select.Option value="매칭완료">매칭완료</Select.Option>
            <Select.Option value="활동시작">활동시작</Select.Option>
            <Select.Option value="활동완료">활동완료</Select.Option>
            <Select.Option value="활동취소">활동취소</Select.Option>
            <Select.Option value="활동지연">활동지연</Select.Option>
          </Select>
        ) : (
          <span></span>
        )}
        <span>총 {posts?.length} 개</span>
      </div>
      <Table
        //@ts-ignore
        columns={columns}
        rowKey="postId"
        dataSource={posts}
        scroll={{ x: 1000, y: 300 }}
        pagination={false}
      />
    </Modal>
  );
};
export default PostHandleModal;
