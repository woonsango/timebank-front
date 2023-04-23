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
  Modal,
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

const profileCSS = css`
  height: max-content;
`;

const JoinPage = () => {
  const { Text } = Typography;
  const [messageApi, contextHolder] = message.useMessage();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [profileImage, setProfileImage]: any = useState(
    'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
  );
  const [nickName, setNickName] = useState<string>('');
  const [realName, setRealName] = useState<string>('');
  const [phoneNumber, setPhoneNumber] = useState<string>('');
  const [introduction, setIntroduction] = useState<string>('');
  const [id, setId] = useState<string>('0');

  const [year, setYear] = useState<string>('연도');
  const [month, setMonth] = useState<string>('월');
  const [day, setDay] = useState<string>('일');

  const [gu, setGu] = useState(dongData[guData[0]]);
  const [dong, setDong] = useState(dongData[guData[0]][0]);

  const [guText, setGuText] = useState<string>('');
  const [dongText, setDongText] = useState<string>('');

  const handleFileChange = (e: any) => {
    const imageFile = e.target.files[0];
    console.log(imageFile);

    const fileReader = new FileReader();
    fileReader.onload = () => {
      if (fileReader.readyState === 2) {
        if (fileReader.result !== null) {
          setProfileImage(fileReader.result);
        }
      }
    };
    fileReader.readAsDataURL(imageFile); //setImage
  };

  /*onChange*/
  // const onChangeProfileImage = (value: any) => {
  //   setProfileImage(value);
  // };

  const onChangeNickName = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log('닉네임 바뀜:', e.target.value);
    setNickName(e.target.value);
  };

  const onChangeRealName = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log('이름 바뀜:', e.target.value);
    setRealName(e.target.value);
  };

  const onChangeGu = (value: DongName) => {
    setGu(dongData[value]);
    console.log('구 바뀜: ', value.valueOf());
    setGuText(value.valueOf());
  };

  const onChangeDong = (value: DongName) => {
    setDong(value);
    console.log('동 바뀜: ', value.valueOf());
    setDongText(value.valueOf());
  };

  const onChangeYear: DatePickerProps['onChange'] = (date, dateString) => {
    //console.log(date, dateString);
    setYear(dateString);
  };

  const onChangeMonth = (value: string) => {
    setMonth(value);
  };

  const onChangeDay = (value: string) => {
    setDay(value);
  };

  const onChangePhoneNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log('전화번호 바뀜:', e.target.value);
    setPhoneNumber(e.target.value);
  };

  const onChangeIntroduction = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log('자기소개 바뀜:', e.target.value);
    setIntroduction(e.target.value);
  };

  /* 닉네임 유효성 검사 커스텀 */
  const nickname_regExp = /^[a-zA-Zㄱ-힣0-9]{2,16}$/;
  const rightNickname = (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('닉네임을 입력해 주세요.'));
    } else if (value.search(/\s/) !== -1) {
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
  const realname_regExp = /^[a-zA-Zㄱ-힣]{0,20}$/;
  const rightRealname = (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('이름을 입력해 주세요.'));
    }
    if (!realname_regExp.test(value)) {
      return Promise.reject(new Error('이름은 한글 또는 영어로 입력해주세요.'));
    }
    return Promise.resolve();
  };

  /* 전화번호 유효성 검사 커스텀 */
  const phone_regex = /^[0-9\b -]{9,11}$/;
  const rightPhoneNumber = (_: any, value: string) => {
    if (!value) {
      return Promise.reject(new Error('전화번호를 입력해 주세요.'));
    } else if (value.search(/\s/) !== -1) {
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
  const handleSubmitBtn = async () => {
    /* 닉네임, 이름, 생년월일, 전화번호를 필수값으로 검사*/
    if (!nickName || !nickname_regExp.test(nickName)) {
      console.log('가입 완료 제출: 닉네임 형식 부적합 ');
    } else if (!realName || !realname_regExp.test(realName)) {
      console.log('가입 완료 제출: 이름 형식 부적합');
    } else if (year === '연도' || month === '월' || day === '일') {
      console.log('가입 완료 제출: 생년월일 형식 부적합');
      warning('생년월일');
    } else if (
      !phoneNumber ||
      phoneNumber.length < 9 ||
      phoneNumber.length > 11 ||
      !phone_regex.test(phoneNumber)
    ) {
      console.log('가입 완료 제출: 전화번호 형식 부적합');
    } else {
      console.log('가입 완료 제출: 조건 충족, 가입 완료');

      /*formData*/
      const birth: string = year + month + day + '0000';
      const town: string = '서울특별시 ' + guText + ' ' + dongText;
      const formData = new FormData();
      formData.append('birthday', birth);
      formData.append('id', id);
      formData.append('imageUrl', profileImage);
      formData.append('introduction', introduction);
      formData.append('location', town);
      formData.append('name', realName);
      formData.append('nickName', nickName);
      formData.append('phone', phoneNumber);

      console.log(profileImage);

      //formData 모든 값 출력
      for (var value of formData.values()) {
        console.log(value);
      }

      await axios({
        method: 'post',
        url: 'http://13.125.119.30/api/users/create',
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        data: formData,
      })
        .then((result) => {
          console.log(result);
        })
        .catch((error) => {
          console.log(error);
        });

      goToFinishJoinPage(PATH.FINISHJOIN);
    }
  };

  const navigate = useNavigate(); //history

  const goToFinishJoinPage = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const handleImageSelect1 = () => {
    setProfileImage(
      'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
    );
  };

  const handleImageSelect2 = () => {
    setProfileImage(
      'https://image.ytn.co.kr/general/jpg/2022/1223/202212231020527831_d.jpg',
    );
  };

  const handleImageSelect3 = () => {
    setProfileImage(
      'https://images.pexels.com/photos/1166869/pexels-photo-1166869.jpeg',
    );
  };

  const handleImageSelect4 = () => {
    setProfileImage(
      'https://images.pexels.com/photos/3361739/pexels-photo-3361739.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    );
  };

  const handleImageSelect5 = () => {
    setProfileImage(
      'https://images.pexels.com/photos/1661179/pexels-photo-1661179.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    );
  };

  return (
    <Space css={topWrapperCSS} align="baseline">
      {contextHolder}
      <Form name="JoinPage">
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
              <Button type="primary" onClick={showModal}>
                프로필 사진 설정하기
              </Button>
              <Modal
                title="프로필 사진 선택"
                open={isModalOpen}
                onOk={handleOk}
                onCancel={handleCancel}
              >
                <Button block onClick={handleImageSelect1} css={profileCSS}>
                  <img
                    width={100}
                    height="auto"
                    src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                  />
                </Button>
                <Button block onClick={handleImageSelect2} css={profileCSS}>
                  {
                    <img
                      width={100}
                      height="auto"
                      src="https://image.ytn.co.kr/general/jpg/2022/1223/202212231020527831_d.jpg"
                    />
                  }
                </Button>
                <Button block onClick={handleImageSelect3} css={profileCSS}>
                  {
                    <img
                      width={100}
                      height="auto"
                      src="https://images.pexels.com/photos/1166869/pexels-photo-1166869.jpeg"
                    />
                  }
                </Button>
                <Button block onClick={handleImageSelect4} css={profileCSS}>
                  {
                    <img
                      width={100}
                      height="auto"
                      src="https://images.pexels.com/photos/3361739/pexels-photo-3361739.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                    />
                  }
                </Button>
                <Button block onClick={handleImageSelect5} css={profileCSS}>
                  {
                    <img
                      width={100}
                      height="auto"
                      src="https://images.pexels.com/photos/1661179/pexels-photo-1661179.jpeg"
                    />
                  }
                </Button>
              </Modal>
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
          label="전화번호(-없이 숫자로만 입력)"
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
