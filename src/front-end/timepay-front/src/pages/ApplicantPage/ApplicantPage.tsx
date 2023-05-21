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
import ApplicantReceivedModal from '../../components/ApplicantReceivedModal';
import { useGetApplicant } from '../../api/hooks/applicant';

const ApplicantPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  const [isOpenRegisterModal, setIsOpenRegisterModal] = useState(false);
  const [isOpenReceivedModal, setIsOpenReceivedModal] = useState(false);

  const [image, setImage]: any = useState();
  const [nickName, setNickName]: any = useState();
  const [personalNum, setPersonalNum]: any = useState();
  const { data } = useGetApplicant();

  const { scaleValue } = useFontSize();
  const { Text } = Typography;

  const handleOnCancelModal = useCallback(() => {
    setIsOpenRegisterModal(false);
    setIsOpenReceivedModal(false);
  }, []);

  const openRegisterModal = () => {
    setIsOpenRegisterModal(true);
  };
  const openReceivedModal = () => {
    setIsOpenReceivedModal(true);
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

  // const dataSource: IApplicant[] = [];
  // for (let i = 0; i < 100; i++) {
  //   dataSource.push({
  //     appliName: '길동홍',
  //     appliUid: i,
  //   });
  // }
  console.log(data?.data.applicant);
  const dataSource = data?.data.applicant
    ? data?.data.applicant.map((data) => ({
        ...data,
        displayUid: `#${data.appliUid}`,
      }))
    : [];

  const columns = [
    {
      title: 'appliName',
      dataIndex: 'appliName',
      key: 'appliName',
      width: 7,
    },
    {
      title: 'displayUid',
      dataIndex: 'displayUid',
      key: 'displayUid',
      width: 7,
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
            locale={{ emptyText: '등록된 신청자가 없습니다.' }}
          />

          <div className="space-align-container" css={cssBtnSpace(scaleValue)}>
            <div className="space-align-block">
              <Space align="center" size={10}>
                <Button
                  className="agentRegister"
                  type="primary"
                  style={{ background: COMMON_COLOR.MAIN1, width: 140 }}
                  onClick={openRegisterModal}
                >
                  신청자 선택 삭제
                </Button>
                <Button
                  className="agentDelete"
                  type="primary"
                  style={{ background: COMMON_COLOR.MAIN2, width: 140 }}
                  danger
                  onClick={openReceivedModal}
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
        applicants={dataSource}
      />
      <ApplicantReceivedModal
        isOpen={isOpenReceivedModal}
        onCancel={handleOnCancelModal}
      />
    </div>
  );
};

export default ApplicantPage;
