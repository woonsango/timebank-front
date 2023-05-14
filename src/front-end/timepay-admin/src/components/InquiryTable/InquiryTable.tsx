import { Button, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import { cssInquiryTableStyle } from './InquiryTable.styles';
import { IInquiry } from '../../api/interfaces/IInquiry';
import InquiryDetailModal from '../InquiryDetailModal';
import { useGetInquiries } from '../../api/hooks/inquiry';

const InquiryTable = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [currentQnA, setCurrentQnA] = useState<IInquiry>();
  const { data, isLoading } = useGetInquiries();

  const dataSource = data?.data.content || [];

  const handleOnShowDetailQnA = useCallback((report: IInquiry) => {
    setCurrentQnA(report);
    setIsOpen(true);
  }, []);

  const handleOnCloseDetailQnA = useCallback(() => {
    setCurrentQnA(undefined);
    setIsOpen(false);
  }, []);

  const dummyDataSource: IInquiry[] = [];
  for (let i = 0; i < 100; i++) {
    dummyDataSource.push({
      inquiryId: i,
      title: `문의 제목 ${i + 1}`,
      createdAt: `2023-12-34 12:34`,
      state: i % 2 ? '답변대기' : '답변완료',
      category: `카테고리 ${i}`,
      content: `문의 내용 ${i + 1}`,
      writer: `${i}작성자 이름`,
    });
  }

  // @ts-ignore
  const columns: ColumnsType<IInquiry> = useMemo(() => {
    return [
      {
        title: '문의 번호',
        key: 'inquiryId',
        dataIndex: 'inquiryId',
        width: 10,
        sorter: (a: IInquiry, b: IInquiry) => a.inquiryId - b.inquiryId,
      },
      {
        title: '문의 상태',
        key: 'state',
        dataIndex: 'state',
        width: 30,
        align: 'center',
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
        sorter: (a: IInquiry, b: IInquiry) =>
          dayjs(a.createdAt).isAfter(dayjs(b.createdAt)),
      },

      {
        title: '문의 제목',
        key: 'title',
        dataIndex: 'title',
        width: 100,
        align: 'center',
        render: (_: string, record: IInquiry) => (
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
        css={cssInquiryTableStyle}
        columns={columns}
        scroll={{ x: 1000 }}
        dataSource={dataSource}
        rowKey="inquiryId"
        loading={isLoading}
      />
      <InquiryDetailModal
        isOpen={isOpen}
        onCancel={handleOnCloseDetailQnA}
        Inquiry={currentQnA}
      />
    </>
  );
};

export default InquiryTable;
