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

  const isnoFooter =
    location.pathname === PATH.Register_F ||
    location.pathname === PATH.Register_HR ||
    location.pathname === PATH.Register_HS;

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
      {isnoFooter ? null : <MainFooter />}
    </Layout>
  );
};
export default BaseLayout;
