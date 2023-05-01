import React, { useState, useEffect, useCallback } from 'react';
import {
  Button,
  Form,
  Input,
  Select,
  Space,
  message,
  Image,
  Typography,
} from 'antd';
import { useNavigate } from 'react-router-dom';
import { headerTitleState } from '../../states/uiState';
import { useSetRecoilState } from 'recoil';

import { siData } from '../JoinPage/Data/SIDATA';
import { guData } from '../JoinPage/Data/GUDATA';
import { dongData } from '../JoinPage/Data/DONGDATA';

import { PATH } from '../../utils/paths';
import './MyEdit_imageSet.css';
import { getTokenFromCookie } from '../../utils/token';
import axios from 'axios';
import {
  cssMyEditCenter,
  cssMyEditMargin,
  cssMyEditSubmitBtn,
  topWrapperCSS,
} from './MyEdit.styles';
import { cssJoinNick } from '../JoinPage/Join.styles';
import { overlap } from '../JoinPage/overlapNickname';

/*행정동 타입 선언*/
type DongName = keyof typeof dongData;

const MyEditPage: React.FC = () => {
  const [messageApi, contextHolder] = message.useMessage();
  const { Text } = Typography;

  const [profileImage, setProfileImage]: any = useState();
  const [finalProfileImage, setfinalProfileImage]: any = useState();

  const [nickName, setNickName] = useState<string>('');
  const [town, setTown] = useState<string>('');
  const [introduction, setIntroduction] = useState<string>('');

  const [gu, setGu] = useState(dongData[guData[0]]);
  const [dong, setDong] = useState(dongData[guData[0]][0]);

  const [guText, setGuText] = useState<string>('');
  const [dongText, setDongText] = useState<string>('');

  const [viewNickname, setViewNickname] = useState<string>('');
  const [viewTown, setViewTown] = useState<string>('');
  const [viewIntroduction, setViewIntroduction] = useState<string>('');

  const handleFileChange = (e: any) => {
    const imageFile = e.target.files[0];

    const fileReader = new FileReader();
    fileReader.onload = () => {
      if (fileReader.readyState === 2) {
        if (fileReader.result !== null) {
          setProfileImage(fileReader.result);
          setfinalProfileImage(imageFile);
        }
      }
    };
    fileReader.readAsDataURL(imageFile);
  };

  const navigate = useNavigate(); //history

  const handlePageMove = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const onChangeNickName = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log('닉네임 바뀜:', e.target.value);
    setNickName(e.target.value);
  };

  const onChangeGu = (value: DongName) => {
    setGu(dongData[value]);
    setDong(dongData[guData[0]][0]);
    //console.log('구 바뀜: ', value.valueOf());
    setGuText(value.valueOf());
  };

  const onChangeDong = (value: DongName) => {
    setDong(value);
    //console.log('동 바뀜: ', value.valueOf());
    setDongText(value.valueOf());
  };

  const onChangeIntroduction = (e: React.ChangeEvent<HTMLInputElement>) => {
    //console.log('자기소개 바뀜:', e.target.value);
    setIntroduction(e.target.value);
  };

  /* 닉네임 유효성 검사 커스텀 */
  const rightNickname = (_: any, value: string) => {
    const nickname_regExp = /^[a-zA-Zㄱ-힣0-9]{2,16}$/;

    /*닉네임 수정없이 기존 닉네임 그대로*/
    if (nickName === viewNickname || value === '') {
      return Promise.resolve();
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
      content: value,
    });
  };

  /*닉네임 중복 검사*/
  const overlapNickname = () => {
    /*get*/
    //   axios
    //     .get('url 넣기', nickName)
    //     .then((res) => {
    //       console.log('닉네임 중복 검사 성공');
    //       console.log(res);
    //     })
    //     .catch((err) => {
    //       console.log('닉네임 중복 검사 실패');
    //     });
  };

  /*From Check*/
  const onFinishJoin = () => {
    console.log('수정 성공!');
  };

  const onFinishFailedJoin = () => {
    console.log('수정 실패!');
  };

  /*Handle 가입 완료 Btn*/
  const handleSubmitBtn = () => {
    const formData = new FormData();
    var townText: string = '서울특별시 ' + guText + ' ' + dongText;

    if (gu === dongData[guData[0]] && dong === '동') {
      townText = town;
    } else if (dong === '동') {
      warning('지역을 입력해 주세요.');
    }

    // if (!(nickName === viewNickname)) {
    //   console.log('닉네임 중복 여부 확인되지 않음');
    //   warning('닉네임 중복 여부를 검사해 주세요.');
    // }

    console.log('PUT할 데이터');
    console.log('프로필 이미지: ', finalProfileImage);
    console.log('닉네임: ', nickName);
    console.log('지역: ', townText);
    console.log('소개: ', introduction);

    /*formData, put */
    formData.append('image', finalProfileImage);
    formData.append('nickName', nickName);
    formData.append('location', townText);
    formData.append('introduction', introduction);

    /*토큰으로 put*/
    const userToken = getTokenFromCookie();

    axios.defaults.headers.common['Authorization'] = `Bearer ${userToken}`;
    axios
      .put('/api/users/update', formData, {
        headers: {
          'Contest-Type': 'multipart/form-data',
        },
      })
      .then((res) => {
        console.log('PUT 완료');
        console.log(res);
        handlePageMove(PATH.MY);
      })
      .catch((err) => {
        console.log('PUT 실패');
      });
  };

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    console.log('enter edit page');
    setHeaderTitle('내 정보 수정');

    /*토큰으로 get*/
    const userToken = getTokenFromCookie();

    console.log('가지고 있는 유저 토큰: ', userToken);

    axios.defaults.headers.common['Authorization'] = `Bearer ${userToken}`;
    axios
      .get('/api/users/get')
      .then((res) => {
        console.log(res);

        console.log('GET한 데이터');
        console.log('프로필 이미지: ', res.data.image_url);
        console.log('닉네임: ', res.data.nick_name);
        console.log('지역: ', res.data.location);
        console.log('소개: ', res.data.introduction);

        setViewNickname(res.data.nick_name);
        setViewTown(res.data.location);
        setViewIntroduction(res.data.introduction);

        setfinalProfileImage(res.data.image_url);
        setProfileImage(res.data.image_url);
        setNickName(res.data.nick_name);
        setTown(res.data.location);
        setIntroduction(res.data.introduction);
      })
      .catch((error) => {
        console.error('Error sending GET request:', error);
      });
  }, []);

  return (
    <Space css={topWrapperCSS} align="baseline">
      {contextHolder}
      <Form
        name="EditMyPage"
        onFinish={handleSubmitBtn}
        onFinishFailed={onFinishFailedJoin}
      >
        <Form.Item name="profileImage" css={cssMyEditCenter}>
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

        <Space direction="vertical" css={cssMyEditMargin}>
          <Text strong>기존 닉네임: {viewNickname}</Text>
          <Text strong>기존 지역: {viewTown}</Text>
          <Text strong>기존 자기소개: {viewIntroduction}</Text>
        </Space>

        <Form.Item
          label="닉네임"
          name="nickName"
          rules={[{ validator: rightNickname }]}
          css={cssMyEditMargin}
        >
          <Input onChange={onChangeNickName} defaultValue={nickName} />
        </Form.Item>

        <Form.Item name="닉네임 중복 검사">
          <Button type="primary" css={cssJoinNick} onClick={overlapNickname}>
            닉네임 중복 검사
          </Button>
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
          <Button type="primary" htmlType="submit" css={cssMyEditSubmitBtn}>
            수정 완료
          </Button>
        </Form.Item>
      </Form>
    </Space>
  );
};

export default MyEditPage;
