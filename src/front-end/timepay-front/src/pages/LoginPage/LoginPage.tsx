import { Button, Space, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';

import KAKAO_AUTH_URL from './Authorization_kakao';
import kakao_login_logo from '../../assets/images/icons/kakao_login_medium_wide.png';
import timepayLogo from '../../assets/images/icons/timepayLogo.png';
import {
  cssBottomText,
  cssKakaoLoginBtn,
  cssLogo,
  cssMiddleText,
  cssTopText,
  topWrapperCSS,
} from './Login.styles';

const LoginPage = () => {
  const { Text } = Typography;

  const navigate = useNavigate();
  const goTo = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <Space
      direction="vertical"
      size="middle"
      style={{ display: 'flex' }}
      css={topWrapperCSS}
    >
      <Text css={cssTopText}>모두에게 동일한 시간</Text>
      <Text css={cssMiddleText}>시간을 거래하는 타임페이</Text>
      <img src={timepayLogo} className="logo" css={cssLogo} alt="타임페이" />
      <Text css={cssBottomText}>나의 시간을 가치있게 써보아요</Text>

      <Button
        type="link"
        ghost
        block
        className="kakaoBtn"
        href={KAKAO_AUTH_URL}
        icon={<img width={300} src={kakao_login_logo} alt="카카오로 로그인" />}
        css={cssKakaoLoginBtn}
      ></Button>
    </Space>
  );
};

export default LoginPage;
