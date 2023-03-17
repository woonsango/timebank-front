import BaseLayout from './components/BaseLayout';
import { Route, Routes } from 'react-router-dom';
import { ConfigProvider } from 'antd';
import { customTheme } from './styles/constants/customTheme';
import PageRoutes from './pages';
import { PATH } from './utils/paths';
import { RecoilRoot } from 'recoil';

function App() {
  return (
    <RecoilRoot>
      <ConfigProvider theme={customTheme}>
        <Routes>
          <Route path={PATH.LOGIN} element={<PageRoutes.LoginPage />} />
          <Route
            path={PATH.REDIRECT}
            element={<PageRoutes.kakaoRedirectHandler />}
          />
          <Route path={PATH.JOIN} element={<PageRoutes.JoinPage />} />

          <Route element={<BaseLayout />}>
            <Route path={PATH.HOME} element={<PageRoutes.HomePage />} />
            <Route path={PATH.SEARCH} element={<PageRoutes.SearchPage />} />
            <Route path={PATH.MY_PAGE} element={<PageRoutes.MyPage />} />
          </Route>
        </Routes>
      </ConfigProvider>
    </RecoilRoot>
  );
}

export default App;
