import {
  Steps,
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
import { useCallback, useState, useMemo, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import {
  cssPostPageStyle,
  cssPostTitleStyle,
  cssPostTitleInputStyle,
  cssLineStyle,
  cssPostContentStyle,
  cssPostContentInputStyle,
  cssPostBtnStyle,
  cssPostBtnStyle2,
  cssPostFooterStyle,
  cssPostCategoryStyle,
  cssPostDateStyle,
} from './RegisterFreePage.style';
import { FlagFilled } from '@ant-design/icons';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { useQueryClient } from 'react-query';
import { useCreateDealBoards } from '../../api/hooks/register';
import dummyProfileImg from '../../assets/images/icons/dummy-profile-img.png';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import locale from 'antd/es/date-picker/locale/ko_KR';
import { useGetCategory } from '../../api/hooks/category';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { startTime } from '../../states/register';

const { TextArea } = Input;
const MAX_IMAGES = 5;

const RegisterRequestPage = () => {
  const queryClient = useQueryClient();
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('도움요청');
  }, [setHeaderTitle]);

  const [title, setTitle] = useState<string>('');
  const [location, setLocation] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [form] = Form.useForm();
  const [imgFileList, setImgFileList] = useState<UploadFile[]>([]);
  const [previewImage, setPreviewImage] = useState('');
  const [previewOpen, setPreviewOpen] = useState(false);

  const createDealBoardsMutation = useCreateDealBoards();
  const [messageApi, contextHolder] = message.useMessage();

  const pay = 100;

  const { data, isLoading } = useGetCategory({
    type: '도움요청',
    useYn: 'Y',
  });

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
        startTime: `${values.activityDate.format(
          'YYYY-MM-DD',
        )}T${values.startTime.format('HH:mm:ss')}.000Z`,
        endTime: `${values.activityDate.format(
          'YYYY-MM-DD',
        )}T${values.endTime.format('HH:mm:ss')}.000Z`,
      };

      console.log(newPost);

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

  const [current, setCurrent] = useState(0);

  const handleCategoryChange = (value: any) => {
    setCurrent(1); // Change to the next step when category is selected
    // handle the category change logic if needed
  };

  const handleTimeLocationChange = () => {
    setCurrent(2); // Change to the next step when time and location are entered
    // handle the time and location change logic if needed
  };

  return (
    <div css={cssPostPageStyle}>
      {contextHolder}
      <Steps
        direction="vertical"
        current={current}
        style={{
          position: 'fixed',
          zIndex: 1,
          background: `${COMMON_COLOR.WHITE}`,
          paddingLeft: 10,
          paddingTop: 10,
          boxShadow: '0px 2px 5px rgba(0, 0, 0, 0.2)',
        }}
        items={[
          {
            title: '카테고리 선택',
          },
          {
            title: '시간/장소 입력',
          },
          {
            title: '게시글 내용 입력',
          },
        ]}
      />
      <Form onFinish={handleOnSubmit} form={form} layout="vertical">
        <Form.Item
          label="카테고리 선택"
          name="category"
          css={cssPostCategoryStyle}
        >
          <Radio.Group onChange={handleCategoryChange}>
            {data?.data.map((category) => (
              <Radio.Button
                key={category.categoryId}
                value={category.categoryId}
                style={{
                  borderRadius: '0',
                  margin: '5px',
                  fontWeight: '500',
                }}
              >
                {category.categoryName}
              </Radio.Button>
            ))}
          </Radio.Group>
        </Form.Item>

        <div css={cssLineStyle} />

        <Form.Item
          name="activityDate"
          label="활동일"
          initialValue={dayjs()}
          css={cssPostDateStyle}
        >
          <DatePicker format="YYYY년 MM월 DD일" />
        </Form.Item>

        <div className="time">
          <Form.Item
            name="startTime"
            label="활동을 시작할 시간"
            css={cssPostDateStyle}
            initialValue={dayjs()}
          >
            <DatePicker.TimePicker
              locale={locale}
              format="HH시 mm분"
              placeholder="시간"
              showNow={false}
              minuteStep={30}
            />
          </Form.Item>
          <Form.Item
            name="endTime"
            label="활동이 끝날 시간"
            css={cssPostDateStyle}
            initialValue={dayjs()}
          >
            <DatePicker.TimePicker
              locale={locale}
              format="HH시 mm분"
              placeholder="시간"
              showNow={false}
              minuteStep={30}
              allowClear={false}
            />
          </Form.Item>
        </div>

        <Form.Item label="장소" name="location" css={cssPostDateStyle}>
          <Input
            size="large"
            placeholder="희망하는 장소를 입력하세요 :)"
            style={{
              paddingLeft: '15px',
              width: '280px',
            }}
            prefix={<FlagFilled style={{ marginRight: '5px' }} />}
            onChange={(event) => {
              handleLocationChange(event);
              handleTimeLocationChange();
            }}
          />
        </Form.Item>
        <div css={cssLineStyle} />

        <Form.Item label="제목" name="title" css={cssPostTitleStyle}>
          <Input
            css={cssPostTitleInputStyle}
            placeholder="제목을 입력하세요"
            maxLength={25}
            value={title}
            onChange={handleTitleChange}
          />
        </Form.Item>

        <Form.Item label="내용" name="content" css={cssPostContentStyle}>
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
  );
};

export default RegisterRequestPage;
