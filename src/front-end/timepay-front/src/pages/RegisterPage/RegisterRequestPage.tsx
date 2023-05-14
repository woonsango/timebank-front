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
} from 'antd';
import { RcFile, UploadChangeParam, UploadProps } from 'antd/es/upload';
import { useCallback, useState, useMemo } from 'react';
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
  cssPostCategoryStyle,
} from './RegisterFreePage.style';
import { FlagFilled } from '@ant-design/icons';
import { KoDatePicker } from '../../components/register/KoDatePicker';
import TimeSelct from '../../components/register/TimeSelect';
import { useRecoilState } from 'recoil';
import { DateRange, startTime, endTime } from '../../states/register';
import { useCreateDealBoards } from '../../api/hooks/register';
import dummyProfileImg from '../../assets/images/icons/dummy-profile-img.png';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;
const { RangePicker } = DatePicker;

const MAX_IMAGES = 5;

const RegisterRequestPage = () => {
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

  const normFile = (e: any) => {
    if (Array.isArray(e)) {
      return e;
    }
    return e?.fileList;
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
  // 시간에 따른 타임페이 환산
  const [starttime, setStarttime] = useRecoilState<string>(startTime);
  const [endtime, setEndtime] = useRecoilState<string>(endTime);

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

  const handleOnSubmit = useCallback(
    async (values: any) => {
      let formData = new FormData();
      if (values.images && values.images.length > 0) {
        formData.append('image', values.images[0].originFileObj);
      }

      const newPost = {
        ...values,
        d_board_id: parseInt(values.d_board_id),
        images: null,
      };

      formData.append(
        'dealBoardDTO',
        new Blob([JSON.stringify(newPost)], { type: 'application/json' }),
      );

      await createDealBoardsMutation.mutateAsync(formData, {
        onSuccess: (result) => {
          messageApi.open({
            type: 'success',
            content: '회원가입을 성공했습니다.',
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
              <Radio.Button value="1">산책</Radio.Button>
              <Radio.Button value="2">봉사</Radio.Button>
              <Radio.Button value="3">교육</Radio.Button>
              <Radio.Button value="4">친목</Radio.Button>
              <Radio.Button value="5">생활</Radio.Button>
              <Radio.Button value="6">건강</Radio.Button>
              <Radio.Button value="7">도와주세요</Radio.Button>
            </Radio.Group>
          </Form.Item>

          <div css={cssLineStyle} />
          <Form.Item
            name="range-picker"
            label="날짜"
            css={cssPostCategoryStyle}
          >
            <RangePicker />
          </Form.Item>

          <Form.Item name="time">
            <TimeSelct />
          </Form.Item>
          <div>
            <p>내 타임페이 : {pay}</p>
            <p>지급해야할 타임페이 : {exchangeTime}</p>
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
              <Button
                htmlType="submit"
                css={cssPostBtnStyle}
                disabled={isDisabled}
              >
                작성완료
              </Button>
            ) : (
              <Button
                htmlType="submit"
                css={cssPostBtnStyle}
                disabled={isDisabled}
              >
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
