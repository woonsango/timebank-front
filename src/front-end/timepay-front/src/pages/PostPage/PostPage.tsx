import { useCallback, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Modal } from 'antd';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostDetailPage,
  cssPostDetailFirst,
  cssPostDetailUser,
  cssPostDetailTitle,
  cssLine1,
  cssLine3,
  cssLine4,
  cssPostDetailCreatedAt,
  cssPostDetailProfile,
  cssPostDetailSecond,
  cssPostDetailStatus,
  cssPostDetailThird,
  cssPostDetailCategory1,
  cssPostDetailCategory2,
  cssPostDetailPay,
  cssPostDetailFourth,
  cssPostDetailRegion,
  cssPostDetailTime,
  cssPostDetailFifth,
  cssPostDetailContent1,
  cssPostDetailContent2,
  cssPostDetailAttachment,
  cssPostFooter,
  cssPostDetail,
  cssPostTextarea,
  cssLine2,
  cssPostBtn,
  cssPostFooter2,
  cssLine5,
  cssQnaDeleteStyle,
  cssDeleteBtnStyle,
  cssEditBtnStyle,
  cssLike,
  cssLikeContainer,
} from './PostPage.style';
import PostStatusTag from '../../components/PostStatusTag';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';
import { Button, Layout } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import PostButton from '../../components/post/PostButton';
import { IPost } from '../../api/interfaces/IPost';
import { ReactComponent as LikeDefault } from '../../assets/images/icons/like_default.svg';
import { ReactComponent as LikeClick } from '../../assets/images/icons/like_click.svg';

interface PostPageProps {
  post?: IPost;
}

const Footer = Layout;

const PostPage = ({ post }: PostPageProps) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [like, setLike] = useState(false);

  const navigate = useNavigate();
  const location = useLocation();
  const {
    id,
    type,
    title,
    content,
    createdAt,
    category,
    attachment,
    status,
    pay,
    startTime,
    endTime,
    region,
    user,
  } = location.state;

  const handleEditPageChange = () => {
    navigate(`/edit/${id}`, {
      state: {
        id,
        type,
        title,
        content,
        status,
        category,
        pay,
        startTime,
        endTime,
        region,
        attachment,
        user,
      },
    });
  };

  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const handleLike = () => {
    setLike(!like);
  };

  return (
    <Layout css={cssPostDetail}>
      <div css={cssMainHeaderStyle}>
        <BackArrow onClick={handleClickBack} />
        <span>{type}</span>
      </div>
      <div css={cssPostDetailPage}>
        <div css={cssPostDetailFirst}>
          <div css={cssPostDetailCreatedAt}>{createdAt}</div>
          <div css={cssPostDetailProfile}></div>
          <div css={cssPostDetailUser}>{user}</div>
          <div css={cssLikeContainer}>
            <p>관심 </p>
            {like === true ? (
              <button css={cssLike} onClick={handleLike}>
                <LikeClick />
              </button>
            ) : (
              <button css={cssLike} onClick={handleLike}>
                <LikeDefault />
              </button>
            )}
          </div>
        </div>
        <div css={cssLine1} />
        <div css={cssQnaDeleteStyle}>
          <Button css={cssEditBtnStyle} onClick={handleEditPageChange}>
            수정
          </Button>
          <Button css={cssDeleteBtnStyle} onClick={showModal}>
            삭제
          </Button>
        </div>
        <Modal
          title="정말 종료로 상태를 변경하시겠습니까?"
          open={isModalOpen}
          onOk={handleOk}
          onCancel={handleCancel}
          okText="확인"
          cancelText="취소"
        ></Modal>
        <div css={cssLine3} />
        <div css={cssPostDetailSecond}>
          <div css={cssPostDetailTitle}>{title}</div>
          <div css={cssPostDetailStatus}>
            <PostStatusTag status={status} />
          </div>
        </div>
        <div css={cssLine3} />
        <div>
          <div css={cssPostDetailThird}>
            <div css={cssPostDetailCategory1}>카테고리</div>
            <div css={cssPostDetailCategory2}>{category}</div>
            <div css={cssPostDetailPay}>{pay} TP</div>
          </div>
        </div>
        <div css={cssLine3} />
        <div css={cssPostDetailFourth}>
          <div css={cssPostDetailRegion}>
            <FlagFilled style={{ marginRight: 10 }} />
            {region}
          </div>
          <div css={cssPostDetailTime}>
            <ClockCircleOutlined style={{ marginRight: 10 }} />
            {startTime} ~ {endTime}
          </div>
        </div>
        <div css={cssLine1} />
        <div css={cssPostDetailFifth}>
          <div css={cssPostDetailContent1}>내용</div>
          <div css={cssPostDetailContent2}>{content}</div>
          <div css={cssPostDetailAttachment}>{attachment}</div>
        </div>
        <div css={cssLine4} />
      </div>
      <Footer css={cssPostFooter}>
        <div css={cssLine2} />
        <PostButton />
        <div css={cssLine5} />
        <div css={cssPostFooter2}>
          <TextArea
            placeholder="댓글 입력창"
            css={cssPostTextarea}
            style={{
              height: 100,
              width: '90%',
              fontSize: 20,
              resize: 'none',
            }}
          ></TextArea>
          <Button css={cssPostBtn}>등록</Button>
        </div>
      </Footer>
    </Layout>
  );
};
export default PostPage;
