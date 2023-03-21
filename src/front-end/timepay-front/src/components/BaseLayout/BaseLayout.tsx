import { Layout } from 'antd';
import { useMemo } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';
import HomeHeader from '../HomeHeader';
import MainFooter from '../MainFooter';
import MainHeader from '../MainHeader';
import { cssBaseLayoutStyle } from './BaseLayout.styles';
const BaseLayout = () => {
  const location = useLocation();
  const headerTitle = useRecoilValue(headerTitleState);

  const isHome = useMemo(() => {
    return location.pathname === PATH.HOME;
  }, [location]);

  return (
    <Layout css={cssBaseLayoutStyle}>
      {isHome ? <HomeHeader /> : headerTitle ? <MainHeader /> : null}
      <Layout.Content
        className={`main-section-container ${
          isHome || headerTitle ? 'show-header' : 'no-header'
        }`}
      >
        <Outlet />
      </Layout.Content>
      <MainFooter />
    </Layout>
  );
};
export default BaseLayout;
