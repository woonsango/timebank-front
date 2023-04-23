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
} from './RegisterFreePage.style';
import { FlagFilled } from '@ant-design/icons';
import { KoDatePicker } from '../../components/register/KoDatePicker';
import TimeSelct from '../../components/register/TimeSelect';
import ImageUpload from '../../components/register/ImageUpload';
import { useRecoilState } from 'recoil';
import { DateRange, startTime, endTime } from '../../states/register';
import axios from 'axios';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;

const RegisterRequestPage = () => {
  const timepay = 1000;
  const category = 'serve';
  const state = '게시완료';
  const [title, setTitle] = useState<string>('');
  const [location, setLocation] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [selectedCategory, setSelectedCategory] = useState<string>('');
  const categories = [
    '산책',
    '봉사',
    '교육',
    '친목',
    '생활',
    '건강',
    '도와주세요',
  ];

  const handleCategoryClick = (category: string) => {
    setSelectedCategory((prevCategory) =>
      prevCategory === category ? '' : category,
    );
    console.log(category);
    console.log(typeof category);
  };

  // 날짜
  const [dates, setDates] = useState<DateRange>([null, null]);
  const handleDatesChange = (value: DateRange) => {
    setDates(value);
  };
  // 시간에 따른 타임페이 환산
  const [starttime, setStarttime] = useRecoilState(startTime);
  const [endtime, setEndtime] = useRecoilState(endTime);

  const minusHours: any =
    0 <= Number(endtime.slice(0, 2)) - Number(starttime.slice(0, 2))
      ? Number(endtime.slice(0, 2)) - Number(starttime.slice(0, 2))
      : 0;
  const minusMinutes: any =
    0 !== Number(endtime.slice(3, 5)) - Number(starttime.slice(3, 5))
      ? Number(endtime.slice(3, 5)) + Number(starttime.slice(3, 5))
      : 0;
  const exchangeTime: number = minusHours * 60 + minusMinutes;
  // 보유 타임페이보다 지급 타임페이가 큰 경우의 로직 나중에.. 구현

  // 뒤로가기
  const navigate = useNavigate();
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  // 버튼 활성화 관련
  const isDisabled =
    !title ||
    !content ||
    !location ||
    !dates[0] ||
    !dates[1] ||
    !selectedCategory;
  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };
  const handleLocationChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setLocation(event.target.value);
  };
  const handleContentChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setContent(event.target.value);
  };
  const handleSubmit = () => {
    axios
      .post('/api/deal-boards/write/help', {
        category,
        title,
        content,
        location,
        state,
      })
      .then((response) => {
        // 요청이 성공적으로 처리되었을 때 실행될 코드 작성
        console.log('게시글 등록!');
        // 등록 후에는 홈 화면으로 이동
        navigate(PATH.HOME);
      })
      .catch((error) => {
        // 요청이 실패했을 때 실행될 코드 작성
        console.error('게시글 등록 실패애애', error);
      });
  };

  return (
    <Layout css={cssPostPageStyle}>
      <div className="wrapper">
        <Header css={cssMainHeaderStyle}>
          <BackArrow onClick={handleClickBack} />
          <span>도움받기</span>
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
          <div className="category-container">
            {categories.map((category) => (
              <button
                key={category}
                className={`category ${
                  selectedCategory === category ? 'selected' : ''
                }`}
                onClick={() => handleCategoryClick(category)}
              >
                {category}
              </button>
            ))}
          </div>
          <div css={cssLineStyle} />
          <h6>날짜</h6>
          <KoDatePicker value={dates} onChange={handleDatesChange} />
          <h6>시간</h6>
          <TimeSelct />
          <p>내 타임페이 : {timepay}</p>
          <p>지급해야할 타임페이 : {exchangeTime}</p>
          <div css={cssLineStyle} />
          <h6>장소</h6>
          <Input
            size="large"
            placeholder="희망하는 장소를 입력하세요 :)"
            style={{ marginLeft: '20px', paddingLeft: '15px', width: '280px' }}
            prefix={<FlagFilled style={{ marginRight: '5px' }} />}
            onChange={handleLocationChange}
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

export default RegisterRequestPage;
