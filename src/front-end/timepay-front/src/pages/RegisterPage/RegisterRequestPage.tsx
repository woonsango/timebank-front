import {
  Layout,
  Input,
  Button,
  Form,
  message,
  Upload,
  UploadFile,
  Radio,
  DatePicker,
  TimePicker,
} from 'antd';
import dayjs from 'dayjs';
import moment from 'moment';
import 'moment/locale/ko';
import locale from 'antd/lib/locale/ko_KR';
import { ConfigProvider } from 'antd';

import { RcFile, UploadChangeParam, UploadProps } from 'antd/es/upload';
import { useCallback, useState, useMemo, useEffect } from 'react';
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
  cssPostBtnStyle2,
  cssPostFooterStyle,
  cssPostCategoryStyle,
  cssPostDateStyle,
} from './RegisterFreePage.style';
import { FlagFilled } from '@ant-design/icons';
import { KoDatePicker } from '../../components/register/KoDatePicker';
import TimeSelct from '../../components/register/TimeSelect';
import { useRecoilState } from 'recoil';
import { DateRange, startTime, endTime } from '../../states/register';
import { useCreateDealBoards } from '../../api/hooks/register';
import dummyProfileImg from '../../assets/images/icons/dummy-profile-img.png';
import { apiRequest } from '../../api/request';
import { API_URL } from '../../api/urls';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;
const { RangePicker } = DatePicker;

const MAX_IMAGES = 5;

