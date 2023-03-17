import { cssLoginStyle } from '../../components/Login/Login.styles';
import { Button } from 'antd';
import { NavLink, useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';

import KAKAO_AUTH_URL from './Authorization_kakao';
import kakao_login_logo from '../../assets/images/icons/kakao_login_medium_wide.png';

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
    <div>
      <div>
        <Button href={KAKAO_AUTH_URL} icon={iconimg}></Button>
      </div>
      <Button type="text" onClick={() => handleOnClickLinkBtn(PATH.HOME)}>
        로그인없이 시작하기
        <NavLink
          to={PATH.HOME}
          className={({ isActive }) => (isActive ? 'active' : undefined)}
        ></NavLink>
      </Button>
    </div>
  );
};

export default LoginPage;
