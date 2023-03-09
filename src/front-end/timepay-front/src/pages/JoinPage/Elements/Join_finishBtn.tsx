import { cssJoinStyle_finishBtn } from '../../../components/Join/Join.styles';
import { Button } from 'antd';
import { NavLink, useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../../utils/paths';

const Join_finishBtn = () => {
  const navigate = useNavigate();

  const handleOnClickLinkBtn = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <div>
      <Button
        type="text"
        css={cssJoinStyle_finishBtn}
        onClick={() => handleOnClickLinkBtn(PATH.HOME)}
      >
        가입하기
        <NavLink
          to={PATH.HOME}
          className={({ isActive }) => (isActive ? 'active' : undefined)}
        ></NavLink>
      </Button>
    </div>
  );
};

export default Join_finishBtn;
