import React, { useCallback } from 'react';
import { Button, Checkbox, Form, Input } from 'antd';
import { ReactComponent as Logo } from '../../assets/images/timepay-logo.svg';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { useNavigate } from 'react-router-dom';
import {
  cssPasswordEditButtonStyle,
  cssPasswordEditInnerBox,
} from './PasswordEditPage.styles';

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};

const PasswordEditPage = () => {
  const navigate = useNavigate();

  const handleOnClickChangeBtn = () => {
    navigate(`/post-management`);
  };

  return (
    <div css={cssPasswordEditInnerBox}>
      <Logo fill={COMMON_COLOR.LOGO1} width="378px" height="128.01px" />
      <Form
        name="basic"
        style={{ width: 300 }}
        onFinish={handleOnClickChangeBtn}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
        layout="vertical"
      >
        <Form.Item
          label="현재 비밀번호"
          name="currentPassword"
          rules={[{ required: true, message: '비밀번호를 입력해주세요' }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="새 비밀번호"
          name="newPassword"
          rules={[
            { required: true, message: '새 비밀번호를 입력해주세요' },
            {
              pattern:
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
              message:
                '8자이상의 숫자 1개, 문자 1개, 특수문자 1개이상을 포함해야합니다',
            },
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          label="비밀번호 확인"
          name="newPasswordCheck"
          dependencies={['newPassword']}
          rules={[
            { required: true, message: '비밀번호를 입력해주세요' },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue('newPassword') === value) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error('비밀번호가 일치하지 않습니다'),
                );
              },
            }),
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Button
            type="primary"
            htmlType="submit"
            css={cssPasswordEditButtonStyle}
          >
            변경
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default PasswordEditPage;
