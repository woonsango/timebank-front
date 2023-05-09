import {
  InboxOutlined,
  LinkOutlined,
  UploadOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { Button, Modal, Table, Upload } from 'antd';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import {
  IVolunteerBoardInfo,
  IVolunteerUser,
} from '../../api/interfaces/IVolunteer';
import { headerTitleState } from '../../states/uiState';
import {
  cssVolunteerCertificationPaymentPageStyle,
  cssVolunteerOverviewStyle,
  cssVolunteerUserInfoStyle,
} from './VolunteerCertificationPaymentPage.styles';
import { ReactComponent as RegionPin } from '../../assets/images/icons/region-pin.svg';
import { ReactComponent as Clock } from '../../assets/images/icons/clock.svg';
import { PATH } from '../../utils/paths';
import dayjs from 'dayjs';
import { isMobileWidth } from '../../utils/device';

const VolunteerCertificationPaymentPage = () => {
  const navigate = useNavigate();
  let { boardId } = useParams();

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const [isShowUploadModal, setIsShowUploadModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState<IVolunteerUser>();

  const dummyBoard: IVolunteerBoardInfo = useMemo(() => {
    return {
      title: 'XX 골목 연탄 배달 봉사',
      location: '정릉 3동',
      startTime: '2023-05-09T13:00:00',
      endTime: '2023-05-09T16:00:00',
      participateNum: 5,
    };
  }, []);

  const dummyTableData: IVolunteerUser[] = [
    {
      userId: 2,
      userName: '서준원',
      userNickname: '준원이',
      email: 'test@gmail.com',
      phone: '010-1234-5678',
      certificationUrl:
        'https://storage.googleapis.com/timepay-79a6f.appspot.com/31f44eb3-1210-4a96-b1dd-a7526069fe7e_certificate.pdf',
      published: true,
    },
    {
      userId: 3,
      userName: '김준영',
      userNickname: '준영이',
      email: 'test@gmail.com',
      phone: '010-1234-5678',
      certificationUrl:
        'https://storage.googleapis.com/timepay-79a6f.appspot.com/31f44eb3-1210-4a96-b1dd-a7526069fe7e_certificate.pdf',
      published: true,
    },
    {
      userId: 4,
      userName: '김명환',
      userNickname: '명환이',
      email: 'test@gmail.com',
      phone: '010-1234-5678',
      certificationUrl: null,
      published: false,
    },
    {
      userId: 5,
      userName: '김용훈',
      userNickname: '용훈이',
      email: 'test@gmail.com',
      phone: '010-1234-5678',
      certificationUrl: null,
      published: false,
    },
  ];

  const handleResizeWindowWidth = useCallback(() => {
    setWindowWidth(window.innerWidth);
  }, []);

  const handleOnCancelModal = useCallback(() => {
    setIsShowUploadModal(false);
  }, []);

  const boardInfo = useMemo(() => {
    return (
      <div css={cssVolunteerOverviewStyle}>
        <span className="header">봉사활동 정보</span>
        <span className="title">{dummyBoard.title}</span>
        <div className="region">
          <RegionPin /> {dummyBoard.location}
        </div>
        <div className="time">
          <Clock /> {dummyBoard.startTime.replaceAll('T', ' ').slice(0, 16)} ~{' '}
          {dummyBoard.endTime.replaceAll('T', ' ').slice(0, 16)} (총{' '}
          {dayjs(dummyBoard.endTime, 'YYYY-MM-DDTHH:mm:ss').diff(
            dayjs(dummyBoard.startTime, 'YYYY-MM-DDTHH:mm:ss'),
            'hours',
          )}{' '}
          시간)
        </div>
        <div className="participateNum">
          <UserOutlined /> {dummyBoard.participateNum} 명
        </div>
        * 아래 활동자들에게 봉사활동 시간을 지급한 후 인증서를 등록해주세요.{' '}
        <br />* 인증서 등록 시 활동자에게 푸시 알림이 가며 활동자가 조회
        가능해집니다.
      </div>
    );
  }, [dummyBoard]);

  const columns = useMemo(() => {
    if (isMobileWidth(windowWidth))
      return [
        {
          title: '회원번호',
          dataIndex: 'userId',
          width: 60,
        },
        {
          title: '회원정보',
          dataIndex: 'userName',
          width: 180,
          render: (userName: string, record: IVolunteerUser) => {
            return (
              <div css={cssVolunteerUserInfoStyle}>
                <span className="name">{userName}</span>
                <div>
                  닉네임 : <span>{record.userNickname}</span>
                </div>
                <div>
                  <span>{record.email}</span>
                </div>
                <div>
                  <span>{record.phone}</span>
                </div>
              </div>
            );
          },
        },
        {
          title: '인증서',
          dataIndex: 'certificationUrl',
          width: 90,
          render: (certificationUrl: string, record: IVolunteerUser) => {
            return (
              <>
                <Button
                  icon={<UploadOutlined />}
                  onClick={() => {
                    setIsShowUploadModal(true);
                    setSelectedUser(record);
                  }}
                >
                  {record.published ? '재등록' : '등록'}
                </Button>{' '}
                {record.published && (
                  <Button type="link" href={certificationUrl} target="_blank">
                    <LinkOutlined /> 보기
                  </Button>
                )}
              </>
            );
          },
        },
      ];
    else {
      return [
        {
          title: '회원번호',
          dataIndex: 'userId',
          width: 60,
        },
        {
          title: '이름',
          dataIndex: 'userName',
          width: 100,
        },
        {
          title: '닉네임',
          dataIndex: 'userNickname',
          width: 130,
        },
        {
          title: '이메일',
          dataIndex: 'email',
          width: 130,
        },
        {
          title: '전화번호',
          dataIndex: 'phone',
          width: 130,
        },
        {
          title: '등록된 인증서',
          dataIndex: 'certificationUrl',
          width: 80,
          render: (certificationUrl: string, record: IVolunteerUser) => {
            return (
              <>
                {record.published ? (
                  <Button type="link" href={certificationUrl} target="_blank">
                    <LinkOutlined /> 보기
                  </Button>
                ) : (
                  '-'
                )}
              </>
            );
          },
        },
        {
          title: '인증서 등록',
          dataIndex: 'published',
          width: 80,
          render: (published: boolean, record: IVolunteerUser) => {
            return (
              <Button
                icon={<UploadOutlined />}
                onClick={() => {
                  setIsShowUploadModal(true);
                  setSelectedUser(record);
                }}
              >
                {published ? '재등록' : '등록'}
              </Button>
            );
          },
        },
      ];
    }
  }, [windowWidth]);

  const footer = useMemo(() => {
    return (
      <>
        <Button onClick={handleOnCancelModal}>취소</Button>
        <Button type="primary">
          {selectedUser?.published ? '재등록' : '등록'}
        </Button>
      </>
    );
  }, [handleOnCancelModal, selectedUser]);

  useEffect(() => {
    setHeaderTitle('봉사활동 인증서 지급');
  }, [setHeaderTitle]);

  useEffect(() => {
    if (boardId && isNaN(Number(boardId))) {
      // boardId가 유효하지 않은 값일 떄 처리
      // 추후에 404페이지로 연결 예정
      navigate(PATH.HOME);
    }
  }, [boardId, navigate]);
  useEffect(() => {
    // 화면 너비 변경 시 테이블 컬럼 변화
    window.addEventListener('resize', handleResizeWindowWidth);
    return () => {
      window.removeEventListener('resize', handleResizeWindowWidth);
    };
  }, [handleResizeWindowWidth]);

  return (
    <div css={cssVolunteerCertificationPaymentPageStyle}>
      <Table
        bordered
        title={() => boardInfo}
        scroll={{ x: isMobileWidth(windowWidth) ? 300 : 1000 }}
        columns={columns}
        dataSource={dummyTableData}
        rowKey="userId"
      />
      <Modal
        open={isShowUploadModal}
        onCancel={handleOnCancelModal}
        title="인증서 등록"
        footer={footer}
      >
        <Upload.Dragger
          beforeUpload={() => false}
          multiple={false}
          maxCount={1}
        >
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">
            클릭이나 드래그 드롭을 통해서 인증서 파일을 올릴 수 있습니다.
          </p>
          <p className="ant-upload-hint">단 하나의 파일만 올릴 수 있습니다.</p>
        </Upload.Dragger>
      </Modal>
    </div>
  );
};

export default VolunteerCertificationPaymentPage;
