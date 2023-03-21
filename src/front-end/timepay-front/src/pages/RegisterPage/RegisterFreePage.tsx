import { Layout, Input, Button } from 'antd';
import { useNavigate, Link } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import { useCallback, useState } from 'react';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostPageStyle,
  cssPostTitleInputStyle,
  cssLineStyle,
  cssPostContentInputStyle,
  cssPostBtnStyle,
  cssPostFooterStyle,
} from './RegisterFreePage.style';
import ImageUpload from '../../components/register/ImageUpload';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;

const RegisterFreePage = () => {
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');

  const navigate = useNavigate();
  // 뒤로 가기
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  // 버튼 활성화 관련
  const isDisabled = !title || !content;

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };
  const handleContentChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setContent(event.target.value);
  };

  const handleSubmit = () => {
    // 게시글 작성 완료 처리
    console.log('자유게시글 등록');
  };

  return (
    <Layout css={cssPostPageStyle}>
      <div className="wrapper">
        <Header css={cssMainHeaderStyle}>
          <BackArrow onClick={handleClickBack} />
          <span>글쓰기</span>
        </Header>
        <Content style={{ paddingTop: 60 }}>
          <input
            css={cssPostTitleInputStyle}
            placeholder="제목을 입력하세요"
            maxLength={25}
            value={title}
            onChange={handleTitleChange}
          />
          <div css={cssLineStyle} />
          <TextArea
            rows={10}
            bordered={false}
            style={{ resize: 'none' }}
            css={cssPostContentInputStyle}
            placeholder="내용을 입력하세요"
            value={content}
            onChange={handleContentChange}
          />
          <div css={cssLineStyle} />
          <ImageUpload />
        </Content>
      </div>
      <Footer css={cssPostFooterStyle}>
        <Link to={PATH.HOME}>
          <Button
            css={cssPostBtnStyle}
            onClick={handleSubmit}
            disabled={isDisabled}
          >
            작성완료
          </Button>
        </Link>
      </Footer>
    </Layout>
  );
};

export default RegisterFreePage;
