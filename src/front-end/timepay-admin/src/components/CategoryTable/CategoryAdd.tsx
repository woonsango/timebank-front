import React, { useState } from 'react';
import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { cssCategoryAdd, cssBox } from './CategoryAdd.style';

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

const AdminAdd = () => {
  const [modal2Open, setModal2Open] = useState(false);
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
        label="카테고리 번호"
        rules={[{ type: 'number' }]}
      >
        <Input />
      </Form.Item>
      <Form.Item name={['user', 'name']} label="카테고리 이름">
        <Input />
      </Form.Item>
      <Form.Item name={['user', 'parentCategory']} label="상위 카테고리">
        <Input />
      </Form.Item>
    </Form>
  );

  ////////////////////////////////////////////////////

  return (
    <div css={cssBox}>
      <Button
        css={cssCategoryAdd}
        type="primary"
        onClick={() => setModal2Open(true)}
      >
        카테고리 추가
      </Button>
      <Modal
        title="카테고리 추가"
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
