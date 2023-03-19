import React, { useState } from 'react';
import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { cssAdminAdd, cssBox } from './AdminAdd.style';

//form
const layout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 16 },
};

/* eslint-disable no-template-curly-in-string */
const validateMessages = {
  types: {
    email: '${label} is not a valid email!',
  },
};
/* eslint-enable no-template-curly-in-string */

const onFinish = (values: any) => {
  console.log(values);
};

/////////////////////////////////////////////////////////////

const AdminAdd = () => {
  //관리자추가
  const [modal2Open, setModal2Open] = useState(false);
  /////form
  //관리자 번호 , 이름, 이메일, 아이디, 초기 비밀번호
  const content = (
    <Form
      name="nest-messages"
      {...layout}
      onFinish={onFinish}
      style={{ maxWidth: 600 }}
      validateMessages={validateMessages}
    >
      <Form.Item
        name={['user', 'key']}
        label="관리자 번호"
        rules={[{ type: 'number' }]}
      >
        <Input />
      </Form.Item>
      <Form.Item name={['user', 'name']} label="이름">
        <Input />
      </Form.Item>
      <Form.Item
        name={['user', 'email']}
        label="이메일"
        rules={[{ type: 'email' }]}
      >
        <Input />
      </Form.Item>
      <Form.Item name={['user', 'ID']} label="아이디">
        <Input />
      </Form.Item>

      <Form.Item name={['user', 'password']} label="초기비밀번호">
        <Input />
      </Form.Item>
    </Form>
  );

  ////////////////////////////////////////////////////

  return (
    <div css={cssBox}>
      <Button
        css={cssAdminAdd}
        type="primary"
        onClick={() => setModal2Open(true)}
      >
        관리자 추가
      </Button>
      <Modal
        title="관리자 추가"
        centered
        open={modal2Open}
        onCancel={() => setModal2Open(false)}
        onOk={() => setModal2Open(false)}
      >
        {content}
      </Modal>
    </div>
  );
};

export default AdminAdd;
