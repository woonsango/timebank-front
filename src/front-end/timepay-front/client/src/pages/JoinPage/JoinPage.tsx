import React, { useState, useEffect, useCallback } from 'react';
import {
  Button,
  Form,
  Input,
  Select,
  Typography,
  Space,
  message,
  Image,
} from 'antd';

import { MONTH } from './Data/months';
import { DAY } from './Data/days';
import { siData } from './Data/SIDATA';
import { guData } from './Data/GUDATA';
import { dongData } from './Data/DONGDATA';

import type { DatePickerProps } from 'antd';
import { DatePicker } from 'antd';
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import './Join_imageSet.css';

import axios from 'axios';

/*행정동 타입 선언*/
type DongName = keyof typeof dongData;

/*수직 수평 중앙 정렬*/
const topWrapperCSS = css`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
`;

/*서버로부터*/
const getUid = async () => {
  axios({
    method: 'post',
    url: 'http://localhost:8080/join',
    data: 'please uid!',
  })
    .then((res) => {
      console.log('서버의 응답', res); //서버 응답
      const response = res.data;
      const code = response.data;
      console.log('response: ', code);
    })
    .catch((err) => {
      console.log('카카오 로그인 에러', err);
    });
};

const JoinPage = () => {
  /*서버에게*/
  const postUserInfo = async () => {
    axios({
      method: 'post',
      url: 'http://localhost:8080/info',
      data: { nickname: year, test: year },
    })
      .then((res) => {
        console.log('서버의 응답', res); //서버 응답
        const response = res.data;

        console.log('response: ', response);
      })
      .catch((err) => {
        console.log('카카오 로그인 에러', err);
      });
  };

  useEffect(() => {
    getUid();
    postUserInfo();
  }, []);

  const { Text } = Typography;
  const [messageApi, contextHolder] = message.useMessage();

  const [realName, setRealName] = useState('');
  const [nickName, setNickName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [profileImage, setProfileImage]: any = useState(
    'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
  );

  const [year, setYear] = useState<string>('연도');
  const [month, setMonth] = useState<string>('월');

  const [day, setDay] = useState<string>('일');
  const [gu, setGu] = useState(dongData[guData[0]]);
  const [dong, setDong] = useState(dongData[guData[0]][0]);

  const handleFileChange = (e: any) => {
    const imageFile = e.target.files[0];
    const last = imageFile;
    if (imageFile !== null) {
      setProfileImage(imageFile);
    } else {
      setProfileImage(
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
      );
      return;
    }

    //프로필 사진 시각화
    const fileReader = new FileReader();
    fileReader.onload = () => {
      if (fileReader.readyState == 2) {
        if (fileReader.result !== null) {
          setProfileImage(fileReader.result);
        }
      }
    };
    fileReader.readAsDataURL(imageFile); //setImage
  };

  /*onChange*/
  const onChangeRealName = (value: any) => {
    setRealName(value);
  };
  const onChangeNickName = (value: any) => {
    setNickName(value);
  };

  const onChangePhoneNumber = (value: any) => {
    setPhoneNumber(value);
  };

  const onChangeIntroduction = (value: any) => {
    setIntroduction(value);
  };

  const onChangeProfileImage = (value: any) => {
    setProfileImage(value);
  };

  const onChangeYear: DatePickerProps['onChange'] = (date, dateString) => {
    console.log(date, dateString);
    setYear(dateString);
  };

  const onChangeMonth = (value: string) => {
    setMonth(value);
  };

  const onChangeDay = (value: string) => {
    setDay(value);
  };

  const onChangeGu = (value: DongName) => {
    setGu(dongData[value]);
    //setDong(dongData[value][0]);
  };

  const onChangeDong = (value: DongName) => {
    setDong(value);
  };

  /* 닉네임 유효성 검사 커스텀 */
  const rightNickname = (_: any, value: string) => {
    const nickname_regExp = /^[a-zA-Zㄱ-힣0-9]{2,16}$/;
    if (!value) {
      return Promise.reject(new Error('닉네임을 입력해 주세요.'));
    } else if (value.search(/\s/) != -1) {
      return Promise.reject(new Error('닉네임을 공백 없이 입력해 주세요.'));
    }
    if (!nickname_regExp.test(value)) {
      return Promise.reject(
        new Error(
          '닉네임은 한글, 영어, 숫자를 조합하여 2자 이상 16자 이하로 입력해 주세요.',
        ),
      );
    }
    return Promise.resolve();
  };

  /*이름 검사 커스텀 */
  const rightRealname = (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('이름을 입력해 주세요.'));
    }
    return Promise.resolve();
  };

  /* 전화번호 유효성 검사 커스텀 */
  const rightPhoneNumber = (_: any, value: string) => {
    const phone_regex = /^[0-9\b -]{9,11}$/;
    if (!value) {
      return Promise.reject(new Error('전화번호를 입력해 주세요.'));
    } else if (value.search(/\s/) != -1) {
      return Promise.reject(new Error('공백 없이 입력해 주세요.'));
    }
    if (value.length < 9 || value.length > 11) {
      return Promise.reject(new Error('올바른 전화번호를 입력해 주세요.'));
    }
    if (!phone_regex.test(value)) {
      return Promise.reject(
        new Error('전화번호는 -없이 숫자만 입력해 주세요.'),
      );
    }
    return Promise.resolve();
  };

  /*지역, 생년월일 null check*/
  const warning = (value: string) => {
    messageApi.open({
      type: 'warning',
      content: value + '을 입력해 주세요.',
    });
  };

  /*Handle 가입 완료 Btn*/
  const handleSubmitBtn = () => {
    console.log(dong);
    if (year === '연도' || month === '월' || day === '일') {
      warning('생년월일');
    }
    if (gu === dongData[guData[0]] || dong === '동') {
      warning('지역');
    } else {
      GoToFinishJoinPage(PATH.FINISHJOIN);
    }
  };

  /*From Check*/
  const onFinishJoin = () => {
    console.log('회원가입 성공!');
  };

  const onFinishFailedJoin = () => {
    console.log('회원가입 실패!');
  };

  const navigate = useNavigate(); //history

  const GoToFinishJoinPage = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <Space css={topWrapperCSS} align="baseline">
      {contextHolder}
      <Form
        name="JoinPage"
        onFinish={onFinishJoin}
        onFinishFailed={onFinishFailedJoin}
      >
        <Text
          css={css`
            font-size: 30px;
            font-weight: bold;
          `}
        >
          회원가입
        </Text>
        <Form.Item
          name="profileImage"
          style={{ textAlign: 'center', marginTop: 30 }}
        >
          <Space direction="vertical">
            <Image
              src={profileImage}
              width={100}
              height={100}
              className="EditprofileImage"
            />
            <div className="EditprofileImageWrap">
              <label htmlFor="image_guide">
                프로필 사진 설정하기
                <input
                  type="file"
                  id="image_guide"
                  src={profileImage}
                  accept="image/*"
                  onChange={handleFileChange}
                />
              </label>
            </div>
          </Space>
        </Form.Item>

        <Form.Item
          label="닉네임"
          name="nickName"
          style={{ marginTop: 60 }}
          rules={[{ validator: rightNickname }]}
        >
          <Input onChange={onChangeNickName} />
        </Form.Item>

        <Form.Item
          label="이름"
          name="realName"
          rules={[{ validator: rightRealname }]}
        >
          <Input onChange={onChangeRealName} />
        </Form.Item>

        <Form.Item label="생년월일" name="Birth">
          <Space>
            <DatePicker onChange={onChangeYear} picker="year" />
            <Select
              defaultValue={month}
              onChange={onChangeMonth}
              options={MONTH.map((month) => ({
                label: month,
                value: month,
              }))}
              style={{ width: 100 }}
            />

            <Select
              defaultValue={day}
              onChange={onChangeDay}
              options={DAY.map((day) => ({
                label: day,
                value: day,
              }))}
              style={{ width: 100 }}
            />
          </Space>
        </Form.Item>

        <Form.Item
          label="전화번호"
          name="phone"
          rules={[{ validator: rightPhoneNumber }]}
        >
          <Input onChange={onChangePhoneNumber} />
        </Form.Item>

        <Form.Item label="지역" name="Town">
          {' '}
          <Space align="baseline">
            <Select
              defaultValue={siData[0]}
              options={siData.map((si) => ({
                label: si,
                value: si,
              }))}
            />

            <Select
              defaultValue={'구'}
              onChange={onChangeGu}
              options={guData.map((province) => ({
                label: province,
                value: province,
              }))}
              style={{ width: 100 }}
            />

            <Select
              value={dong as DongName}
              onChange={onChangeDong}
              options={gu.map((city) => ({ label: city, value: city }))}
              style={{ width: 100 }}
            />
          </Space>
        </Form.Item>

        <Form.Item
          label="자신을 소개해 주세요"
          name="introduction"
          rules={[{ required: false, message: '' }]}
        >
          <Input onChange={onChangeIntroduction} />
        </Form.Item>

        <Form.Item name="submit" style={{ textAlign: 'center' }}>
          <Button
            type="primary"
            onClick={handleSubmitBtn}
            htmlType="submit"
            css={css`
              font-size: 17px;
              width: 180px;
              height: 35px;
              margin-bottom: 40px;
            `}
          >
            가입 완료
          </Button>
        </Form.Item>
      </Form>
    </Space>
  );
};

export default JoinPage;
