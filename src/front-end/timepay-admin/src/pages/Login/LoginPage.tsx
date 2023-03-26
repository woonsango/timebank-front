import React, { useCallback } from 'react';
import { Button, Checkbox, Form, Input } from 'antd';
import { ReactComponent as Logo } from '../../assets/images/timepay-logo.svg';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { useNavigate } from 'react-router-dom';
import {
  cssLoginButtonStyle,
  cssLoginInnerBox,
  cssLoginOuterBox,
} from './Login.styles';

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};

const LoginPage = () => {
  const navigate = useNavigate();

  const handleOnClickLoginBtn = () => {
    /*첫로그인 검증후 비밀번호 변경 페이지 접근 제어 추가예정*/
    const firstLogin = true;
    firstLogin ? navigate('/password-edit') : navigate(`/post-management`);
  };

  return (
    <div css={cssLoginOuterBox}>
      <div css={cssLoginInnerBox}>
        <Logo fill={COMMON_COLOR.LOGO1} width="378px" height="128.01px" />
        <Form
          name="basic"
          style={{ width: 300 }}
          initialValues={{ remember: true }}
          onFinish={handleOnClickLoginBtn}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
          layout="vertical"
        >
          <div>
            <Form.Item
              name="username"
              rules={[{ required: true, message: '아이디를 입력해주세요.' }]}
            >
              <Input placeholder="아이디" />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[{ required: true, message: '비밀번호를 입력해주세요.' }]}
            >
              <Input.Password placeholder="비밀번호" />
            </Form.Item>
          </div>

          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            {/*로그인토큰 검증 아직 x */}
            <Button type="primary" htmlType="submit" css={cssLoginButtonStyle}>
              로그인
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default LoginPage;
