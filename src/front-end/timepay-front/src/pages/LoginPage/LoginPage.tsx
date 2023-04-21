import { Button, Space, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';
import { css } from '@emotion/react';

import KAKAO_AUTH_URL from './Authorization_kakao';
import kakao_login_logo from '../../assets/images/icons/kakao_login_medium_wide.png';
import timepayLogo from '../../assets/images/icons/timepayLogo.png';

/*수직 수평 중앙 정렬*/
const topWrapperCSS = css`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
`;

const LoginPage = () => {
  const navigate = useNavigate(); //history

  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const { Text } = Typography;

  return (
    <Space
      direction="vertical"
      size="middle"
      style={{ display: 'flex' }}
      css={topWrapperCSS}
    >
      <Text
        css={css`
          font-size: 20px;
          font-weight: bold;
        `}
      >
        모두에게 동일한 시간
      </Text>
      <Text
        css={css`
          font-size: 25px;
          font-weight: bold;
        `}
      >
        시간을 거래하는 타임페이
      </Text>
      <img
        src={timepayLogo}
        className="logo"
        css={css`
          margin-top: 30px;
          width: 150px;
        `}
      ></img>
      <Text
        css={css`
          font-size: 20px;
          font-weight: bold;
        `}
      >
        나의 시간을 가치있게 써보아요
      </Text>

      <Button
        type="link"
        ghost
        block
        className="kakaoBtn"
        href={KAKAO_AUTH_URL}
        icon={<img width={300} src={kakao_login_logo}></img>}
        css={css`
          margin-top: 30px;
        `}
      ></Button>

      <Button type="text" onClick={() => handleOnClickLinkBtn(PATH.HOME)}>
        로그인없이 시작하기
      </Button>
    </Space>
  );
};

export default LoginPage;
