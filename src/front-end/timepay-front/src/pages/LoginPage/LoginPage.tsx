import { cssLoginStyle } from '../../components/Login/Login.styles';
import { Button } from 'antd';
import { NavLink, useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';

const LoginPage = () => {
  const navigate = useNavigate();

  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <div>
      <div>
        <Button
          type="text"
          css={cssLoginStyle}
          onClick={() => handleOnClickLinkBtn(PATH.JOIN)}
        >
          카카오로 로그인하기
          <NavLink
            to={PATH.JOIN}
            className={({ isActive }) => (isActive ? 'active' : undefined)}
          ></NavLink>
        </Button>
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
