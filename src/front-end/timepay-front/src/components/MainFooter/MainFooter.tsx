import { cssMainFooterStyle, cssPlusPostBtnStyle } from './MainFooter.styles';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Layout } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { ReactComponent as ModifyFontSize } from '../../assets/images/icons/modify-font-size.svg';
import { ReactComponent as WriteBoard } from '../../assets/images/icons/write-board.svg';
import { ReactComponent as ActivityRecord } from '../../assets/images/icons/activity-record.svg';
import { ReactComponent as Home } from '../../assets/images/icons/home.svg';
import { Link, NavLink } from 'react-router-dom';
import { PATH } from '../../utils/paths';

const MainFooter = () => {
  const items: MenuProps['items'] = [
    {
      key: '/register/help-request',
      label: <Link to="/register/help-request">도움받기</Link>,
    },
    {
      key: '/register/help-serve',
      label: <Link to="/register/help-serve">도움주기</Link>,
    },
    {
      key: '/register/free',
      label: <Link to="/register/free">자유</Link>,
    },
  ];

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
      <Button>
        <ModifyFontSize />
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
