import { useCallback, useState, useRef, useEffect, useMemo } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { List, Modal, Form, Input, message } from 'antd';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostDetailPage,
  cssPostDetailFirst,
  cssPostDetailUser,
  cssPostDetailTitle,
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
  cssPostDetailContent2,
  cssPostDetailAttachment,
  cssReportContainer,
  cssReportBtnStyle,
  cssPostFooter,
  cssPostDetail,
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
import PostButton from '../../components/post/PostButton';
import { IPost } from '../../api/interfaces/IPost';
import { ReactComponent as LikeDefault } from '../../assets/images/icons/like_default.svg';
import { ReactComponent as LikeClick } from '../../assets/images/icons/like_click.svg';
import { apiRequest } from '../../api/request';
import { API_URL } from '../../api/urls';
import Item from '../../components/post/Item';
import InputText from '../../components/post/InputText';
import { ApplicantButton } from '../../components/post/ApplicantButton';

import axios from 'axios';
import { useDeleteBoard } from '../../api/hooks/board';
import { PATH } from '../../utils/paths';
import { COMMON_COLOR } from '../../styles/constants/colors';

interface PostPageProps {
  post?: IPost;
}

interface TList {
  id: number;
  text: string;
}

const Footer = Layout;

const PostPage = ({ post }: PostPageProps) => {
  const [like, setLike] = useState(false);
  const [nickName, setNickName] = useState('');

  useEffect(() => {
    apiRequest
      .get(API_URL.USER_INFO_GET)
      .then((res) => {
        setNickName(res.data.body.nick_name);
      })
      .catch((error) => {
        console.error('Error sending GET request:', error);
      });
  }, []);

  const useDeleteBoardMutation = useDeleteBoard();
  const [messageApi, contextHolder] = message.useMessage();
  const url = window.location.pathname;
  const real_id = url.substring(6);

  const handleDelete = useCallback(async () => {
    Modal.confirm({
      content: '정말 게시글을 삭제하시겠습니까?',
      okText: '삭제',
      cancelText: '취소',
      okButtonProps: {
        style: {
          background: `${COMMON_COLOR.MAIN1}`,
          borderColor: `${COMMON_COLOR.MAIN1}`,
        },
      },
      onOk: async () => {
        try {
          await useDeleteBoardMutation.mutateAsync(real_id, {
            onSuccess: async (data) => {
              messageApi.open({
                type: 'success',
                content: '게시글 삭제 완료',
                duration: 0.5,
                onClose() {
                  navigate(PATH.HOME);
                },
              });
            },
            onError: (err) => {
              console.log(err);
            },
          });
        } catch (err) {
          console.log('2', err);
        }
      },
    });
  }, [useDeleteBoardMutation]);

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

  // 수정 및 삭제 버튼 표시 여부를 결정하는 함수
  let [author, setAuthor] = useState(false);
  const isAuthor = useMemo(() => {
    if (user === nickName) {
      setAuthor(true);
    } else setAuthor(false);
  }, [user, nickName]);

  const onApplicantClick = (applicant: any) => {
    console.log(`Selected applicant: ${applicant}`);
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
        <span>도움요청</span>
      </div>
      <div css={cssPostDetailPage}>
        {author && (
          <>
            <div css={cssQnaDeleteStyle}>
              <Button css={cssEditBtnStyle} onClick={handleEditPageChange}>
                수정
              </Button>
              <Button css={cssDeleteBtnStyle} onClick={handleDelete}>
                삭제
              </Button>
            </div>
          </>
        )}
        {!author && (
          <>
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
                  rules={[
                    { required: true, message: '신고 사유를 적어주세요.' },
                  ]}
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
          </>
        )}

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
          <div css={cssPostDetailCreatedAt}>{createdAt.substring(0, 10)}</div>
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
        <ApplicantButton
          applicantList={applicantList}
          onItemClick={onApplicantClick}
        />
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
