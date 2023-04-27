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
import axios from 'axios';
import { useRecoilState } from 'recoil';
import { DateRange, startTime, endTime } from '../../states/register';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;

const MAX_IMAGES = 5;

const RegisterServePage = () => {
  const timepay = 1000;
  const state = '게시완료';
  const hidden: boolean = false;
  const [title, setTitle] = useState<string>('');
  const [place, setPlace] = useState<string>('');
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

  // 사진
  const [images, setImages] = useState<File[]>([]);
  const [previewUrls, setPreviewUrls] = useState<string[]>([]);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(e.target.files as FileList);
    const urls = files.map((file) => URL.createObjectURL(file));
    // 최대 5개의 이미지를 업로드할 수 있도록 하고 이미지가 5개가 넘을 경우 추가로 업로드하지 못하도록 합니다.
    if (images.length + files.length > MAX_IMAGES) {
      alert(`최대 ${MAX_IMAGES}개의 이미지까지 업로드할 수 있습니다.`);
      return;
    }
    setImages([...images, ...files]);
    setPreviewUrls([...previewUrls, ...urls]);
  };

  const handleDeleteImage = (index: number) => {
    setImages((prevState) => prevState.filter((_, i) => i !== index));
    setPreviewUrls((prevState) => prevState.filter((_, i) => i !== index));
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
    !title || !content || !place || !dates[0] || !dates[1] || !selectedCategory;
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
  // 이미지 업로드 핸들러
  const handleImageChange = (
    event: React.ChangeEvent<HTMLInputElement>,
    index: number,
  ) => {
    const file = event.target.files?.[0];
    if (!file) return;

    // 이미지 파일만 업로드 가능하도록 체크합니다.
    if (!file.type.startsWith('image/')) {
      alert('이미지 파일만 업로드 가능합니다.');
      return;
    }

    const newImages = [...images];
    const newPreviewUrls = [...previewUrls];
    newImages[index] = file;
    newPreviewUrls[index] = URL.createObjectURL(file);

    setImages(newImages);
    setPreviewUrls(newPreviewUrls);
  };

  const handleSubmit = () => {
    const formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);
    formData.append('location', place);
    formData.append('state', state);
    formData.append('startTime', starttime);
    formData.append('endTime', endtime);

    // images.forEach((image) => formData.append('images', image));
    axios
      .post('/api/deal-boards/write/helper', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((response) => {
        console.log('게시글이 등록🤩');
        navigate(PATH.HOME);
      })
      .catch((error) => {
        console.error('게시글 등록 실패🥹', error);
      });
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
          <p>받아야 할 타임페이 : {exchangeTime}</p>
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
          <div className="image-container">
            <div className="imageFont">사진 ({images.length} / 5)</div>

            {previewUrls.length < MAX_IMAGES && (
              <div className="cssImageWrapper1">
                <div className="cssImagePlaceholder">
                  <label htmlFor="upload">
                    <div className="uploadBtn">
                      📷 <br />
                      사진 추가
                    </div>
                  </label>
                  <input
                    type="file"
                    accept="image/*"
                    onChange={handleFileChange}
                    id="upload"
                  />
                </div>
              </div>
            )}

            <div className="images-container">
              {previewUrls.map((url, index) => (
                <div className="cssImageWrapper2" key={index}>
                  <img src={url} className="cssSelectedImage" alt="uploaded" />
                  <div className="cssImages">
                    <div className="cssImagePlaceholder2">
                      <label htmlFor="change">
                        <div className="changeBtn">사진 변경</div>
                      </label>
                      <input
                        className="fileButton"
                        type="file"
                        accept="image/*"
                        onChange={(e) => handleImageChange(e, index)}
                        id="change"
                      />
                    </div>
                    <Button
                      danger
                      className="deleteBtn"
                      onClick={() => handleDeleteImage(index)}
                    >
                      삭제
                    </Button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </Content>
      </div>
      <Footer css={cssPostFooterStyle}>
        {isDisabled ? (
          <Button
            css={cssPostBtnStyle}
            onClick={handleSubmit}
            disabled={isDisabled}
          >
            작성완료
          </Button>
        ) : (
          <Link to={PATH.HOME}>
            <Button
              css={cssPostBtnStyle}
              onClick={handleSubmit}
              disabled={isDisabled}
            >
              작성완료
            </Button>
          </Link>
        )}
      </Footer>
    </Layout>
  );
};

export default RegisterServePage;
