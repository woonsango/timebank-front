import { Layout, Input, Button } from 'antd';
import { useCallback, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostPageStyle,
  cssPostTitleInputStyle,
  cssLineStyle,
  cssPostContentInputStyle,
  cssPostBtnStyle,
  cssPostFooterStyle,
} from '../RegisterPage/RegisterFreePage.style';
import { TagQnaSelect } from '../../components/register/TagSelect';
import ImageUpload from '../../components/register/ImageUpload';
import { useRecoilState } from 'recoil';
import { selectedTagsQnaState } from '../../states/register';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;

const QnaRegisterPage = () => {
  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  // 태그
  const [selectedTags, setSelectedTags] = useRecoilState(selectedTagsQnaState);

  // 뒤로가기
  const navigate = useNavigate();
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  // 버튼 활성화 관련
  const isDisabled = !title || !content || !selectedTags[0];
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
    console.log('문의글 등록');
    navigate(-1);
  };

  return (
    <Layout css={cssPostPageStyle}>
      <div className="wrapper">
        <Header css={cssMainHeaderStyle}>
          <BackArrow onClick={handleClickBack} />
          <span>문의작성</span>
        </Header>
        <Content style={{ paddingTop: 60 }}>
          <input
            css={cssPostTitleInputStyle}
            placeholder="제목을 입력하세요"
            maxLength={22}
            value={title}
            onChange={handleTitleChange}
          />
          <div css={cssLineStyle} />
          <h6>카테고리 설정</h6>
          <TagQnaSelect />
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

export default QnaRegisterPage;
