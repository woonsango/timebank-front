import { cssMainFooterStyle, cssPlusPostBtnStyle } from './MainFooter.styles';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Layout } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { ReactComponent as ModifyFontSizeBig } from '../../assets/images/icons/modify-font-size-big.svg';
import { ReactComponent as ModifyFontSizeSmall } from '../../assets/images/icons/modify-font-size-small.svg';
import { ReactComponent as WriteBoard } from '../../assets/images/icons/write-board.svg';
import { ReactComponent as ActivityRecord } from '../../assets/images/icons/activity-record.svg';
import { ReactComponent as Home } from '../../assets/images/icons/home.svg';
import { Link, NavLink } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import { useCallback, useMemo } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { fontSizeState } from '../../states/uiState';
import useFontSize from '../../hooks/useFontSize';
import { agencyState } from '../../states/user';

const MainFooter = () => {
  const { isBig } = useFontSize();
  const setFontSize = useSetRecoilState(fontSizeState);

  const agencyValue = useRecoilValue(agencyState);

  const items: MenuProps['items'] = useMemo(() => {
    const items = [
      {
        key: PATH.Register_HR,
        label: <Link to={PATH.Register_HR}>도움요청</Link>,
      },
      {
        key: PATH.Register_HS,
        label: <Link to={PATH.Register_HS}>같이하기</Link>,
      },
    ];
    if (agencyValue)
      items.push({
        key: PATH.Register_EVENT,
        label: <Link to={PATH.Register_EVENT}>이벤트</Link>,
      });
    return items;
  }, [agencyValue]);

  const handleOnClickModifyFontSize = useCallback(() => {
    if (isBig) setFontSize('small');
    else setFontSize('big');
  }, [isBig, setFontSize]);

  return (
    <Layout.Footer css={cssMainFooterStyle}>
      <NavLink
        to={PATH.HOME}
        className={({ isActive }) => (isActive ? 'active' : undefined)}
      >
        <Button>
          <Home width={30} height={30} />
        </Button>
      </NavLink>
      <Button onClick={handleOnClickModifyFontSize}>
        <ModifyFontSizeSmall style={{ display: isBig ? 'none' : 'block' }} />
        <ModifyFontSizeBig style={{ display: isBig ? 'block' : 'none' }} />
      </Button>
      <Dropdown menu={{ items }} placement="top" arrow trigger={['click']}>
        <Button css={cssPlusPostBtnStyle}>
          <WriteBoard width={30} height={30} />
        </Button>
      </Dropdown>

      <NavLink
        to={PATH.MY_ACTIVITY_RECORD}
        className={({ isActive }) => (isActive ? 'active' : undefined)}
      >
        <Button>
          <ActivityRecord width={30} height={30} />
        </Button>
      </NavLink>

      <NavLink
        to={PATH.MY}
        className={({ isActive }) => (isActive ? 'active' : undefined)}
      >
        <Button>
          <UserOutlined />
        </Button>
      </NavLink>
    </Layout.Footer>
  );
};

export default MainFooter;
