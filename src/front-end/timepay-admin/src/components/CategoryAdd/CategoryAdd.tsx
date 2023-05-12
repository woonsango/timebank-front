import React, { useState } from 'react';
import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { cssCategoryAdd, cssBox } from './CategoryAdd.style';

const layout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 16 },
};

const onFinish = (values: any) => {
  console.log(values);
};

const CategoryAdd = () => {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const content = (
    <Form {...layout} onFinish={onFinish} style={{ maxWidth: 600 }}>
      <Form.Item name={['categoryId']} label="카테고리 번호">
        <Input />
      </Form.Item>
      <Form.Item name={['boardType']} label="카테고리 타입">
        <Input />
      </Form.Item>
      <Form.Item name={['categoryName']} label="카테고리 이름">
        <Input />
      </Form.Item>
    </Form>
  );

  return (
    <div css={cssBox}>
      <Button
        css={cssCategoryAdd}
        type="primary"
        onClick={() => setIsOpenModal(true)}
      >
        카테고리 추가
      </Button>
      <Modal
        title="카테고리 추가"
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

export default CategoryAdd;
