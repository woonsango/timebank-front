import React, { useState } from 'react';
import { Button, Modal, Form, Input, InputNumber } from 'antd';
import { cssPushAddContainer } from './PushAddModal.style';

const layout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 16 },
};

const onFinish = (values: any) => {
  console.log(values);
};

export interface PushAddModalProps {
  isOpen: boolean;
  onCancel: () => void;
}

const { TextArea } = Input;

const PushAddModal = ({ isOpen, onCancel }: PushAddModalProps) => {
  const content = (
    <Form {...layout} onFinish={onFinish} style={{ maxWidth: 600 }}>
      <Form.Item name={['title']} label="공지 제목">
        <Input />
      </Form.Item>
      <Form.Item name={['content']} label="공지 내용">
        <TextArea maxLength={100} style={{ height: 120, resize: 'none' }} />
      </Form.Item>
    </Form>
  );

  return (
    <div>
      <Modal
        title="공지 작성"
        open={isOpen}
        onCancel={onCancel}
        onOk={onCancel}
      >
        {content}
      </Modal>
    </div>
  );
};

export default PushAddModal;
