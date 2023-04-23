import React, { useState } from 'react';
import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { cssAdminAdd, cssBox } from './AdminAdd.style';

const layout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 16 },
};

const validateMessages = {
  types: {
    email: '${label} is not a valid email!',
  },
};

const handleOnClickAdminAdd = (values: any) => {
  console.log(values);
};

const AdminAdd = () => {
  const [form] = Form.useForm();
  const [isOpenModal, setIsOpenModal] = useState(false);
  const content = (
    <Form
      form={form}
      {...layout}
      onFinish={handleOnClickAdminAdd}
      style={{ maxWidth: 600 }}
      validateMessages={validateMessages}
    >
      <Form.Item name={['user', 'key']} label="관리자 번호">
        <Input />
      </Form.Item>
      <Form.Item name={['user', 'name']} label="이름">
        <Input />
      </Form.Item>
      <Form.Item name={['email']} label="이메일" rules={[{ type: 'email' }]}>
        <Input />
      </Form.Item>
      <Form.Item name={['ID']} label="아이디">
        <Input />
      </Form.Item>

      <Form.Item name={['password']} label="초기비밀번호">
        <Input />
      </Form.Item>
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
        onCancel={() => setIsOpenModal(false)}
        onOk={() => setIsOpenModal(false)}
      >
        {content}
      </Modal>
    </div>
  );
};

export default AdminAdd;
