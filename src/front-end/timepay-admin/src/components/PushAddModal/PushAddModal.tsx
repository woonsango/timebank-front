import React, { useCallback } from 'react';
import { Modal, Form, Input, Button, message } from 'antd';
import { useCreateNotifications } from '../../api/hooks/notification';
import { cssPushAddModalStyle } from './PushAddModal.style';
import { useQueryClient } from 'react-query';

export interface PushAddModalProps {
  isOpen: boolean;
  onCancel: () => void;
}

const PushAddModal = ({ isOpen, onCancel }: PushAddModalProps) => {
  const queryClient = useQueryClient();

  const [form] = Form.useForm();
  const [messageApi, contextHolder] = message.useMessage();
  const { TextArea } = Input;

  const createNotificationMutation = useCreateNotifications();

  const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 16 },
  };

  const onFinish = useCallback(
    (values: any) => {
      createNotificationMutation.mutateAsync(values, {
        onSuccess: (result) => {
          messageApi.open({
            type: 'success',
            content: '공지가 등록되었습니다.',
          });
        },
        onError: (err) => {
          messageApi.open({
            type: 'error',
            content: (
              <>
                오류 발생: <br />
                {err}
              </>
            ),
          });
        },
        onSettled: async (data) => {
          onCancel();
          await queryClient.invalidateQueries({
            queryKey: ['useGetNotifications'],
          });
          form.resetFields();
        },
      });
    },
    [queryClient, form, createNotificationMutation, messageApi, onCancel],
  );

  return (
    <Modal
      title="공지 작성"
      open={isOpen}
      onCancel={onCancel}
      footer={null}
      onOk={onCancel}
      css={cssPushAddModalStyle}
    >
      {contextHolder}
      <Form
        {...layout}
        form={form}
        onFinish={onFinish}
        style={{ width: '100%' }}
      >
        <Form.Item
          name="title"
          label="공지 제목"
          rules={[{ required: true, message: '제목을 입력해주세요' }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="content"
          label="공지 내용"
          rules={[{ required: true, message: '내용을 입력해주세요' }]}
        >
          <TextArea rows={10} maxLength={100} />
        </Form.Item>
        <div className="control-box">
          <Button
            onClick={() => {
              onCancel();
              form.resetFields();
            }}
          >
            취소
          </Button>
          <Button type="primary" htmlType="submit">
            작성완료
          </Button>
        </div>
      </Form>
    </Modal>
  );
};

export default PushAddModal;
