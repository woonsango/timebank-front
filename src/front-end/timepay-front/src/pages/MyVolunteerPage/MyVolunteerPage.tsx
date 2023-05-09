import { LinkOutlined } from '@ant-design/icons';
import { Button, Table } from 'antd';
import { useCallback, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { IVolunteerBoard } from '../../api/interfaces/IVolunteer';
import { headerTitleState } from '../../states/uiState';
import { cssMyVolunteerPageStyle } from './MyVolunteerPage.styles';

const MyVolunteerPage = () => {
  const navigate = useNavigate();
  const setHeaderTitle = useSetRecoilState(headerTitleState);

  useEffect(() => {
    setHeaderTitle('봉사활동 기록');
  }, [setHeaderTitle]);

  const handleOnClickTitle = useCallback(
    (record: IVolunteerBoard) => {
      navigate(`/post/${record.boardId}`, {
        state: {
          id: record.boardId,
          type: '도움받기',
          title: record.title,
          content: '봉사하고 봉사시간 받아요',
          createdAt: '',
          status: '',
          category: '',
          pay: 0,
          startTime: '',
          endTime: '',
          region: '',
          attachment: '',
          user: record.managerName,
        },
      });
    },
    [navigate],
  );

  const columns = useMemo(() => {
    return [
      {
        title: '번호',
        dataIndex: 'index',
        render: (_text: number, _record: IVolunteerBoard, index: number) => {
          ++index;
          return index;
        },
        width: 60,
      },
      {
        title: '활동명',
        dataIndex: 'title',
        width: 200,
        render: (title: string, record: IVolunteerBoard) => {
          return (
            <>
              <Button
                type="link"
                onClick={() => {
                  handleOnClickTitle(record);
                }}
              >
                <LinkOutlined />
                {title}
              </Button>
              <br />
              <div className="agency-info">
                기관명 : <span>{record.organizationName}</span>
                <br />
                담당자 : <span>{record.managerName}</span>
                <br />
                담당자 : <span>{record.managerPhone}</span>
              </div>
            </>
          );
        },
      },
      {
        title: '시간',
        dataIndex: 'volunteerTime',
        width: 70,
      },
      {
        title: '인증서',
        dataIndex: 'certificationUrl',
        width: 100,
        render: (certificationUrl?: string) =>
          certificationUrl ? (
            <Button
              className="certification-link"
              type="link"
              href={certificationUrl}
              target="_blank"
              download
            >
              <LinkOutlined />
              인증서 보기
            </Button>
          ) : (
            '미발급'
          ),
      },
    ];
  }, [handleOnClickTitle]);

  const data: IVolunteerBoard[] = [
    {
      boardId: 1,
      title: '사랑의 연탄 봉사',
      organizationName: '하연 기관',
      managerName: '김하연',
      managerPhone: '01023860370',
      volunteerTime: 6,
      state: true,
      certificationUrl:
        'https://storage.googleapis.com/timepay-79a6f.appspot.com/31f44eb3-1210-4a96-b1dd-a7526069fe7e_certificate.pdf',
    },
    {
      boardId: 2,
      title: '도시락 배달 봉사',
      organizationName: '하연 기관',
      managerName: '김하연',
      managerPhone: '01023860370',
      volunteerTime: 12,
      state: false,
      certificationUrl: null,
    },
    {
      boardId: 3,
      title: '벽화 그리기 봉사',
      organizationName: '커뮤니티상생센터',
      managerName: '매니저이름',
      managerPhone: '010-1111-1111',
      volunteerTime: 6,
      state: true,
      certificationUrl:
        'https://storage.googleapis.com/timepay-79a6f.appspot.com/31f44eb3-1210-4a96-b1dd-a7526069fe7e_certificate.pdf',
    },
  ];

  return (
    <div css={cssMyVolunteerPageStyle}>
      <Table
        bordered
        title={() => (
          <>
            <div className="volunteer-overview">
              <span className="title">지급받은 총 봉사시간</span>
              <span>총 13시간</span>
            </div>
            <div className="volunteer-extra">
              * 타임페이 서비스를 통한 봉사활동만 표시됩니다.
            </div>
          </>
        )}
        columns={columns}
        dataSource={data}
        rowKey="boardId"
      />
    </div>
  );
};

export default MyVolunteerPage;
