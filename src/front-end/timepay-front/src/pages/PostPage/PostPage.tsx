import { useCallback, useState, useRef } from 'react';
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
  cssCommentContainer,
} from './PostPage.style';
import PostStatusTag from '../../components/PostStatusTag';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';
import { Button, Layout } from 'antd';
import PostButton from '../../components/post/PostButton';
import { IPost } from '../../api/interfaces/IPost';
import { ReactComponent as LikeDefault } from '../../assets/images/icons/like_default.svg';
import { ReactComponent as LikeClick } from '../../assets/images/icons/like_click.svg';

import { Comment } from '../../components/post/Comment';
import Item from '../../components/post/Item';
import InputText from '../../components/post/InputText';

interface PostPageProps {
  post?: IPost;
}

interface TList {
  id: number;
  text: string;
}

const Footer = Layout;

const PostPage = ({ post }: PostPageProps) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [like, setLike] = useState(false);

  const [inputText, setInputText] = useState('');
  const [tasks, setTasks] = useState<TList[]>([
    {
      id: 1,
      text: '이거는 어떻게 해야하나요?',
    },
    {
      id: 2,
      text: '저 지원하고 싶네요~ 열심히 할수 있어요!',
    },
    {
      id: 3,
      text: '정확히 어디서 하는 건지 알려줘요',
    },
  ]);
  const nextId = useRef(4);

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

  // 입력값 변경 핸들러
  const handleInputTextChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setInputText(e.target.value);
  };

  // 입력값 버튼 핸들러
  const handleSubmitComment = (e: React.FormEvent<HTMLButtonElement>) => {
    const newList: TList = {
      id: nextId.current,
      text: inputText,
    };
    setTasks(tasks.concat(newList));
    setInputText('');
    nextId.current += 1;
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
        <div css={cssCommentContainer}>
          <p>댓글</p>
          <div>
            {tasks.map((task) => (
              <Item key={`${task.id}task`} id={task.id} text={task.text} />
            ))}
          </div>
        </div>
      </div>
      <Footer css={cssPostFooter}>
        <div css={cssLine2} />
        <PostButton />
        <div css={cssLine5} />
        <div css={cssPostFooter2}>
          <InputText onChange={handleInputTextChange} inputText={inputText} />
          <button css={cssPostBtn} onClick={handleSubmitComment}>
            등록
          </button>
        </div>
      </Footer>
    </Layout>
  );
};
export default PostPage;
