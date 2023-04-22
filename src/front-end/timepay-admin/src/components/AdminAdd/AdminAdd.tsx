import React, { useState } from 'react';
import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { cssAddModal, cssAdminAdd, cssBox } from './AdminAdd.style';

const layout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 16 },
};

const validateMessages = {
  types: {
    email: '이메일 형식으로 입력해주세요',
  },
};

const AdminAdd = () => {
  const [form] = Form.useForm();
  const [isOpenModal, setIsOpenModal] = useState(false);
  const onClickCancel = () => {
    setIsOpenModal(false);
  };
  const onFinish = (values: any) => {
    console.log(values);
  };

  const content = (
    <Form
      {...layout}
      form={form}
      onFinish={onFinish}
      style={{ maxWidth: 600 }}
      css={cssAddModal}
      validateMessages={validateMessages}
    >
      <Form.Item
        name={['adminName']}
        label="이름"
        rules={[{ required: true, message: '이름을 입력해주세요' }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name={['email']}
        label="이메일"
        rules={[
          { type: 'email', required: true, message: '이메일을 입력해주세요' },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name={['name']}
        label="아이디"
        rules={[{ required: true, message: '아이디를 입력해주세요' }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name={['password']}
        label="초기비밀번호"
        rules={[{ required: true, message: '초기비밀번호를 입력해주세요' }]}
      >
        <Input.Password />
      </Form.Item>
      <Form.Item
        name={['phone']}
        label="전화번호"
        rules={[{ required: true, message: '전화번호를 입력해주세요' }]}
      >
        <Input />
      </Form.Item>
      <div className="footer">
        <Button key="취소" onClick={onClickCancel}>
          취소
        </Button>
        <Button key="submit" type="primary" htmlType="submit">
          추가
        </Button>
      </div>
    </Form>
  );

  return (
    <div css={cssBox}>
      <Button
        css={cssAdminAdd}
        type="primary"
        onClick={() => setIsOpenModal(true)}
      >
        관리자 추가
      </Button>
      <Modal
        title="관리자 추가"
        centered
        open={isOpenModal}
        onCancel={onClickCancel}
        footer={null}
      >
        {content}
      </Modal>
    </div>
  );
};

export default AdminAdd;
