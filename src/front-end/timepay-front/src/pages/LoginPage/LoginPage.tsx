import { Button, Space, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useCallback, useEffect } from 'react';
import { PATH } from '../../utils/paths';
import { css } from '@emotion/react';

import KAKAO_AUTH_URL from './Authorization_kakao';
import kakao_login_logo from '../../assets/images/icons/kakao_login_medium_wide.png';
import timepayLogo from '../../assets/images/icons/timepayLogo.png';
import { getTokenFromCookie } from '../../utils/token';
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

  useEffect(() => {
    console.log('토큰 값 확인:', getTokenFromCookie());
    var userToken = getTokenFromCookie();

    if (!!userToken) {
      //토큰이 존재하면
      console.log('토큰 존재: ', userToken);
      console.log('go home');
      goTo(PATH.HOME);
    }
  }, []);

  return (
    <Space
      direction="vertical"
      size="middle"
      style={{ display: 'flex' }}
      css={topWrapperCSS}
    >
      <Text css={cssTopText}>모두에게 동일한 시간</Text>
      <Text css={cssMiddleText}>시간을 거래하는 타임페이</Text>
      <img src={timepayLogo} className="logo" css={cssLogo}></img>
      <Text css={cssBottomText}>나의 시간을 가치있게 써보아요</Text>

      <Button
        type="link"
        ghost
        block
        className="kakaoBtn"
        href={KAKAO_AUTH_URL}
        icon={<img width={300} src={kakao_login_logo}></img>}
        css={cssKakaoLoginBtn}
      ></Button>

      <Button type="text" onClick={() => goTo(PATH.HOME)}>
        로그인없이 시작하기
      </Button>
    </Space>
  );
};

export default LoginPage;
