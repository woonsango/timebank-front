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
            <Route index element={<PageRoutes.QnAManagementPage />} />
            <Route
              path={PATH.QNA_MANAGEMENT}
              element={<PageRoutes.QnAManagementPage />}
            />
            <Route
              path={PATH.USER_MANAGEMENT}
              element={<PageRoutes.UserManagementPage />}
            />
            <Route
              path={PATH.POST_MANAGEMENT}
              element={<PageRoutes.PostManagementPage />}
            />
            <Route
              path={PATH.CATEGORY_MANAGEMENT}
              element={<PageRoutes.CategoryManagementPage />}
            />
            <Route
              path={PATH.ADMIN_MANAGEMENT}
              element={<PageRoutes.AdminManagementPage />}
            />
            <Route
              path={PATH.REPORT_MANAGEMENT}
              element={<PageRoutes.ReportManagementPage />}
            />
          </Route>
        </Routes>
      </ConfigProvider>
    </RecoilRoot>
  );
}

export default App;
