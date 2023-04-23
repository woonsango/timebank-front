import React, { useState, useEffect, useCallback } from 'react';
import { Button, Form, Input, Select, Space, message, Image } from 'antd';
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { headerTitleState } from '../../states/uiState';
import { useSetRecoilState } from 'recoil';

import { siData } from '../JoinPage/Data/SIDATA';
import { guData } from '../JoinPage/Data/GUDATA';
import { dongData } from '../JoinPage/Data/DONGDATA';
import { PATH } from '../../utils/paths';
import user from './dummy.json';
import { COMMON_COLOR } from '../../styles/constants/colors';
import './MyEdit_imageSet.css';

/*행정동 타입 선언*/
type DongName = keyof typeof dongData;

/*수직 수평 중앙 정렬*/
const topWrapperCSS = css`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80vh;
`;

const MyEditPage: React.FC = () => {
  const userInfo = user.user1[0];

  const [messageApi, contextHolder] = message.useMessage();

  const [profileImage, setProfileImage]: any = useState(userInfo.img);
  const [nickName, setNickName] = useState<string>('test');
  const [day, setDay] = useState<string>('일');
  const [gu, setGu] = useState(dongData[guData[0]]);
  const [userGu, setUserGu] = useState<string>('구테스트');
  const [userDong, setUserDong] = useState<string>('동테스트');
  const [dong, setDong] = useState(dongData[guData[0]][0]);
  const [introduction, setIntroduction]: any = useState(userInfo.introduction);

  const handleFileChange = (e: any) => {
    const imageFile = e.target.files[0];
    console.log(imageFile);

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

  const onChangeProfileImage = (value: any) => {
    setProfileImage(value);
  };

  const onChangeNickName = (value: any) => {
    setNickName(value);
  };

  const onChangeGu = (val: DongName) => {
    setGu(dongData[val]);
    const gu: string = val.valueOf();
    setUserGu(gu);
  };
  const onChangeDong = (val: DongName) => {
    setDong(val);
    const dong: string = val.valueOf();
    setUserDong(dong);
  };

  const onChangeIntroduction = (value: any) => {
    setIntroduction(value);
  };

  /* 닉네임 유효성 검사 커스텀 */
  const rightNickname = (_: any, value: string) => {
    const nickname_regExp = /^[a-zA-Zㄱ-힣0-9]{2,16}$/;
    if (nickName) {
      //닉네임 수정없이 기존 닉네임 그대로
      return Promise.resolve();
    } else if (!value) {
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

  /*지역 null check*/
  const warning = (value: string) => {
    messageApi.open({
      type: 'warning',
      content: value + '을 입력해 주세요.',
    });
  };

  /*Handle 가입 완료 Btn*/
  const handleSubmitBtn = () => {
    console.log(dong);
    if (gu === dongData[guData[0]] || dong === '동') {
      warning('지역');
    } else {
      handlePageMove(PATH.MY);
    }
  };

  /*From Check*/
  const onFinishJoin = () => {
    console.log('수정 성공!');
  };

  const onFinishFailedJoin = () => {
    console.log('수정 실패!');
  };

  const navigate = useNavigate(); //history

  const handlePageMove = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('내 정보 수정');
    /*
    fetch('http://localhost:5000/user')
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setUserData(data);
      });*/
    //setProfileImage(userInfo.img);
    setNickName(userInfo.nickName);
    setIntroduction(userInfo.introduction);
  }, []);

  return (
    <Space css={topWrapperCSS} align="baseline">
      {contextHolder}
      <Form
        name="EditMyPage"
        onFinish={onFinishJoin}
        onFinishFailed={onFinishFailedJoin}
      >
        <Form.Item name="profileImage" style={{ textAlign: 'center' }}>
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
          <Input onChange={onChangeNickName} defaultValue={nickName} />
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
          <Input
            onChange={onChangeIntroduction}
            defaultValue={userInfo.introduction}
          />
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
            수정 완료
          </Button>
        </Form.Item>
      </Form>
    </Space>
  );
};

export default MyEditPage;
