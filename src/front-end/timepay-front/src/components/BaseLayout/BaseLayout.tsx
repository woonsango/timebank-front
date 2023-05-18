import { Badge, Layout, Typography } from 'antd';
import { useCallback, useMemo } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';
import MainFooter from '../MainFooter';
import MainHeader from '../MainHeader';
import SearchHeader from '../SearchHeader';
import {
  cssBaseLayoutStyle,
  cssHomeHeaderNotificationStyle,
} from './BaseLayout.styles';
import { BellOutlined } from '@ant-design/icons';

const { Text, Link } = Typography;

const BaseLayout = () => {
  const headerTitle = useRecoilValue(headerTitleState);
  const location = useLocation();
  const navigate = useNavigate();

  const isnoFooter =
    location.pathname === PATH.Register_F ||
    location.pathname === PATH.Register_HR ||
    location.pathname === PATH.Register_HS ||
    location.pathname === PATH.Qna_List ||
    location.pathname === PATH.Qna_Register ||
    location.pathname.includes('/post/') ||
    location.pathname.includes('/edit/') ||
    location.pathname === PATH.AGENCY_SIGN_UP;

  const isNoHeader = useMemo(() => {
    return location.pathname === PATH.HOME;
  }, [location]);

  const isSearch = useMemo(() => {
    return location.pathname === PATH.SEARCH;
  }, [location]);

  const Header = useMemo(() => {
    if (isNoHeader) return;
    if (isSearch) return <SearchHeader />;
    if (headerTitle) return <MainHeader />;
  }, [isNoHeader, isSearch, headerTitle]);

  const handleOnLinkNotification = useCallback(() => {
    navigate(PATH.NOTIFICATION);
  }, [navigate]);

  return (
    <Layout css={cssBaseLayoutStyle}>
      <Badge dot css={cssHomeHeaderNotificationStyle}>
        <BellOutlined onClick={handleOnLinkNotification} />
      </Badge>
      {Header}
      <Layout.Content
        className={`main-section-container ${
          isSearch
            ? 'show-search-header'
            : !isNoHeader && !!headerTitle
            ? 'show-header'
            : 'no-header'
        }`}
      >
        <Outlet />
      </Layout.Content>

      {isnoFooter ? null : <MainFooter />}
    </Layout>
  );
};
export default BaseLayout;
