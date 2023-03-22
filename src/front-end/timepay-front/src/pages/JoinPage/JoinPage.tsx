import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Form, Input, Select, Typography } from 'antd';

import './Join.css';

import type { DatePickerProps } from 'antd';
import { DatePicker } from 'antd';
import { css } from '@emotion/react';

import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import { message, Upload } from 'antd';
import type { UploadChangeParam } from 'antd/es/upload';
import type { RcFile, UploadFile, UploadProps } from 'antd/es/upload/interface';

const MONTH = [
  '01',
  '02',
  '03',
  '04',
  '05',
  '06',
  '07',
  '08',
  '09',
  '10',
  '11',
  '12',
];
const DAY = [
  '01',
  '02',
  '03',
  '04',
  '05',
  '06',
  '07',
  '08',
  '09',
  '10',
  '11',
  '12',
  '13',
  '14',
  '15',
  '16',
  '17',
  '18',
  '19',
  '20',
  '21',
  '22',
  '23',
  '24',
  '25',
  '26',
  '27',
  '28',
  '29',
  '30',
  '31',
];

const siData = ['서울특별시'];

const guData = [
  '구',
  '강남구',
  '강동구',
  '강북구',
  '강서구',
  '관악구',
  '광진구',
  '구로구',
  '금천구',
  '노원구',
  '도봉구',
  '동대문구',
  '동작구',
  '마포구',
  '서대문구',
  '서초구',
  '성동구',
  '성북구',
  '송파구',
  '양천구',
  '영등포구',
  '용산구',
  '은평구',
  '종로구',
  '중구',
  '중랑구',
] as const;

