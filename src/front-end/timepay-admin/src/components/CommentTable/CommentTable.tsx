import { Button, Modal, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useMemo } from 'react';
import dayjs from 'dayjs';
import {
  cssCommentTableRowCountStyle,
  cssCommentTableStyle,
} from './CommentTable.styles';
import { IComment } from '../../api/interfaces/IComment';

interface CommentTableProps {
  selectedCommentIds?: React.Key[];
  setSelectedCommentIds: (args?: React.Key[]) => void;
  setSelectedComments: (args?: IComment[]) => void;
}

const CommentTable = ({
  selectedCommentIds,
  setSelectedCommentIds,
  setSelectedComments,
}: CommentTableProps) => {
  const dummyDataSource: IComment[] = [];

  for (let i = 0; i < 100; i++) {
    dummyDataSource.push({
      commentId: i,
      postId: i % 3,
      user: {
        userPk: i * 1000 + 1,
        name: `사용자 ${i}`,
        birthday: '2000-01-15 11:00:00',
        createdAt: '2023-01-15 11:00:00',
        sex: 'W',
        nickname: '하연',
        region: '광진구',
        phoneNumber: '01023860370',
        accountEmail: 'iioo3356@gmail.com',
        isAdmin: true,
      },
      createdAt: `2023-02-18 ${(i % 12).toString()}:00:00`,
      content: `댓글 내용 ${i}`,
      parentCommentId: i % 6 > 3 ? i % 6 : null,
      isApply: i % 2 === 0,
      isSelected: i % 4 === 0,
      isAuthorOfPost: i % 20 === 0,
      isHidden: false,
    });
  }
  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: IComment[]) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        'selectedRows: ',
        selectedRows,
      );
      setSelectedCommentIds(selectedRowKeys);
      setSelectedComments(selectedRows);
    },
  };

  // @ts-ignore
  const columns: ColumnsType<IComment> = useMemo(() => {
    return [
      {
        title: '댓글 번호',
        key: 'commentId',
        dataIndex: 'commentId',
        width: 90,
        sorter: (a: IComment, b: IComment) => b.commentId - a.commentId,
      },
      {
        title: '게시글 번호',
        key: 'postId',
        dataIndex: 'postId',
        width: 90,
        sorter: (a: IComment, b: IComment) => b.postId - a.postId,
      },
      {
        title: '부모 댓글 번호',
        key: 'parentCommentId',
        dataIndex: 'parentCommentId',
        width: 90,
        sorter: (a: IComment, b: IComment) =>
          (b.parentCommentId || 0) - (a.parentCommentId || 0),
        render: (text: string) => text || '-',
      },
      {
        title: '작성자 이름',
        key: 'userName',
        dataIndex: 'userName',
        width: 100,
        align: 'center',
        render: (_userName: string, record: IComment) => record.user.name,
      },
      {
        title: '작성자 회원번호',
        key: 'userPk',
        dataIndex: 'userPk',
        width: 110,
        sorter: (a: IComment, b: IComment) => b.user.userPk - a.user.userPk,
        render: (_userPk: string, record: IComment) => record.user.userPk,
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 140,
        align: 'center',
        sorter: (a: IComment, b: IComment) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },
      {
        title: '수정일시',
        key: 'updatedAt',
        dataIndex: 'updatedAt',
        width: 140,
        align: 'center',
        sorter: (a: IComment, b: IComment) =>
          dayjs(a.updatedAt).isAfter(dayjs(b.updatedAt)),
        render: (text: string) => text || '-',
      },
      {
        title: '지원여부',
        key: 'isApply',
        dataIndex: 'isApply',
        width: 140,
        align: 'center',
        filters: [
          { text: 'Y', value: true },
          { text: 'N', value: false },
        ],
        onFilter: (value: boolean, record: IComment) =>
          record.isApply === value,
        render: (isApply: boolean) => (isApply ? 'Y' : 'N'),
      },
      {
        title: '선정여부',
        key: 'isSelected',
        dataIndex: 'isSelected',
        width: 140,
        align: 'center',
        filters: [
          { text: 'Y', value: true },
          { text: 'N', value: false },
        ],
        onFilter: (value: boolean, record: IComment) =>
          record.isSelected === value,
        render: (isSelected: boolean) => (isSelected ? 'Y' : 'N'),
      },
      {
        title: '게시글 작성자 본인 여부',
        key: 'isAuthorOfPost',
        dataIndex: 'isAuthorOfPost',
        width: 120,
        align: 'center',
        filters: [
          { text: 'Y', value: true },
          { text: 'N', value: false },
        ],
        onFilter: (value: boolean, record: IComment) =>
          record.isAuthorOfPost === value,
        render: (isAuthorOfPost: boolean) => (isAuthorOfPost ? 'Y' : 'N'),
      },
      {
        title: '숨김여부',
        key: 'isHidden',
        dataIndex: 'isHidden',
        width: 140,
        align: 'center',
        filters: [
          { text: 'Y', value: true },
          { text: 'N', value: false },
        ],
        onFilter: (value: boolean, record: IComment) =>
          record.isHidden === value,
        render: (isHidden: boolean) => (isHidden ? 'Y' : 'N'),
      },
      {
        title: '댓글 내용',
        key: 'content',
        dataIndex: 'content',
        width: 150,
        align: 'center',
        render: (_: string, record: IComment) => (
          <Button
            type="link"
            onClick={() =>
              Modal.info({
                title: `${record.commentId}번 내용`,
                content: record.content,
              })
            }
          >
            내용 보기
          </Button>
        ),
      },
    ];
  }, []);

  return (
    <>
      <div css={cssCommentTableRowCountStyle}>
        {selectedCommentIds && selectedCommentIds.length > 0
          ? `${selectedCommentIds.length} 개 선택 / `
          : ''}
        총 {dummyDataSource.length} 개
      </div>
      <Table
        css={cssCommentTableStyle}
        rowSelection={rowSelection}
        columns={columns}
        scroll={{ x: 1500 }}
        dataSource={dummyDataSource}
        rowKey="commentId"
      />
    </>
  );
};

export default CommentTable;
