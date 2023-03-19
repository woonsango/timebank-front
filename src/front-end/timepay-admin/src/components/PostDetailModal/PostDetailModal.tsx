import { Button, Modal } from 'antd';
import { useMemo } from 'react';
import { IPost } from '../../api/interfaces/IPost';
import { cssPostDetailModalStyle } from './PostDetailModal.styles';

interface PostDetailModalProps {
  post?: IPost;
  isOpen: boolean;
  onCancel: () => void;
}
const PostDetailModal = ({ post, isOpen, onCancel }: PostDetailModalProps) => {
  const footer = useMemo(() => {
    return (
      <Button type="primary" onClick={onCancel}>
        확인
      </Button>
    );
  }, [onCancel]);

  return (
    <Modal
      title="게시글 상세정보 조회"
      open={isOpen}
      onOk={onCancel}
      onCancel={onCancel}
      footer={footer}
      width={900}
      css={cssPostDetailModalStyle}
    >
      <div className="post-detail-container">
        <span className="title">제목</span>
        <div className="post-detail-inner-container">{post?.title}</div>
      </div>
      <div className="post-detail-container">
        <span className="title">본문</span>
        <div className="post-detail-inner-container">{post?.content}</div>
      </div>
      <div className="post-detail-container">
        <span className="title">첨부사진</span>
        <div className="post-detail-inner-container">
          <img src={post?.attachment} alt="첨부사진" />
        </div>
      </div>
      <div className="post-detail-container">
        <span className="title">태그</span>
        <div className="post-detail-inner-container">{post?.tag}</div>
      </div>
    </Modal>
  );
};
export default PostDetailModal;
