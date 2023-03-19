import { Button, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useMemo } from 'react';
import { IPost, IPostState, IPostType } from '../../api/interfaces/IPost';
import dayjs from 'dayjs';
import { cssPostTableStyle } from './PostTable.styles';

const PostTable = () => {
  const postTypes: IPostType[] = ['도움요청', '도움주기', '자유', '후기'];
  const postStatus: IPostState[] = [
    '매칭중',
    '매칭완료',
    '활동시작',
    '활동완료',
    '활동취소',
    '활동지연',
  ];
  const dummyDataSource: IPost[] = [];

  for (let i = 0; i < 100; i++) {
    dummyDataSource.push({
      postId: i,
      type: postTypes[i % 4],
      status: postStatus[i % 6],
      category: '카텍',
      tag: ['i:', i.toString()],
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
      pay: i * 100,
      startTime: '2023-03-19 11:00:00',
      endTime: `2023-03-19 ${((12 + i) % 12).toString()}:00:00`,
      createdAt: `2023-02-18 ${(i % 12).toString()}:00:00`,
      title: `게시글 제목 ${i}`,
      content: `게시글 내용 ${i}`,
      region: '정릉3동',
      originPostId: i % 20 === 0 ? i : undefined,
    });
  }
  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: IPost[]) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        'selectedRows: ',
        selectedRows,
      );
    },
  };

  // @ts-ignore
  const columns: ColumnsType<IPost> = useMemo(() => {
    return [
      {
        title: '게시글 번호',
        key: 'postId',
        dataIndex: 'postId',
        width: 90,
        sorter: (a: IPost, b: IPost) => a.postId - b.postId,
      },
      {
        title: '유형',
        key: 'type',
        dataIndex: 'type',
        width: 100,
        align: 'center',
        filters: [
          { text: '도움요청', value: '도움요청' },
          { text: '도움주기', value: '도움주기' },
          { text: '자유', value: '자유' },
          { text: '후기', value: '후기' },
        ],
        onFilter: (value: string, record) => record.type.indexOf(value) === 0,
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
        onFilter: (value: string, record) => record.status.indexOf(value) === 0,
      },
      {
        title: '카테고리',
        key: 'category',
        dataIndex: 'category',
        align: 'center',
        width: 120,
        // 카테고리 범위 정의 후 필터 추가
        // filters: [
        //     { text: '생활', value: '생활' },
        //   ],
        //   onFilter: (value: string, record) => record.category.indexOf(value) === 0,
      },
      {
        title: '태그',
        key: 'tag',
        dataIndex: 'tag',
        width: 120,
        align: 'center',
        render: (_: string) => <Button type="link">더보기</Button>,
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
        title: '수정일시',
        key: 'updatedAt',
        dataIndex: 'updatedAt',
        width: 140,
        align: 'center',
        sorter: (a: IPost, b: IPost) =>
          dayjs(a.updatedAt).isAfter(dayjs(b.updatedAt)),
      },
      {
        title: '활동시간',
        key: 'startTime',
        dataIndex: 'startTime',
        width: 150,
        align: 'center',
        render: (startTime: string, record: IPost) => (
          <div>
            {startTime}
            <br />~<br />
            {record.endTime}
          </div>
        ),
      },
      {
        title: '타임페이 거래량',
        key: 'pay',
        dataIndex: 'pay',
        width: 100,
        align: 'center',
        sorter: (a: IPost, b: IPost) => b.pay - a.pay,
      },
      {
        title: '제목',
        key: 'title',
        dataIndex: 'title',
        width: 150,
        align: 'center',

        render: (_: string) => <Button type="link">더보기</Button>,
      },
      {
        title: '본문 내용',
        key: 'content',
        dataIndex: 'content',
        width: 150,
        align: 'center',
        render: (_: string) => <Button type="link">더보기</Button>,
      },
      {
        title: '본문 첨부사진',
        key: 'attachment',
        dataIndex: 'attachment',
        width: 100,
        align: 'center',
        render: (_: string) => <Button type="link">더보기</Button>,
      },
      {
        title: '원본 게시글 번호',
        key: 'originPostId',
        dataIndex: 'originPostId',
        width: 100,
        sorter: (a: IPost, b: IPost) =>
          (b.originPostId || 0) - (a.originPostId || 0),
      },
    ];
  }, []);
  return (
    <Table
      css={cssPostTableStyle}
      rowSelection={rowSelection}
      columns={columns}
      scroll={{ x: 1500 }}
      dataSource={dummyDataSource}
      rowKey="postId"
    />
  );
};

export default PostTable;
