import { Button } from 'antd';
import { NavLink, useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';

import KAKAO_AUTH_URL from './Authorization_kakao';
import kakao_login_logo from '../../assets/images/icons/kakao_login_medium_wide.png';
import timepayLogo from '../../assets/images/icons/timepayLogo.png';
import './Login.css';

const LoginPage = () => {
  const navigate = useNavigate(); //history

  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const iconimg = <img width={300} height={50} src={kakao_login_logo}></img>;

  return (
    <div className="LoginWrap">
      <div className="designWrap">
        <div className="MainSentence1">모두에게 동일한 시간</div>
        <div className="MainSentence2">시간을 거래하는 타임페이</div>
        <div className="logoWrap">
          <img src={timepayLogo} className="logo"></img>
        </div>

        <div className="subSentence">나의 시간을 가치있게 써보아요</div>
      </div>
      <div className="BtnWrap">
        <div className="kakao">
          <Button
            className="kakaoBtn"
            href={KAKAO_AUTH_URL}
            icon={iconimg}
          ></Button>
        </div>
        <div className="nonKakao">
          <Button type="text" onClick={() => handleOnClickLinkBtn(PATH.HOME)}>
            로그인없이 시작하기
            <NavLink
              to={PATH.HOME}
              className={({ isActive }) => (isActive ? 'active' : undefined)}
            ></NavLink>
          </Button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