const dongData = {
  구: ['동'],
  강남구: [
    '신사동',
    '논현1동',
    '논현2동',
    '삼성1동',
    '삼성2동',
    '대치1동',
    '대치2동',
    '대치4동',
    '역삼1동',
    '역삼2동',
    '도곡1동',
    '도곡2동',
    '개포1동',
    '개포2동',
    '개포4동',
    '일원본동',
    '일원1동',
    '일원2동',
    '수서동',
    '세곡동',
    '압구정동',
    '청담동',
  ],
  강동구: [
    '강일동',
    '명일1동',
    '명일2동',
    '고덕1동',
    '고덕2동',
    '암사1동',
    '암사2동',
    '암사3동',
    '천호1동',
    '천호2동',
    '천호3동',
    '성내1동',
    '성내2동',
    '성내3동',
    '길동',
    '둔촌1동',
    '둔촌2동',
    '상일1동',
    '상일2동',
  ],
  강북구: [
    '번1동',
    '번2동',
    '번3동',
    '수유1동',
    '수유2동',
    '수유3동',
    '삼양동',
    '미아동',
    '송중동',
    '송천동',
    '삼각산동',
    '우이동',
    '인수동',
  ],
  강서구: [
    '염창동',
    '등촌1동',
    '등촌2동',
    '등촌3동',
    '화곡본동',
    '화곡2동',
    '화곡3동',
    '화곡4동',
    '화곡6동',
    '화곡8동',
    '가양1동',
    '가양2동',
    '가양3동',
    '발산1동',
    '공항동',
    '방화1동',
    '방화2동',
    '방화3동',
    '화곡1동',
    '우장산동',
  ],
  관악구: [
    '보라매동',
    '청림동',
    '행운동',
    '낙성대동',
    '중앙동',
    '인헌동',
    '남현동',
    '서원동',
    '신원동',
    '서림동',
    '신사동',
    '신림동',
    '난향동',
    '조원동',
    '대학동',
    '은천동',
    '성현동',
    '청룡동',
    '난곡동',
    '삼성동',
    '미성동',
  ],
  광진구: [
    '화양동',
    '군자동',
    '중곡1동',
    '중곡2동',
    '중곡3동',
    '중곡4동',
    '능동',
    '구의1동',
    '구의2동',
    '구의3동',
    '광장동',
    '자양1동',
    '자양2동',
    '자양3동',
    '자양4동',
  ],
  구로구: [
    '신도림동',
    '구로1동',
    '구로2동',
    '구로3동',
    '구로4동',
    '구로5동',
    '고척1동',
    '고척2동',
    '개봉2동',
    '개봉3동',
    '오류1동',
    '오류2동',
    '수궁동',
    '가리봉동',
    '개봉1동',
    '항동',
  ],
  금천구: [
    '가산동',
    '독산1동',
    '독산2동',
    '독산3동',
    '독산4동',
    '시흥1동',
    '시흥2동',
    '시흥3동',
    '시흥4동',
    '시흥5동',
  ],
  노원구: [
    '월계1동',
    '월계2동',
    '월계3동',
    '공릉2동',
    '하계1동',
    '하계2동',
    '중계본동',
    '중계1동',
    '중계4동',
    '상계1동',
    '상계2동',
    '상계5동',
    '상계8동',
    '상계9동',
    '상계10동',
    '상계3.4동',
    '상계6.7동',
    '중계2.3동',
    '공릉1동',
  ],
  도봉구: [
    '쌍문1동',
    '쌍문2동',
    '쌍문3동',
    '쌍문4동',
    '방학1동',
    '방학2동',
    '방학3동',
    '창1동',
    '창2동',
    '창3동',
    '창4동',
    '창5동',
    '도봉1동',
    '도봉2동',
  ],
  동대문구: [
    '회기동',
    '휘경1동',
    '휘경2동',
    '청량리동',
    '용신동',
    '제기동',
    '전농1동',
    '전농2동',
    '답십리1동',
    '답십리2동',
    '장안1동',
    '장안2동',
    '이문1동',
    '이문2동',
  ],
  동작구: [
    '노량진2동',
    '상도1동',
    '상도2동',
    '상도3동',
    '상도4동',
    '사당1동',
    '사당3동',
    '사당4동',
    '사당5동',
    '대방동',
    '신대방1동',
    '신대방2동',
    '흑석동',
    '노량진1동',
    '사당2동',
  ],
  마포구: [
    '용강동',
    '대흥동',
    '염리동',
    '신수동',
    '서교동',
    '합정동',
    '망원1동',
    '망원2동',
    '연남동',
    '성산1동',
    '성산2동',
    '상암동',
    '도화동',
    '서강동',
    '공덕동',
    '아현동',
  ],
  서대문구: [
    '천연동',
    '홍제1동',
    '홍제2동',
    '홍제3동',
    '홍은1동',
    '홍은2동',
    '남가좌1동',
    '남가좌2동',
    '북가좌1동',
    '북가좌2동',
    '충현동',
    '북아현동',
    '신촌동',
    '연희동',
  ],
  서초구: [
    '서초1동',
    '서초2동',
    '서초3동',
    '서초4동',
    '잠원동',
    '반포본동',
    '반포1동',
    '반포2동',
    '반포3동',
    '반포4동',
    '방배본동',
    '방배1동',
    '방배2동',
    '방배3동',
    '방배4동',
    '양재1동',
    '양재2동',
    '내곡동',
  ],
  성동구: [
    '왕십리2동',
    '마장동',
    '사근동',
    '행당1동',
    '행당2동',
    '응봉동',
    '금호1가동',
    '금호4가동',
    '성수1가1동',
    '성수1가2동',
    '성수2가1동',
    '성수2가3동',
    '송정동',
    '용답동',
    '왕십리도선동',
    '금호2.3가동',
    '옥수동',
  ],
  성북구: [
    '돈암1동',
    '돈암2동',
    '안암동',
    '보문동',
    '정릉1동',
    '정릉2동',
    '정릉3동',
    '정릉4동',
    '길음1동',
    '길음2동',
    '월곡1동',
    '월곡2동',
    '장위1동',
    '장위2동',
    '장위3동',
    '성북동',
    '삼선동',
    '동선동',
    '종암동',
    '석관동',
  ],
  송파구: [
    '풍납1동',
    '풍납2동',
    '거여1동',
    '거여2동',
    '마천1동',
    '마천2동',
    '방이1동',
    '방이2동',
    '오륜동',
    '오금동',
    '송파1동',
    '송파2동',
    '석촌동',
    '삼전동',
    '가락본동',
    '가락1동',
    '가락2동',
    '문정1동',
    '문정2동',
    '장지동',
    '위례동',
    '잠실본동',
    '잠실2동',
    '잠실3동',
    '잠실4동',
    '잠실6동',
    '잠실7동',
  ],
  양천구: [
    '목1동',
    '목2동',
    '목3동',
    '목4동',
    '목5동',
    '신월1동',
    '신월2동',
    '신월3동',
    '신월4동',
    '신월5동',
    '신월6동',
    '신월7동',
    '신정1동',
    '신정2동',
    '신정4동',
    '신정3동',
    '신정6동',
    '신정7동',
  ],
  영등포구: [
    '여의동',
    '당산1동',
    '당산2동',
    '양평1동',
    '양평2동',
    '신길1동',
    '신길3동',
    '신길4동',
    '신길5동',
    '신길6동',
    '신길7동',
    '대림1동',
    '대림2동',
    '대림3동',
    '영등포본동',
    '영등포동',
    '도림동',
    '문래동',
  ],
  용산구: [
    '후암동',
    '용산2가동',
    '남영동',
    '원효로2동',
    '효창동',
    '용문동',
    '이촌1동',
    '이촌2동',
    '이태원1동',
    '이태원2동',
    '서빙고동',
    '보광동',
    '청파동',
    '원효로1동',
    '한강로동',
    '한남동',
  ],
  은평구: [
    '녹번동',
    '불광1동',
    '갈현1동',
    '갈현2동',
    '구산동',
    '대조동',
    '응암1동',
    '응암2동',
    '신사1동',
    '신사2동',
    '증산동',
    '수색동',
    '진관동',
    '불광2동',
    '응암3동',
    '역촌동',
  ],
  종로구: [
    '사직동',
    '삼청동',
    '부암동',
    '평창동',
    '무악동',
    '교남동',
    '가회동',
    '종로1.2.3.4가동',
    '종로5.6가동',
    '이화동',
    '혜화동',
    '창신1동',
    '창신2동',
    '창신3동',
    '숭인1동',
    '숭인2동',
    '청운효자동',
  ],
  중구: [
    '소공동',
    '회현동',
    '명동',
    '필동',
    '장충동',
    '광희동',
    '을지로동',
    '신당5동',
    '황학동',
    '중림동',
    '신당동',
    '다산동',
    '약수동',
    '청구동',
    '동화동',
  ],
  중랑구: [
    '면목2동',
    '면목4동',
    '면목5동',
    '면목7동',
    '상봉1동',
    '상봉2동',
    '중화1동',
    '중화2동',
    '묵1동',
    '묵2동',
    '망우3동',
    '신내1동',
    '신내2동',
    '면목본동',
    '면목3.8동',
    '망우본동',
  ],
};

