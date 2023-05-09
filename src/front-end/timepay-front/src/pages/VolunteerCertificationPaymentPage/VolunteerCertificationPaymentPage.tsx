import { LinkOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Table } from 'antd';
import { useEffect, useMemo } from 'react';
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

const VolunteerCertificationPaymentPage = () => {
  const navigate = useNavigate();
  let { boardId } = useParams();

  const setHeaderTitle = useSetRecoilState(headerTitleState);

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

  const dummyBoard: IVolunteerBoardInfo = useMemo(() => {
    return {
      title: 'XX 골목 연탄 배달 봉사',
      location: '정릉 3동',
      startTime: '2023-05-09T13:00:00',
      endTime: '2023-05-09T16:00:00',
      participateNum: 5,
    };
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
        * 인증서 지급 시 활동자에게 푸시 알림이 가며 활동자가 조회 가능해집니다.
      </div>
    );
  }, [dummyBoard]);

  const columns = useMemo(() => {
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
              <Button>{record.published ? '재지급' : '지급'}</Button>
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
  }, []);

  const data: IVolunteerUser[] = [
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

  return (
    <div css={cssVolunteerCertificationPaymentPageStyle}>
      <Table
        bordered
        title={() => boardInfo}
        scroll={{ x: 330 }}
        columns={columns}
        dataSource={data}
        rowKey="userId"
      />
    </div>
  );
};

export default VolunteerCertificationPaymentPage;
