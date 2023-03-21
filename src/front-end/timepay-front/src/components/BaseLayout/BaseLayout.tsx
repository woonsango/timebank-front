import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import MainFooter from '../MainFooter';
import MainHeader from '../MainHeader';
import { cssBaseLayoutStyle } from './BaseLayout.styles';
import { PATH } from '../../utils/paths';

const BaseLayout = () => {
  const headerTitle = useRecoilValue(headerTitleState);
  const location = useLocation();

  const isFooterVisible =
    location.pathname === PATH.HOME ||
    location.pathname === PATH.SEARCH ||
    location.pathname === PATH.MY_PAGE;

  return (
    <Layout css={cssBaseLayoutStyle}>
      {headerTitle && <MainHeader />}
      <Layout.Content
        className={`main-section-container ${
          headerTitle ? 'show-header' : 'no-header'
        }`}
      >
        <Outlet />
      </Layout.Content>
      {isFooterVisible && <MainFooter />}
    </Layout>
  );
};
export default BaseLayout;
