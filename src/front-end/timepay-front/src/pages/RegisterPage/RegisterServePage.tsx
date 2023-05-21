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
  Steps,
} from 'antd';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import locale from 'antd/es/date-picker/locale/ko_KR';
import { RcFile, UploadChangeParam, UploadProps } from 'antd/es/upload';
import { useCallback, useState, useMemo, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssRegisterRequestPageStyle,
  cssRegisterRequestStepItemStyle,
} from './RegisterRequest.styles';
import { FlagFilled } from '@ant-design/icons';
import { useRecoilState } from 'recoil';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { useCreateDealBoards } from '../../api/hooks/register';
import dummyProfileImg from '../../assets/images/icons/dummy-profile-img.png';
import { apiRequest } from '../../api/request';
import { API_URL } from '../../api/urls';
import { useQueryClient } from 'react-query';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;
const { RangePicker } = DatePicker;

const MAX_IMAGES = 5;

const RegisterRequestPage = () => {
  const queryClient = useQueryClient();

  const createDealBoardsMutation = useCreateDealBoards();
  const navigate = useNavigate();
  const setHeaderTitle = useSetRecoilState(headerTitleState);

  const [categoryForm] = Form.useForm();
  const [timeForm] = Form.useForm();
  const [contentForm] = Form.useForm();
  const [current, setCurrent] = useState(0);
  const [exchangeTimepay, setExchangeTimepay] = useState(60);

  const [messageApi, contextHolder] = message.useMessage();

  const next = useCallback(() => {
    setCurrent(current + 1);
  }, [current]);

  const prev = useCallback(() => {
    setCurrent(current - 1);
  }, [current]);

  const handleOnChangeStep = useCallback((value: number) => {
    setCurrent(value);
  }, []);

  useEffect(() => {
    setHeaderTitle('도움 요청');
  }, [setHeaderTitle]);

  useEffect(() => {
    timeForm.setFieldValue('startTime', dayjs());
    timeForm.setFieldValue('rangeTime', dayjs('01:00', 'HH:mm'));
  }, [timeForm]);

  const handleOnChangeTime = useCallback((changedValues: any, values: any) => {
    if (values.rangeTime) {
      setExchangeTimepay(
        values.rangeTime.hour() * 60 + values.rangeTime.minute(),
      );
    } else {
      setExchangeTimepay(30);
    }
  }, []);

  const steps = useMemo(
    () => [
      { key: '카테고리', title: '1단계 : 카테고리 선택' },
      {
        key: '시간/장소',
        title: '2단계 : 시간/장소 입력',
      },
      {
        key: '내용',
        title: '3단계 : 게시글 내용 작성',
      },
    ],
    [],
  );

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

  // 뒤로가기

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

  // const [selectedDate, setSelectedDate] = useState(null); // Date picker에서 선택한 날짜 객체
  // const [selectedTime, setSelectedTime] = useState(null); // Time picker에서 선택한 시간 객체

  const [today1, setToday1] = useState('');
  const [today2, setToday2] = useState('');

  const [starttime, setStarttime] = useState('');

  // date-picker
  let today = new Date();
  let year = today.getFullYear();
  let month = String(today.getMonth() + 1).padStart(2, '0');
  let date = String(today.getDate()).padStart(2, '0');
  const todayDate = year + '-' + month + '-' + date;

  const onChange = (value: any) => {
    const formattedDate = value.format('YYYY-MM-DD');
    setToday1(formattedDate);
    console.log('1', formattedDate);
  };

  // time-picker
  const format = 'HH:mm';

  const onChange2 = (value: any) => {
    const formattedTime = value.format('HH:mm:ss.265');
    setToday2(formattedTime);
    console.log('2', formattedTime);
  };

  useEffect(() => {
    setStarttime(`${today1}T${today2}Z`);
  }, [starttime]);

  // 게시글 등록
  const handleOnSubmit = useCallback(
    async (values: any) => {
      const timeFormValues = timeForm.getFieldsValue();
      const contentFormValues = contentForm.getFieldsValue();

      let formData = new FormData();
      if (values.images && values.images.length > 0) {
        formData.append('image', values.images[0].originFileObj);
      }

      const newPost = {
        ...values,
        d_board_id: parseInt(values.d_board_id),
        images: null,
        startTime: `${timeFormValues.activityDate.format(
          'YYYY-MM-DDT',
        )}${timeFormValues.startTime.format('HH:mm:ss.000')}Z`,
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
    [messageApi, navigate, createDealBoardsMutation, today1, today2],
  );

  const uploadButton = useMemo(() => {
    return <img width="100%" height="100%" src={dummyProfileImg} alt="+" />;
  }, []);

  return (
    <div>
      <div css={cssRegisterRequestPageStyle}>
        {contextHolder}
        <Steps current={current} items={steps} onChange={handleOnChangeStep} />
        <div css={cssRegisterRequestStepItemStyle(current === 0)}>
          <Form form={categoryForm}>
            <Form.Item label="카테고리" name="category">
              <Radio.Group>
                <Radio.Button value="심부름">심부름</Radio.Button>
                <Radio.Button value="교육">교육</Radio.Button>
                <Radio.Button value="돌봄">돌봄</Radio.Button>
                <Radio.Button value="청소,가사">청소,가사</Radio.Button>
                <Radio.Button value="수리,설치">수리,설치</Radio.Button>
                <Radio.Button value="이동">이동</Radio.Button>
              </Radio.Group>
            </Form.Item>
          </Form>
          <div className="control-box">
            <Button onClick={() => navigate(PATH.HOME)}>취소</Button>
            <Button type="primary" onClick={next}>
              다음 단계
            </Button>
          </div>
        </div>

        <div css={cssRegisterRequestStepItemStyle(current === 1)}>
          <Form
            form={timeForm}
            layout="horizontal"
            onValuesChange={handleOnChangeTime}
          >
            <Form.Item
              name="activityDate"
              label="활동일"
              initialValue={dayjs()}
            >
              <DatePicker format="YYYY년 MM월 DD일" />
            </Form.Item>

            <Form.Item
              name="startTime"
              label="활동을 시작할 시간"
              initialValue={dayjs()}
              rules={[{ required: true, message: '필수로 작성해주세요.' }]}
            >
              <DatePicker.TimePicker
                locale={locale}
                format="HH시 mm분"
                placeholder="시간"
                showNow={false}
                // 확인 버튼 누르지 않아도 값이 지정되도록 커스텀
                popupClassName="time-picker-no-footer"
                onSelect={(value) => {
                  timeForm.setFieldValue('rangeTime', value);
                  handleOnChangeTime(
                    { rangeTime: value },
                    timeForm.getFieldsValue(),
                  );
                }}
                minuteStep={30}
                allowClear={false}
              />
            </Form.Item>

            <Form.Item
              name="endTime"
              label="활동을 끝낼 시간"
              initialValue={dayjs()}
              rules={[{ required: true, message: '필수로 작성해주세요.' }]}
            >
              <DatePicker.TimePicker
                locale={locale}
                format="HH시 mm분"
                placeholder="시간"
                showNow={false}
                // 확인 버튼 누르지 않아도 값이 지정되도록 커스텀
                popupClassName="time-picker-no-footer"
                onSelect={(value) => {
                  timeForm.setFieldValue('rangeTime', value);
                  handleOnChangeTime(
                    { rangeTime: value },
                    timeForm.getFieldsValue(),
                  );
                }}
                minuteStep={30}
                allowClear={false}
              />
            </Form.Item>
            <div className="guide">
              <div>
                교환할 타임페이 양 :{' '}
                <b>{exchangeTimepay ? exchangeTimepay + ' TP' : ''}</b>{' '}
              </div>
              <div>교환할 타임페이가 충분한지 확인해주세요.</div>
            </div>
          </Form>
          <div className="control-box">
            <Button onClick={prev}>이전 단계</Button>
            <Button type="primary" onClick={next}>
              다음 단계
            </Button>
          </div>
        </div>

        <div css={cssRegisterRequestStepItemStyle(current === 2)}>
          <Form form={contentForm}>
            <div className="form-info">
              어떤 활동을 했는지 간략하게 적어주세요.
            </div>

            <Form.Item
              name="content"
              label="활동 내용"
              rules={[{ required: true, message: '필수로 작성해주세요.' }]}
            >
              <Input.TextArea rows={5} maxLength={100} showCount />
            </Form.Item>
            <div className="guide">
              활동 내용은 100자 내로 작성해주세요 <br />
              활동 내용에는 다음과 같은 내용들을 넣으면 좋아요. <br />
              <ul>
                <li>장소</li>
                <li>어떤 도움에 대한 내용인지</li>
                <li>도움 활동 중 특이사항이 있었는지</li>
                <li>도움 활동 후 소감</li>
              </ul>
            </div>
          </Form>
          <div className="control-box">
            <Button onClick={prev}>이전 단계</Button>
            <Button type="primary" onClick={handleOnSubmit}>
              작성 완료
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterRequestPage;
