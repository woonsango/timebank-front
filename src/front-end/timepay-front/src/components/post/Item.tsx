import React, { useState } from 'react';
import {
  cssComments,
  cssCommentItem,
  cssPostDetailProfile,
  cssEditDelete,
  cssCommentText,
  cssCommentProfile,
} from './Item.style';
import { Form, Input, Modal, Button, message } from 'antd';
import { useDeleteComment } from '../../api/hooks/comment';

const Item = ({ c }: any) => {
  const write_user = false; // true = 수정 / false = 신고

  // 수정 기능
  const { TextArea } = Input;
  const [isOpenModal2, setIsOpenModal2] = useState(false);

  // 수정 기능
  const showReportModal = () => {
    setIsOpenModal2(true);
  };
  const onOk = () => {
    setIsOpenModal2(false);
  };
  const onCancel = () => {
    setIsOpenModal2(false);
  };

  const url = window.location.pathname;
  const real_id = url.substring(6);
  const [messageApi, contextHolder] = message.useMessage();
  const useDeleteCommentMutation = useDeleteComment();

  const handleDeleteComment = async (postPk: number, id: number) => {
    try {
      await useDeleteCommentMutation.mutateAsync({ postPk, id });
      messageApi.success('댓글이 성공적으로 삭제되었습니다.');
    } catch (error) {
      messageApi.error('댓글 삭제 중 오류가 발생했습니다.');
    }
  };

  return (
    <div css={cssComments}>
      <div css={cssEditDelete}>
        {write_user ? (
          <Button className="edit">수정</Button>
        ) : (
          <Button className="edit" onClick={showReportModal}>
            신고
          </Button>
        )}

        <Modal
          title="댓글 신고하기"
          open={isOpenModal2}
          onOk={onOk}
          onCancel={onCancel}
          footer={null}
        >
          <Form style={{ width: '100%' }}>
            <Form.Item
              name="content"
              label="신고사유"
              rules={[{ required: true, message: '신고 사유를 적어주세요.' }]}
            >
              <TextArea
                rows={10}
                maxLength={100}
                style={{ resize: 'none', fontSize: 20 }}
              />
            </Form.Item>
            <div className="control-box">
              <Button style={{ marginRight: 20 }} onClick={onCancel}>
                취소
              </Button>
              <Button
                type="primary"
                htmlType="submit"
                style={{ resize: 'none' }}
              >
                신고하기
              </Button>
            </div>
          </Form>
        </Modal>
        <div className="sidebar">|</div>
        <Button
          className="delete"
          onClick={() => handleDeleteComment(parseInt(real_id), c.id)}
        >
          삭제
        </Button>
      </div>

      <div css={cssCommentItem}>
        <div css={cssPostDetailProfile}>
          <div css={cssCommentProfile}></div>
        </div>
        <div css={cssCommentText}>{c.content}</div>
      </div>
    </div>
  );
};

export default Item;
