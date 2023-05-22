import { useCallback, useState, useRef, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { Modal, Form, Input, message } from 'antd';
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
  cssPostDetailSixth,
  cssPostDetailContent2,
  cssPostDetailAttachment,
  cssReportContainer,
  cssReportBtnStyle,
  cssAuthorFooter,
  cssNonAuthorFooter,
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
import { IBoard } from '../../api/interfaces/IPost';
import { ReactComponent as LikeDefault } from '../../assets/images/icons/like_default.svg';
import { ReactComponent as LikeClick } from '../../assets/images/icons/like_click.svg';
import { apiRequest } from '../../api/request';
import { API_URL } from '../../api/urls';
import Item from '../../components/post/Item';
import InputText from '../../components/post/InputText';
import { ApplicantButton } from '../../components/post/ApplicantButton';

import axios from 'axios';
import { useDeleteBoard, useGetBoard } from '../../api/hooks/board';
import {
  useCreateComment,
  useGetComments,
  useDeleteComment,
} from '../../api/hooks/comment';
import { useMutation, useQueryClient } from 'react-query';

import { PATH } from '../../utils/paths';
import { COMMON_COLOR } from '../../styles/constants/colors';

import { AxiosError, AxiosResponse } from 'axios';
import { IReportBoard } from '../../api/interfaces/IPost';

import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import dayjs from 'dayjs';

interface BoardProps {
  post?: IBoard;
}

interface TList {
  id: number;
  text: string;
}

interface Applicant {
  id: number;
  content: string;
  hidden: boolean;
  applied: boolean;
  adopted: boolean;
}

const Footer = Layout;

const PostPage = () => {
  const queryClient = useQueryClient();
  const [applicants, setApplicants] = useState<Applicant[]>([]);

  const navigate = useNavigate();
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('도움요청');
  }, [setHeaderTitle]);

  const [like, setLike] = useState(false);
  const [nickName, setNickName] = useState('');

  const useDeleteBoardMutation = useDeleteBoard();
  const [messageApi, contextHolder] = message.useMessage();
  const url = window.location.pathname;
  const real_id = url.substring(6);
  const { data, isLoading } = useGetBoard(parseInt(real_id));
  const createCommentMutation = useCreateComment(parseInt(real_id));
  const comments = useGetComments(parseInt(real_id));
  const useDeleteCommentMutation = useDeleteComment();

  useEffect(() => {
    console.log(data?.data.state);
  }, [data?.data.state]);

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
  useEffect(() => {
    apiRequest
      .get(`/api/deal-boards/comments/${real_id}`)
      .then((res) => {
        setApplicants(res.data);
        let comments = applicants.length;
      })
      .catch((error) => {
        console.error('Error sending GET request:', error);
      });
  }, [comments]);

  const useCreateReports = () => {
    return useMutation<AxiosResponse<boolean>, AxiosError, IReportBoard>({
      mutationKey: 'useReports',
      mutationFn: (data) =>
        apiRequest.post(`/api/deal-boards/${real_id}/report`, {
          ...data,
        }),
    });
  };

  const useReportMutation = useCreateReports();

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
      onOk: async (result) => {
        try {
          await useDeleteBoardMutation.mutateAsync(real_id);
          messageApi.open({
            type: 'success',
            content: '게시글 삭제 완료',
            duration: 0.5,
            onClose() {
              queryClient.invalidateQueries({
                queryKey: ['useInfiniteGetSearchBoard'],
              });
            },
          });
        } catch (error) {
          console.log(error);
        } finally {
          navigate('/search');
        }
      },
    });
  }, [useDeleteBoardMutation, queryClient, messageApi]);

  const handleDeleteComment = async (postPk: number, id: number) => {
    console.log(postPk);
    try {
      await useDeleteCommentMutation.mutateAsync({ postPk, id });
      messageApi.success('댓글이 성공적으로 삭제되었습니다.');
    } catch (error) {
      messageApi.error('댓글 삭제 중 오류가 발생했습니다.');
    }
  };

  const layout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 16 },
  };

  const onReport = useCallback(async () => {
    Modal.confirm({
      title: '신고하기',
      content: (
        <Form>
          <Form.Item
            name="reason"
            label="신고사유"
            rules={[
              {
                required: true,
                message: '신고 사유를 입력해주세요.',
              },
            ]}
          >
            <Input.TextArea rows={4} />
          </Form.Item>
        </Form>
      ),
      okText: '신고',
      cancelText: '취소',
      onOk: (e) => {
        const reason = e.reason;
        useReportMutation.mutate(
          { boardId: parseInt(real_id), report_body: reason },
          {
            onSuccess: () => {
              messageApi.success('게시글이 신고되었습니다.');
            },
            onError: (error: AxiosError) => {
              console.error('Error reporting post:', error);
            },
          },
        );
      },
    });
  }, [messageApi, useReportMutation]);

  // 수정 및 삭제 버튼 표시 여부를 결정하는 함수
  let [author, setAuthor] = useState(false);
  const isAuthor = useMemo(() => {
    return data?.data.userNickname === nickName;
  }, [data?.data.userNickname, nickName]);

  useEffect(() => {
    setAuthor(isAuthor);
  }, [isAuthor]);

  const [isListModalOpen, setIsListModalOpen] = useState(false);

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

  // 댓글 입력
  const [inputText, setInputText] = useState('');

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

  const adopted = false;
  const applied = false;
  const hidden = false;
  const content = inputText;

  // 입력값 버튼 핸들러
  const handleSubmitComment = useCallback(async () => {
    createCommentMutation.mutateAsync(
      { adopted, applied, content, hidden },
      {
        onSuccess: (data) => {
          queryClient.invalidateQueries('');
          setInputText('');
        },
        onError(error) {
          console.log('error');
        },
        onSettled: (data) => {
          console.log('dddddd');
        },
      },
    );
  }, [messageApi, createCommentMutation, queryClient, comments]);

  const startTime = dayjs(data?.data.startTime, 'YYYY-MM-DDTHH:mm:ss').format(
    'MM월 DD일 HH시 mm분',
  );
  const endTime = dayjs(data?.data.endTime, 'YYYY-MM-DDTHH:mm:ss').format(
    'HH시 mm분',
  );

  return (
    <Layout css={cssPostDetail}>
      <div css={cssPostDetailPage}>
        {author && (
          <>
            <div css={cssQnaDeleteStyle}>
              <Button css={cssEditBtnStyle}>수정</Button>
              <Button css={cssDeleteBtnStyle} onClick={handleDelete}>
                삭제
              </Button>
            </div>
          </>
        )}
        {!author && (
          <>
            <div css={cssReportContainer}>
              <Button css={cssReportBtnStyle} onClick={onReport}>
                게시글 신고하기
              </Button>
            </div>
          </>
        )}

        <div css={cssPostDetailThird}>
          <div className="category">
            <div css={cssPostDetailCategory1}>카테고리</div>
            <div css={cssPostDetailCategory2}>{data?.data.category}</div>
          </div>
          <div css={cssPostDetailPay}>{data?.data.pay} TP</div>
        </div>

        <div css={cssPostDetailSecond}>
          <div css={cssPostDetailTitle}>{data?.data.title}</div>
          <div css={cssPostDetailStatus}>
            <PostStatusTag status={data?.data.state} />
          </div>
        </div>

        <div css={cssPostDetailFourth}>
          <div css={cssPostDetailRegion}>
            <FlagFilled style={{ marginRight: 10 }} />
            {data?.data.location}
          </div>
          <div css={cssPostDetailTime}>
            <ClockCircleOutlined style={{ marginRight: 10 }} />
            {startTime} ~ {endTime}
          </div>
        </div>

        <div css={cssPostDetailFifth}>
          <div className="content">내용</div>
          <div css={cssPostDetailContent2}>{data?.data.content}</div>
          <div css={cssPostDetailAttachment}>{data?.data.imageUrl}</div>
        </div>
        <div css={cssPostDetailFirst}>
          <div css={cssPostDetailCreatedAt}>
            {data?.data.createdAt.substring(0, 10)}
          </div>
          <div css={cssPostDetailProfile}></div>
          <div css={cssPostDetailUser}>{data?.data.userNickname}</div>
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
        <div css={cssPostDetailSixth}>
          {applicants.length > 0 ? (
            applicants.map((data) => (
              <Item
                c={data}
                id={data.id}
                key={data.id}
                onClick={() => handleDeleteComment(parseInt(real_id), data.id)}
              />
            ))
          ) : (
            <p>
              아직 댓글이 없어요🥹 <br /> 첫 댓글을 입력해보세요!
            </p>
          )}
        </div>
      </div>
      <Footer css={author ? cssAuthorFooter : cssNonAuthorFooter}>
        <div css={cssLine2} />
        {author && (
          <>
            <PostButton />
            <div css={cssLine5} />
          </>
        )}

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
