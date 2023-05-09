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
          <Route
            path={PATH.FINISHJOIN}
            element={<PageRoutes.FinishJoinPage />}
          />
          <Route
            path={PATH.AGENCY_SIGN_IN}
            element={<PageRoutes.AgencySignInPage />}
          />
          <Route element={<BaseLayout />}>
            <Route path={PATH.HOME} element={<PageRoutes.HomePage />} />
            <Route path={PATH.SEARCH} element={<PageRoutes.SearchPage />} />
            <Route path={PATH.MY_PAGE} element={<PageRoutes.MyPage />} />
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
            <Route
              path={PATH.NOTIFICATION}
              element={<PageRoutes.PushNotificationPage />}
            />
            <Route path={PATH.Qna_List} element={<PageRoutes.QnaListPage />} />
            <Route
              path={PATH.Qna_Register}
              element={<PageRoutes.QnaRegisterPage />}
            />
            <Route
              path={PATH.Qna_Detail}
              element={<PageRoutes.QnaDetailPage />}
            />
            <Route path={PATH.Posts} element={<PageRoutes.PostPage />} />
            <Route
              path={PATH.Posts_Edit}
              element={<PageRoutes.PostEditPage />}
            />
            <Route
              path={PATH.MY_ACTIVITY_RECORD}
              element={<PageRoutes.ActivityRecordPage />}
            />
            <Route path={PATH.MY} element={<PageRoutes.MyPage />} />
            <Route path={PATH.MY_EDIT} element={<PageRoutes.MyEditPage />} />
            <Route
              path={PATH.AGENCY_SIGN_UP}
              element={<PageRoutes.AgencySignUpPage />}
            />
            <Route
              path={PATH.MY_VOLUNTEER}
              element={<PageRoutes.MyVolunteerPage />}
            />
          </Route>
        </Routes>
      </ConfigProvider>
    </RecoilRoot>
  );
}

export default App;
