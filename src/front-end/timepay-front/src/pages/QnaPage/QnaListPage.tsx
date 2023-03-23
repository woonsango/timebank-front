import { Layout, FloatButton } from 'antd';
import { useCallback } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import { EditFilled } from '@ant-design/icons';
import { PATH } from '../../utils/paths';

const Header = Layout;

const QnaListPage = () => {
  // 뒤로가기
  const navigate = useNavigate();
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  //페이지 이동

  return (
    <Layout>
      <Header css={cssMainHeaderStyle}>
        <BackArrow onClick={handleClickBack} />
        <span>문의하기</span>
      </Header>
      <Link to="/qna/register">
        <FloatButton
          shape="circle"
          type="primary"
          style={{ right: 24, width: 70, height: 70 }}
          icon={<EditFilled style={{ fontSize: 35, marginLeft: -8 }} />}
        />
      </Link>
    </Layout>
  );
};
export default QnaListPage;
