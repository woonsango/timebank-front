import { Layout, Input, Space, Button } from 'antd';
import { useNavigate } from 'react-router-dom';
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
} from './PostFreePage.style';
import { FlagFilled } from '@ant-design/icons';
import dayjs from 'dayjs';

import TagSelect from '../../components/Post/TagSelect';
import TimeSelct from '../../components/Post/TimeSelect';
import ImageUpload from '../../components/Post/ImageUpload';
import { KoDatePicker, DateRange } from '../../components/Post/KoDatePicker';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;

const PostServePage = () => {
  const timepay = 500;
  const [starttime, setStarttime] = useState('');
  const [endtime, setEndtime] = useState('');

  const [title, setTitle] = useState('');

  const [place, setPlace] = useState('');
  const [content, setContent] = useState('');

  /*
  const scrollRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    scrollRef.current?.scrollTo(0, scrollRef.current.scrollHeight);
  });
  */

  const navigate = useNavigate();
  // 뒤로가기
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  // 날짜
  const [dates, setDates] = useState<DateRange>([null, null]);
  const handleDatesChange = (value: DateRange) => {
    setDates(value);
  };

  // 버튼 활성화 관련 변수
  const isDisabled = !title || !content || !place || !dates[0] || !dates[1];

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };
  const handlePlaceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPlace(event.target.value);
  };
  const handleContentChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setContent(event.target.value);
  };

  const handleSubmit = () => {
    // 게시글 작성 완료 처리
  };

  return (
    <Layout css={cssPostPageStyle}>
      <div className="wrapper">
        <Header css={cssMainHeaderStyle}>
          <BackArrow onClick={handleClickBack} />
          <span>도움주기</span>
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
          <h6>카테고리 설정</h6>
          <TagSelect />
          <div css={cssLineStyle} />
          <h6>날짜</h6>
          <KoDatePicker value={dates} onChange={handleDatesChange} />
          <h6>시간</h6>
          <TimeSelct />
          <p>보유하고 있는 타임페이 : {timepay}</p>
          <p>지급 타임페이 : {timepay}</p>
          <div css={cssLineStyle} />
          <h6>장소</h6>
          <Input
            size="large"
            placeholder="희망하는 장소를 입력하세요 :)"
            style={{ marginLeft: '20px', paddingLeft: '15px', width: '280px' }}
            prefix={<FlagFilled style={{ marginRight: '5px' }} />}
            onChange={handlePlaceChange}
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
        <Button
          css={cssPostBtnStyle}
          onClick={handleSubmit}
          disabled={isDisabled}
        >
          작성완료
        </Button>
      </Footer>
    </Layout>
  );
};

export default PostServePage;
