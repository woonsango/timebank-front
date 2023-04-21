import { Modal, Button, Card, Table } from 'antd';
import { ColumnsType } from 'antd/es/table';
import { useState } from 'react';

const DetailModal = () => {
  /*회원 활동 목록 모달 설정 */
  const [modalDetail, setModalDetail] = useState(false);

  const showModalDetail = () => {
    setModalDetail(true);
  };

  const handleOkDetail = () => {
    setModalDetail(false);
  };

  const handleCancelDetail = () => {
    setModalDetail(false);
  };

  /*회원 활동 목록 탭 설정 */
  const tabList = [
    {
      key: '게시글',
      tab: '게시글',
    },
    {
      key: '댓글',
      tab: '댓글',
    },
    {
      key: '신고받은내역',
      tab: '신고받은 내역',
    },
    {
      key: '신고한내역',
      tab: '신고한 내역',
    },
  ];

  /*회원 활동 목록 게시글 탭 Table Data 설정 */
  interface DataTypeUserPost {
    key: React.Key;
    postNum: number;
    postTitle: string;
    writeDate: string;
    type: string;
    curState: string;
    setActivityTime: string;
    realActivityTime: string;
  }

  const columnsUserPost: ColumnsType<DataTypeUserPost> = [
    {
      title: '게시글 번호',
      dataIndex: 'postNum',
      align: 'center',
    },
    {
      title: '제목',
      dataIndex: 'postTitle',
      align: 'center',
    },
    {
      title: '작성 날짜',

      dataIndex: 'writeDate',
      align: 'center',
    },
    {
      title: '유형',
      dataIndex: 'type',
      align: 'center',
    },
    {
      title: '현재 상태',
      dataIndex: 'curState',
      align: 'center',
    },
    {
      title: '설정 활동 시간',
      dataIndex: 'setActivityTime',
      align: 'center',
    },
    {
      title: '실제 활동 시간',
      dataIndex: 'realActivityTime',
      align: 'center',
    },
  ];

  const dataUserPost: DataTypeUserPost[] = [];
  for (let i = 1; i < 12; i++) {
    dataUserPost.push({
      key: i,
      postNum: i,
      postTitle: `제목 ${i}`,
      writeDate: `2023-01-0${i}`,
      type: `도움 주기`,
      curState: `활동 완료`,
      setActivityTime: `0${i}:0${i * 3}~0${i + 2}:0${i}`,
      realActivityTime: `0${i}:0${i * 4}~0${i + 3}:0${i}`,
    });
  }

  /*회원 활동 목록 댓글 탭 Table Data 설정 */
  interface DataTypeUserComment {
    key: React.Key;
    commentNum: number;
    writeDate: string;
    apply: string;
    decide: string;
    content: string;
  }

  const columnsUserComment: ColumnsType<DataTypeUserComment> = [
    {
      title: '댓글 번호',
      dataIndex: 'commentNum',
      align: 'center',
    },
    {
      title: '작성 날짜',
      dataIndex: 'writeDate',
      align: 'center',
    },

    {
      title: '지원 여부',
      dataIndex: 'apply',
      align: 'center',
    },

    {
      title: '확정 여부',
      dataIndex: 'decide',
      align: 'center',
    },

    {
      title: '내용',
      dataIndex: 'content',
      align: 'center',
    },
  ];

  const dataUserComment: DataTypeUserComment[] = [];
  for (let i = 1; i < 11; i++) {
    dataUserComment.push({
      key: i,
      commentNum: i,
      writeDate: `2023-03-0${i}`,
      apply: `O`,
      decide: `X`,
      content: `어떤 언어로 수업하시나요?`,
    });
  }

  /*회원 활동 목록 신고 받은 내역 탭 Table Data 설정 */
  interface DataTypeUserReceiveReport {
    key: React.Key;
    reportNum: number;
    nickNameReporter: string;
    reason: string;
    postNum: number;
    commentNum: number;
  }

  const columnsUserReceiveReport: ColumnsType<DataTypeUserReceiveReport> = [
    {
      title: '신고 번호',
      dataIndex: 'reportNum',
      align: 'center',
    },
    {
      title: '신고자',
      dataIndex: 'nickNameReporter',
      align: 'center',
    },
    {
      title: '신고 사유',
      dataIndex: 'reason',
      align: 'center',
    },
    {
      title: '게시글 번호',
      dataIndex: 'postNum',
      align: 'center',
    },
    {
      title: '댓글 번호',
      dataIndex: 'commentNum',
      align: 'center',
    },
  ];

  const dataUserReceiveReport: DataTypeUserReceiveReport[] = [];
  for (let i = 1; i < 11; i++) {
    dataUserReceiveReport.push({
      key: i,
      reportNum: i,
      nickNameReporter: `reporter ${i}`,
      reason: `신고 사유 ${i}`,
      postNum: i * 1231,
      commentNum: i * 362,
    });
  }

  /*회원 활동 목록 신고한 내역 탭 Table Data 설정 */
  interface DataTypeUserSendReport {
    key: React.Key;
    reportNum: number;
    nickNameReceiver: string;
    reason: string;
    postNum: number;
    commentNum: number;
  }

  const columnsUserSendReport: ColumnsType<DataTypeUserSendReport> = [
    {
      title: '신고 번호',
      dataIndex: 'reportNum',
      align: 'center',
    },
    {
      title: '신고 받은 사람',
      dataIndex: 'nickNameReceiver',
      align: 'center',
    },
    {
      title: '신고 사유',
      dataIndex: 'reason',
      align: 'center',
    },
    {
      title: '게시글 번호',
      dataIndex: 'postNum',
      align: 'center',
    },
    {
      title: '댓글 번호',
      dataIndex: 'commentNum',
      align: 'center',
    },
  ];

  const dataUserSendReport: DataTypeUserSendReport[] = [];
  for (let i = 1; i < 11; i++) {
    dataUserSendReport.push({
      key: i,
      reportNum: i,
      nickNameReceiver: `receiver ${i}`,
      reason: `신고 사유 ${i}`,
      postNum: i * 1231,
      commentNum: i * 362,
    });
  }

  const contentList: Record<string, React.ReactNode> = {
    게시글: <Table columns={columnsUserPost} dataSource={dataUserPost} />,
    댓글: <Table columns={columnsUserComment} dataSource={dataUserComment} />,
    신고받은내역: (
      <Table
        columns={columnsUserReceiveReport}
        dataSource={dataUserReceiveReport}
      />
    ),
    신고한내역: (
      <Table columns={columnsUserSendReport} dataSource={dataUserSendReport} />
    ),
  };

  const [activeTabKey, setActiveTabKey] = useState<string>('');

  const onTabChange = (key: string) => {
    setActiveTabKey(key);
  };

  return (
    <div>
      <Button onClick={showModalDetail}>활동 보기</Button>
      <Modal
        title="활동 목록"
        open={modalDetail}
        onOk={handleOkDetail}
        onCancel={handleCancelDetail}
        okText="확인"
        cancelText="취소"
        width=""
      >
        <Card
          tabList={tabList}
          activeTabKey={activeTabKey}
          onTabChange={onTabChange}
        >
          {contentList[activeTabKey]}
        </Card>
      </Modal>
    </div>
  );
};

export default DetailModal;