const RegisterRequestPage = () => {
  const [userId, setUserId] = useState(0);
  const [nickName, setNickName] = useState('');
  useEffect(() => {
    apiRequest
      .get(API_URL.USER_INFO_GET)
      .then((res) => {
        setUserId(res.data.body.id);
        setNickName(res.data.body.nick_name);
      })
      .catch((error) => {
        console.error('Error sending GET request:', error);
      });
  }, []);

  const [form] = Form.useForm();
  const [imgFileList, setImgFileList] = useState<UploadFile[]>([]);
  const [previewImage, setPreviewImage] = useState('');
  const [previewOpen, setPreviewOpen] = useState(false);

  const createDealBoardsMutation = useCreateDealBoards();
  const [messageApi, contextHolder] = message.useMessage();

  const pay = 100;
  const [title, setTitle] = useState<string>('');
  const [location, setLocation] = useState<string>('');
  const [content, setContent] = useState<string>('');

  const normFile = (e: any) => {
    if (Array.isArray(e)) {
      return e;
    }
    return e?.fileList;
  };

  // 사진
  const [images, setImages] = useState<File[]>([]);
  const [previewUrls, setPreviewUrls] = useState<string[]>([]);

  //////////

  const getBase64 = (file: RcFile): Promise<string> =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
    });
  const handleImgChange: UploadProps['onChange'] = (
    info: UploadChangeParam<UploadFile>,
  ) => {
    setImgFileList(info.fileList);
  };
  const handlePreview = async (file: UploadFile) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj as RcFile);
    }
    setPreviewImage(file.url || (file.preview as string));
    setPreviewOpen(true);
  };
  const handleCancelPreview = useCallback(() => setPreviewOpen(false), []);

  ////////

  // 날짜
  const [dates, setDates] = useState<DateRange>([null, null]);
  const handleDatesChange = (value: DateRange) => {
    setDates(value);
  };

  // 뒤로가기
  const navigate = useNavigate();
  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  // 버튼 활성화 관련
  const isDisabled = !title || !content || !location;

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

  const [selectedDate, setSelectedDate] = useState(null); // Date picker에서 선택한 날짜 객체
  const [selectedTime, setSelectedTime] = useState(null); // Time picker에서 선택한 시간 객체

  // date-picker
  let today = new Date();
  let year = today.getFullYear();
  let month = String(today.getMonth() + 1).padStart(2, '0');
  let date = String(today.getDate()).padStart(2, '0');
  const todayDate = year + '-' + month + '-' + date;
  console.log(todayDate);

  const onChange = (value: any) => {
    console.log(value.format('YYYY-MM-DD'));
  };

  // time-picker
  const [starttime, setStarttime] = useState('');
  const format = 'HH:mm';

  const onChange2 = (value: any) => {
    setStarttime(value.format('HH:mm:ss.SSSSSS'));
  };

  console.log('..', starttime);

  // 게시글 등록
  const handleOnSubmit = useCallback(
    async (values: any) => {
      let formData = new FormData();
      if (values.images && values.images.length > 0) {
        formData.append('image', values.images[0].originFileObj);
      }

      const formattedDate = dayjs(selectedDate).format('YYYY-MM-DD');
      const formattedTime = moment(selectedTime).format('HH:mm:ss.SSSSSS');

      const newPost = {
        ...values,
        d_board_id: parseInt(values.d_board_id),
        images: null,
        start_time: `${formattedDate}T${formattedTime}`, // start_time 값을 form-data에 추가
      };

      formData.append(
        'dealBoardDTO',
        new Blob([JSON.stringify(newPost)], { type: 'application/json' }),
      );

      await createDealBoardsMutation.mutateAsync(formData, {
        onSuccess: (result) => {
          messageApi.open({
            type: 'success',
            content: '게시글이 등록되었습니다.',
            duration: 1,
            onClose: () => {
              navigate(-1);
            },
          });
        },
        onError: (err) => {
          console.log(err);
          messageApi.open({
            type: 'error',
            content: (
              <>
                오류 발생: <br />
                {err}
              </>
            ),
          });
        },
      });
    },
    [messageApi, navigate, createDealBoardsMutation],
  );

  const uploadButton = useMemo(() => {
    return <img width="100%" height="100%" src={dummyProfileImg} alt="+" />;
  }, []);

  return (
    <div css={cssPostPageStyle}>
      <div className="wrapper">
        <Header css={cssMainHeaderStyle}>
          <BackArrow onClick={handleClickBack} />
          <span>도움받기</span>
        </Header>
        {contextHolder}
        <Form
          onFinish={handleOnSubmit}
          style={{ paddingTop: 60 }}
          form={form}
          layout="vertical"
        >
          <Form.Item name="title">
            <Input
              css={cssPostTitleInputStyle}
              placeholder="제목을 입력하세요"
              maxLength={25}
              value={title}
              onChange={handleTitleChange}
            />
          </Form.Item>
          <div css={cssLineStyle} />

          <Form.Item
            label="카테고리"
            name="category"
            css={cssPostCategoryStyle}
          >
            <Radio.Group>
              <Radio.Button value="심부름">심부름</Radio.Button>
              <Radio.Button value="교육">교육</Radio.Button>
              <Radio.Button value="돌봄">돌봄</Radio.Button>
              <Radio.Button value="청소,가사">청소,가사</Radio.Button>
              <Radio.Button value="수리,설치">수리,설치</Radio.Button>
              <Radio.Button value="이동">이동</Radio.Button>
            </Radio.Group>
          </Form.Item>

          <div css={cssLineStyle} />
          <Form.Item
            name="date-picker"
            label="활동할 날짜"
            css={cssPostDateStyle}
          >
            <ConfigProvider locale={locale}>
              <DatePicker
                onChange={onChange}
                defaultValue={dayjs(todayDate, 'YYYY-MM-DD')}
                size="large"
              />
            </ConfigProvider>
          </Form.Item>

          <Form.Item name="time" label="활동할 시간" css={cssPostDateStyle}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <TimePicker
                defaultValue={dayjs('9:00', format)}
                format={format}
                className="time1"
                size="large"
                onChange={onChange2}
              />
              <p> ~ </p>
              <TimePicker
                defaultValue={dayjs('16:00', format)}
                format={format}
                className="time"
                size="large"
                onChange={onChange2}
              />
            </div>
          </Form.Item>
          <div>
            <p>내 타임페이 : {pay}</p>
            <p>지급해야할 타임페이 : </p>
          </div>
          <div css={cssLineStyle} />
          <Form.Item label="장소" name="location" css={cssPostCategoryStyle}>
            <Input
              size="large"
              placeholder="희망하는 장소를 입력하세요 :)"
              style={{
                paddingLeft: '15px',
                width: '280px',
              }}
              prefix={<FlagFilled style={{ marginRight: '5px' }} />}
              onChange={handleLocationChange}
            />
          </Form.Item>
          <div css={cssLineStyle} />
          <Form.Item name="content">
            <TextArea
              rows={10}
              bordered={false}
              style={{ resize: 'none' }}
              css={cssPostContentInputStyle}
              placeholder="내용을 입력하세요"
              value={content}
              onChange={handleContentChange}
            />
          </Form.Item>
          <div css={cssLineStyle} />
          <Form.Item
            name="images"
            getValueFromEvent={normFile}
            valuePropName="fileList"
          >
            <Upload
              onChange={handleImgChange}
              onPreview={handlePreview}
              multiple={false}
              beforeUpload={() => false}
              accept="image/png, image/jpg, image/jpeg"
            >
              {imgFileList.length === 1 ? null : uploadButton}
            </Upload>
          </Form.Item>

          <div css={cssPostFooterStyle}>
            {isDisabled ? (
              <Button css={cssPostBtnStyle2}>작성완료</Button>
            ) : (
              <Button htmlType="submit" css={cssPostBtnStyle}>
                작성완료
              </Button>
            )}
          </div>
        </Form>
      </div>
    </div>
  );
};

export default RegisterRequestPage;
