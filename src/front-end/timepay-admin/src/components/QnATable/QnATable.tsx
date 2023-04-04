import { Button, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import { cssQnATableStyle } from './QnATable.styles';
import { IQna } from '../../api/interfaces/IQna';
import QnADetailModal from '../QnADetailModal';

const QnATable = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [currentQnA, setCurrentQnA] = useState<IQna>();

  const handleOnShowDetailQnA = useCallback((report: IQna) => {
    setCurrentQnA(report);
    setIsOpen(true);
  }, []);

  const handleOnCloseDetailQnA = useCallback(() => {
    setCurrentQnA(undefined);
    setIsOpen(false);
  }, []);

  const dummyDataSource: IQna[] = [];
  for (let i = 0; i < 100; i++) {
    dummyDataSource.push({
      qnaId: i,
      title: `문의 제목 ${i + 1}`,
      createdAt: `2023-12-34 12:34`,
      status: i % 2 ? '답변대기' : '답변완료',
      category: `카테고리 ${i}`,
      content: `문의 내용 ${i + 1}`,
      user: `${i}작성자 id`,
    });
  }

  // @ts-ignore
  const columns: ColumnsType<IQna> = useMemo(() => {
    return [
      {
        title: '문의 번호',
        key: 'qnaId',
        dataIndex: 'qnaId',
        width: 90,
        sorter: (a: IQna, b: IQna) => a.qnaId - b.qnaId,
      },

      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 140,
        align: 'center',
        sorter: (a: IQna, b: IQna) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },
      {
        title: '문의 상태',
        key: 'status',
        dataIndex: 'status',
        width: 100,
        align: 'center',
        filters: [
          { text: '답변대기', value: '답변대기' },
          { text: '답변완료', value: '답변완료' },
        ],
        onFilter: (value: string, record: IQna) =>
          record.status.indexOf(value) === 0,
      },
      {
        title: '카테고리',
        key: 'category',
        dataIndex: 'category',
        width: 100,
        align: 'center',
      },
      {
        title: '문의 제목',
        key: 'title',
        dataIndex: 'title',
        width: 100,
        align: 'center',
        render: (_: string, record: IQna) => (
          <Button type="link" onClick={() => handleOnShowDetailQnA(record)}>
            더보기
          </Button>
        ),
      },
      {
        title: '문의 내용',
        key: 'content',
        dataIndex: 'content',
        width: 150,
        align: 'center',
        render: (_: string, record: IQna) => (
          <Button type="link" onClick={() => handleOnShowDetailQnA(record)}>
            더보기
          </Button>
        ),
      },
      {
        title: '문의 작성자 회원번호',
        key: 'user',
        dataIndex: 'user',
        width: 100,
        align: 'center',
      },
    ];
  }, [handleOnShowDetailQnA]);

  return (
    <>
      <Table
        css={cssQnATableStyle}
        columns={columns}
        scroll={{ x: 1500 }}
        dataSource={dummyDataSource}
        rowKey="postId"
      />
      <QnADetailModal
        isOpen={isOpen}
        onCancel={handleOnCloseDetailQnA}
        qna={currentQnA}
      />
    </>
  );
};

export default QnATable;
