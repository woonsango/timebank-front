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
      inquiryId: i,
      title: `문의 제목 ${i + 1}`,
      createdAt: `2023-12-34 12:34`,
      status: i % 2 ? '답변대기' : '답변완료',
      category: `카테고리 ${i}`,
      content: `문의 내용 ${i + 1}`,
      writer: `${i}작성자 이름`,
    });
  }

  // @ts-ignore
  const columns: ColumnsType<IQna> = useMemo(() => {
    return [
      {
        title: '문의 번호',
        key: 'inquiryId',
        dataIndex: 'inquiryId',
        width: 10,
        sorter: (a: IQna, b: IQna) => a.inquiryId - b.inquiryId,
      },
      {
        title: '문의 상태',
        key: 'status',
        dataIndex: 'status',
        width: 30,
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
        width: 30,
        align: 'center',
      },
      {
        title: '작성일시',
        key: 'createdAt',
        dataIndex: 'createdAt',
        width: 30,
        align: 'center',
        sorter: (a: IQna, b: IQna) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
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
        title: '문의 작성자 이름',
        key: 'writer',
        dataIndex: 'writer',
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
        scroll={{ x: 1000 }}
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
