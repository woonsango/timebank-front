import { Button, Table } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { useCallback, useMemo, useState } from 'react';
import dayjs from 'dayjs';
import {
  cssReportTableRowCountStyle,
  cssReportTableStyle,
} from './ReportTable.styles';
import { IReport } from '../../api/interfaces/IReport';
import ReportDetailModal from '../ReportDetailModal';
import { useGetReports } from '../../api/hooks/report';

const ReportTable = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [currentReport, setCurrentReport] = useState<IReport>();
  const { data, isLoading } = useGetReports();
  const dataSource = data?.data.content || [];

  const handleOnShowDetailReport = useCallback((report: IReport) => {
    setCurrentReport(report);
    setIsOpen(true);
  }, []);

  const handleOnCloseDetailReport = useCallback(() => {
    setCurrentReport(undefined);
    setIsOpen(false);
  }, []);

  // const dummyDataSource: IReport[] = [];
  // for (let i = 0; i < 100; i++) {
  //   dummyDataSource.push({
  //     reportId: i,
  //     reporterId: i,
  //     reporterName: `홍길동${i}`,
  //     type: '댓글',
  //     reason: `신고내용${i}`,
  //     targetId: i,
  //     reportedAt: '2023-12-23 12:23',
  //     targetReportId: i,
  //     process: 'process',
  //   });
  // }

  // @ts-ignore
  const columns: ColumnsType<ReportItem> = useMemo(() => {
    return [
      {
        title: '신고 번호',
        key: 'reportId',
        dataIndex: 'reportId',
        width: 90,
        sorter: (a: IReport, b: IReport) => a.reportId - b.reportId,
      },
      {
        title: '신고자 회원번호',
        key: 'reporterId',
        dataIndex: 'reporterId',
        width: 100,
        align: 'center',
      },
      {
        title: '신고자 이름',
        key: 'reporterName',
        dataIndex: 'reporterName',
        width: 100,
        align: 'center',
      },
      {
        title: '신고글 타입',
        key: 'type',
        dataIndex: 'type',
        width: 100,
        align: 'center',
        filters: [
          { text: '댓글', value: '댓글' },
          { text: '게시글', value: '게시글' },
        ],
        onFilter: (value: string, record: IReport) =>
          record.type.indexOf(value) === 0,
      },
      {
        title: '신고 사유',
        key: 'reason',
        dataIndex: 'reason',
        width: 150,
        align: 'center',
        render: (_: string, record: IReport) => (
          <Button type="link" onClick={() => handleOnShowDetailReport(record)}>
            더보기
          </Button>
        ),
      },
      {
        title: '신고대상 글번호',
        key: 'targetId',
        dataIndex: 'targetId',
        width: 100,
        align: 'center',
      },
      {
        title: '신고일시',
        key: 'reportedAt',
        dataIndex: 'reportedAt',
        width: 140,
        align: 'center',
        sorter: (a: IReport, b: IReport) =>
          dayjs(a.reportedAt).isAfter(dayjs(b.reportedAt)),
      },
      {
        title: '신고대상 회원번호',
        key: 'targetReportId',
        dataIndex: 'targetReportId',
        width: 100,
        align: 'center',
      },
      {
        title: '신고처리여부',
        key: 'process',
        dataIndex: 'reportStatus',
        width: 100,
        align: 'center',
        filters: [
          { text: 'Y', value: 'Y' },
          { text: 'N', value: 'N' },
        ],
        onFilter: (value: string, record: IReport) =>
          record.type.indexOf(value) === 0,
      },
    ];
  }, [handleOnShowDetailReport]);

  return (
    <>
      <Table
        css={cssReportTableStyle}
        columns={columns}
        scroll={{ x: 1500 }}
        dataSource={dataSource}
        rowKey="postId"
      />
      <ReportDetailModal
        isOpen={isOpen}
        onCancel={handleOnCloseDetailReport}
        post={currentReport}
      />
    </>
  );
};

export default ReportTable;
