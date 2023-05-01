import { useCallback, useState, useRef, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { List, Modal } from 'antd';
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
  cssReportContainer,
  cssReportBtnStyle,
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
  cssCollectButton,
  cssCollectBtn,
  cssApplicant,
} from './PostPage.style';
import PostStatusTag from '../../components/PostStatusTag';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';
import { Button, Layout } from 'antd';
import PostButton from '../../components/post/PostButton';
import { IPost } from '../../api/interfaces/IPost';
import { ReactComponent as LikeDefault } from '../../assets/images/icons/like_default.svg';
import { ReactComponent as LikeClick } from '../../assets/images/icons/like_click.svg';

import Item from '../../components/post/Item';
import InputText from '../../components/post/InputText';

import { Form, Input } from 'antd';

import axios from 'axios';
import { useGetFreeBoards } from '../../api/hooks/register';

interface PostPageProps {
  post?: IPost;
}

interface TList {
  id: number;
  text: string;
}

const Footer = Layout;

const PostPage = ({ post }: PostPageProps) => {
  const { data } = useGetFreeBoards();

  useEffect(() => {
    console.log(data);
  });

  const [like, setLike] = useState(false);

  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const showModal = () => {
    setIsDeleteModalOpen(true);
  };
  const handleOk = () => {
    setIsDeleteModalOpen(false);
  };
  const handleCancel = () => {
    setIsDeleteModalOpen(false);
  };

  const [form] = Form.useForm();
  const { TextArea } = Input;
  const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 16 },
  };
  const [isReportModalOpen, setIsReportModalOpen] = useState(false);
  const showReportModal = () => {
    setIsReportModalOpen(true);
  };
  const [isListModalOpen, setIsListModalOpen] = useState(false);
  const showListModal = () => {
    setIsListModalOpen(true);
  };
  const onOk = () => {
    setIsReportModalOpen(false);
  };
  const onCancel = () => {
    setIsReportModalOpen(false);
  };

  // 지원자 목록 모달 창

  const [selectedItem, setSelectedItem] = useState(null);
  const onOk2 = () => {
    if (selectedItem) {
      console.log('선택된 지원자: ', selectedItem);
      setIsListModalOpen(false);
    }
  };
  const onCancel2 = () => {
    setIsListModalOpen(false);
  };
  const onItemClick = (item: any) => {
    setSelectedItem(item);
  };
  const isItemSelected = (item: any) => {
    return selectedItem === item;
  };

  useEffect(() => {
    // Ajax 요청 등으로 서버에서 동적으로 데이터를 가져와서
    // setApplicantList로 데이터를 업데이트합니다.
  }, [isListModalOpen]);

  const [applicantList, setApplicantList] = useState([
    '지원자1',
    '지원자2',
    '지원자3',
  ]);

  /*
  const [apiData, setApiData] = useState(null);

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetch('/api/boards');
        const data = await response.json();
        setApiData(data);
      } catch (error) {
        console.error(error);
      }
    }
    fetchData();
  }, []);

  */

  // 댓글 입력
  const [inputText, setInputText] = useState('');
  // 댓글
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

    axios
      .post(`/api/free-boards/comments/write/${id}`, { inputText })
      .then((response) => {
        // 요청이 성공적으로 처리되었을 때 실행될 코드 작성

        console.log('댓글 등록 성공');
        // 등록 후에는 홈 화면으로 이동
        navigate(-1);
      })
      .catch((error) => {
        // 요청이 실패했을 때 실행될 코드 작성
        console.error('댓글 등록 실패', error);
      });
  };

  return (
    <Layout css={cssPostDetail}>
      <div css={cssMainHeaderStyle}>
        <BackArrow onClick={handleClickBack} />
        <span>{type}</span>
      </div>
      <div css={cssPostDetailPage}>
        <div css={cssQnaDeleteStyle}>
          {}
          <Button css={cssEditBtnStyle} onClick={handleEditPageChange}>
            수정
          </Button>
          <Button css={cssDeleteBtnStyle} onClick={showModal}>
            삭제
          </Button>
        </div>
        <Modal
          title="정말 게시글을 삭제하시겠습니까?"
          open={isDeleteModalOpen}
          onOk={handleOk}
          onCancel={handleCancel}
          okText="확인"
          cancelText="취소"
        ></Modal>

        <div css={cssReportContainer}>
          <Button css={cssReportBtnStyle} onClick={showReportModal}>
            게시글 신고하기
          </Button>
        </div>
        <Modal
          title="게시글 신고하기"
          open={isReportModalOpen}
          onOk={onOk}
          onCancel={onCancel}
          footer={null}
        >
          <Form {...layout} form={form} style={{ width: '100%' }}>
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
              <Button
                style={{ marginRight: 20 }}
                onClick={() => {
                  onCancel();
                  form.resetFields();
                }}
              >
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

        <div css={cssPostDetailSecond}>
          <div css={cssPostDetailTitle}>{title}</div>
          <div css={cssPostDetailStatus}>
            <PostStatusTag status={status} />
          </div>
        </div>

        <div css={cssPostDetailThird}>
          <div css={cssPostDetailCategory1}>카테고리</div>
          <div css={cssPostDetailCategory2}>{category}</div>
          <div css={cssPostDetailPay}>{pay} TP</div>
        </div>

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

        <div css={cssPostDetailFifth}>
          <div css={cssPostDetailContent2}>{content}</div>
          <div css={cssPostDetailAttachment}>{attachment}</div>
        </div>

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

        <div css={cssLine4} />
        <div css={cssCommentContainer}>
          <p>댓글</p>
          <div css={cssCollectButton}>
            <Button css={cssCollectBtn} onClick={showListModal}>
              지원자 선정하기
            </Button>
          </div>
          <Modal
            title="지원자 목록"
            open={isListModalOpen}
            onOk={onOk2}
            onCancel={onCancel2}
            okText="선정하기"
            cancelText="취소"
          >
            <div css={cssApplicant}>
              <List>
                {applicantList.map((applicant, index) => (
                  <List.Item key={index} onClick={() => onItemClick(index)}>
                    {applicant}
                  </List.Item>
                ))}
              </List>
            </div>
          </Modal>
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