type DongName = keyof typeof dongData;

//이미지 업로드
const getBase64 = (img: RcFile, callback: (url: string) => void) => {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result as string));
  reader.readAsDataURL(img);
};

const beforeUpload = (file: RcFile) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('You can only upload JPG/PNG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJpgOrPng && isLt2M;
};

//회원가입 타이틀
const { Title } = Typography;

const JoinPage = () => {
  //이름, 닉네임, 지역, 전화번호(필)
  //프로필 이미지, 소개글(선)
  const [realName, setRealName] = useState('');
  const [nickName, setNickName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('');

  const [text, setText] = useState<any>();

  const handleTextChange = (e: any) => {
    setText(e.target.value);
  };

  //가입 완료 버튼 누르면, 서버에 사용자 정보 보내고, 홈 화면으로 이동
  const navigate = useNavigate(); //history

  const handlePageMove = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const onFinish = (values: any) => {
    console.log('Success:', values);
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };

  const [form] = Form.useForm();

  //이미지 업로드
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>();

  const handleChange: UploadProps['onChange'] = (
    info: UploadChangeParam<UploadFile>,
  ) => {
    if (info.file.status === 'uploading') {
      setLoading(true);
      return;
    }
    if (info.file.status === 'done') {
      // Get this url from response in real world.
      getBase64(info.file.originFileObj as RcFile, (url) => {
        setLoading(false);
        setImageUrl(url);
      });
    }
  };

  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>Upload</div>
    </div>
  );

  const handleOnFinish = (values: any) => {
    console.log('Success:', values);
  };

  const [cities, setCities] = useState(dongData[guData[0]]);
  const [secondCity, setSecondCity] = useState(dongData[guData[0]][0]);

  const handleProvinceChange = (value: DongName) => {
    setCities(dongData[value]);
    setSecondCity(dongData[value][0]);
  };

  const onSecondCityChange = (value: DongName) => {
    setSecondCity(value);
  };

  const onChange: DatePickerProps['onChange'] = (date, dateString) => {
    console.log(date, dateString);
  };

  const rightTown = useCallback((_: any, value: string) => {
    if (value === '구') {
      return Promise.reject(new Error(''));
    }
  }, []);

  return (
    <div>
      <Title
        level={2}
        css={css`
          padding: 20px;
        `}
      >
        회원가입
      </Title>

      <Form.Item name="profileImage" style={{ textAlign: 'center' }}>
        <Upload
          name="avatar"
          listType="picture-circle"
          className="avatar-uploader"
          showUploadList={false}
          action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          beforeUpload={beforeUpload}
          onChange={handleChange}
        >
          {imageUrl ? (
            <img src={imageUrl} alt="avatar" style={{ width: '100%' }} />
          ) : (
            uploadButton
          )}
        </Upload>
      </Form.Item>

      <Form
        name="join"
        style={{}}
        css={css`
          padding: 30px;
        `}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="이름"
          name="username"
          rules={[{ required: true, message: '이름을 입력해 주세요.' }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="닉네임"
          name="nickname"
          rules={[{ required: true, message: '닉네임을 입력해 주세요.' }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="지역"
          name="town"
          style={{}}
          rules={[{ validator: rightTown }]}
        >
          <Select
            style={{ width: 120, textAlign: 'center' }}
            //onChange={handleProvinceChange}
            defaultValue={siData[0]}
            options={siData.map((province) => ({
              label: province,
              value: province,
            }))}
          />
          <Select
            defaultValue={guData[0]}
            style={{ width: 120, textAlign: 'center' }}
            onChange={handleProvinceChange}
            options={guData.map((province) => ({
              label: province,
              value: province,
            }))}
          />
          <Select
            style={{ width: 120, textAlign: 'center' }}
            value={secondCity as DongName}
            onChange={onSecondCityChange}
            options={cities.map((city) => ({ label: city, value: city }))}
          />
        </Form.Item>

        <Form.Item
          label="생년월일"
          name="birth"
          rules={[{ required: true, message: '생년월일을 선택해 주세요.' }]}
        >
          <DatePicker onChange={onChange} picker="year" />
          <Select
            style={{ width: 120 }}
            placeholder="월"
            //onChange={handleProvinceChange}
            options={MONTH.map((month) => ({
              label: month,
              value: month,
            }))}
          />
          <Select
            style={{ width: 120 }}
            placeholder="일"
            //onChange={handleProvinceChange}
            options={DAY.map((day) => ({
              label: day,
              value: day,
            }))}
          />
        </Form.Item>

        <Form.Item
          label="전화번호"
          name="phone"
          rules={[
            { required: true, message: '전화번호를 입력해 주세요.' },
            { type: 'number', message: '숫자만 입력해 주세요.' },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="자신을 소개해 주세요"
          name="introduction"
          rules={[{ required: false, message: '' }]}
        >
          <Input />
        </Form.Item>

        <Form.Item style={{ textAlign: 'center' }}>
          <Button
            type="primary"
            htmlType="submit"
            css={css`
              font-size: 20px;
              width: 200px;
              height: 40px;
            `}
          >
            Submit
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default JoinPage;
