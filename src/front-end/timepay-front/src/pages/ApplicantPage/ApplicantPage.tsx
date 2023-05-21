import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { apiRequest } from '../../api/request';
import { API_URL } from '../../api/urls';
import useFontSize from '../../hooks/useFontSize';
import { cssBtnSpace, cssMyInfoStyle } from './ApplicantPage.style';
import { Button, Modal, Space, Table, Typography } from 'antd';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { ExclamationCircleFilled } from '@ant-design/icons';
import ApplicantModal from '../../components/ApplicantModal';
import { IApplicant } from '../../api/interfaces/IApplicant';

const ApplicantPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  const [isOpenRegisterModal, setIsOpenRegisterModal] = useState(false);

  const [image, setImage]: any = useState();
  const [nickName, setNickName]: any = useState();
  const [personalNum, setPersonalNum]: any = useState();

  const { scaleValue } = useFontSize();
  const { Text } = Typography;
  const { confirm } = Modal;

  const handleOnCancelModal = useCallback(() => {
    setIsOpenRegisterModal(false);
  }, []);

  const isOpenModal = () => {
    setIsOpenRegisterModal(true);
  };

  const showConfirm = () => {
    confirm({
      title: '대리인 삭제',
      icon: <ExclamationCircleFilled />,
      content: '대리인 삭제시 대리 작성 도움을 받을 수 없습니다',
      okText: '삭제',
      okType: 'primary',
      okButtonProps: { style: { backgroundColor: COMMON_COLOR.MAIN2 } },
      onOk() {
        console.log('OK');
      },
      cancelText: '취소',

      onCancel() {
        console.log('Cancel');
      },
    });
  };

  useEffect(() => {
    apiRequest
      .get(API_URL.USER_INFO_GET)
      .then((res) => {
        setImage(
          res.data.body.image_url ||
            'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
        );
        setNickName(res.data.body.nick_name);
        setPersonalNum(res.data.body.id);
      })
      .catch((error) => {
        console.error('Error sending GET request:', error);
      });
  }, []);

  useEffect(() => {
    setHeaderTitle('신청인 관리');
  }, [setHeaderTitle]);

  const onClickSwitch = useCallback((appliNumber: number) => {
    console.log(appliNumber);
  }, []);

  const agent = '미지정'; //api 받아오면 조건문 추가
  //신청자 보여주는것도 테이블
  //신청자 삭제는 radio 테이블
  //받은 신청 목록 보여주는것도 테이블 쓰면 될듯 (column Header off 해서)

  const dataSource: IApplicant[] = [];
  for (let i = 0; i < 100; i++) {
    dataSource.push({
      appliName: '길동홍',
      appliUid: i,
    });
  }

  const columns = [
    {
      title: 'appliUid',
      dataIndex: 'appliUid',
      key: 'appliUid',
      width: 10,
    },
    {
      title: 'appliName',
      dataIndex: 'appliName',
      key: 'appliName',
      width: 10,
    },
    {
      title: '계정전환',
      dataIndex: 'switch',
      width: 10,
      render: (_: any, record: IApplicant) => (
        <Button
          type="primary"
          ghost
          className="switchBtn"
          style={{ color: COMMON_COLOR.MAIN1 }}
          onClick={() => onClickSwitch(record.appliUid)}
        >
          계정전환
        </Button>
      ),
    },
  ];

  return (
    <div className="applicantContent">
      <div css={cssMyInfoStyle(scaleValue)}>
        <div className="MyTopBox">
          <div className="MyImageWrap">
            <img src={image} className="MyProfileImage" alt="내 프로필" />
          </div>
          <div className="space"></div>

          <div className="MyNameWrap">
            <div className="MyName">{nickName}</div>
            <div className="MyPersonalNum"> {'#' + personalNum}</div>
          </div>
        </div>
        <div className="agentBox">
          <Text className="text">나의 신청자</Text>
          <Table
            dataSource={dataSource}
            columns={columns}
            showHeader={false}
            pagination={false}
            scroll={{ y: 240 }}
          />

          <div className="space-align-container" css={cssBtnSpace(scaleValue)}>
            <div className="space-align-block">
              <Space align="center" size={10}>
                <Button
                  className="agentRegister"
                  type="primary"
                  style={{ background: COMMON_COLOR.MAIN1, width: 140 }}
                  onClick={isOpenModal}
                >
                  신청자 선택 삭제
                </Button>
                <Button
                  className="agentDelete"
                  type="primary"
                  style={{ background: COMMON_COLOR.MAIN2, width: 140 }}
                  danger
                  onClick={showConfirm}
                >
                  받은 신청 목록
                </Button>
              </Space>
            </div>
          </div>
        </div>
      </div>
      <ApplicantModal
        isOpen={isOpenRegisterModal}
        onCancel={handleOnCancelModal}
      />
    </div>
  );
};

export default ApplicantPage;
