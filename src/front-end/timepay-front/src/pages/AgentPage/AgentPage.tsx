import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../api/request';
import { API_URL } from '../../api/urls';
import useFontSize from '../../hooks/useFontSize';
import { cssBtnSpace, cssMyInfoStyle } from './AgentPage.style';
import { Button, Space, Typography } from 'antd';
import { COMMON_COLOR } from '../../styles/constants/colors';
import SizeContext from 'antd/es/config-provider/SizeContext';

const AgentPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);

  const [image, setImage]: any = useState();
  const [nickName, setNickName]: any = useState();
  const [personalNum, setPersonalNum]: any = useState();

  const { scaleValue } = useFontSize();
  const { Text } = Typography;

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
    setHeaderTitle('대리인 관리');
  }, [setHeaderTitle]);

  const agent = '미지정'; //api 받아오면 조건문 추가

  return (
    <>
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
          <Text className="text">나의 대리인 :</Text>
          <Text className="agent">{agent}</Text>
        </div>
        <div className="space-align-container" css={cssBtnSpace(scaleValue)}>
          <div className="space-align-block">
            <Space align="center" size={10}>
              <Button
                className="agentRegister"
                type="primary"
                style={{ background: COMMON_COLOR.MAIN1, width: 140 }}
              >
                대리인 등록
              </Button>
              <Button
                className="agentDelete"
                type="primary"
                style={{ background: COMMON_COLOR.MAIN2, width: 140 }}
                danger
              >
                대리인 삭제
              </Button>
            </Space>
          </div>
        </div>
      </div>
    </>
  );
};

export default AgentPage;
