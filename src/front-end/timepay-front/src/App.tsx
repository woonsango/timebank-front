import BaseLayout from './components/BaseLayout';
import { Route, Routes } from 'react-router-dom';
import React from 'react';
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
          <Route element={<BaseLayout />}>
            <Route path={PATH.HOME} element={<PageRoutes.HomePage />} />
            <Route path={PATH.SEARCH} element={<PageRoutes.SearchPage />} />
            <Route path={PATH.MY_PAGE} element={<PageRoutes.MyPage />} />
          </Route>
          <Route>
            <Route
              path={PATH.Register_HR}
              element={<PageRoutes.RegisterRequestPage />}
            />
            <Route
              path={PATH.Register_HS}
              element={<PageRoutes.RegisterServePage />}
            />
            <Route
              path={PATH.Register_F}
              element={<PageRoutes.RegisterFreePage />}
            />
          </Route>
        </Routes>
      </ConfigProvider>
    </RecoilRoot>
  );
}

export default App;
