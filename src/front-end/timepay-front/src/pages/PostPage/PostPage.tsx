import { useCallback, useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { Modal, Form, Input, message, Spin } from 'antd';
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
  cssSpinStyle,
  cssSpinCommentStyle,
  cssVolunteerInfoStyle,
} from './PostPage.style';
import PostStatusTag from '../../components/PostStatusTag';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';
import { Button, Layout } from 'antd';
import PostButton from '../../components/post/PostButton';
import { ReactComponent as LikeDefault } from '../../assets/images/icons/like_default.svg';
import { ReactComponent as LikeClick } from '../../assets/images/icons/like_click.svg';
import { ReactComponent as VolunteerBadge } from '../../assets/images/icons/volunteer-badge.svg';
import Item from '../../components/post/Item';
import InputText from '../../components/post/InputText';
import ApplicantButton from '../../components/post/ApplicantButton';
import {
  useCreateReports,
  useDeleteBoard,
  useGetBoard,
} from '../../api/hooks/board';
import { useCreateComment, useGetComments } from '../../api/hooks/comment';
import { useQueryClient } from 'react-query';
import { PATH } from '../../utils/paths';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import dayjs from 'dayjs';
import { useGetUserInfo } from '../../api/hooks/user';

// interface TList {
//   id: number;
//   text: string;
// }

const Footer = Layout;

const PostPage = () => {
  const queryClient = useQueryClient();

  const navigate = useNavigate();

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  const useDeleteBoardMutation = useDeleteBoard();
  const [messageApi, contextHolder] = message.useMessage();

  const url = window.location.pathname;
  const real_id = url.substring(6);

  const { data, isLoading } = useGetBoard(parseInt(real_id));
  const { data: comments, isLoading: isLoadingComments } = useGetComments(
    parseInt(real_id),
  );
  const { data: userInfo } = useGetUserInfo();

  const createCommentMutation = useCreateComment(parseInt(real_id));
  const useReportMutation = useCreateReports();

  const [like, setLike] = useState(false);
  // const [isListModalOpen, setIsListModalOpen] = useState(false);
  // const [selectedItem, setSelectedItem] = useState(null);
  // 댓글 상태
  const [commentValue, setCommentValue] = useState({
    adopted: false,
    applied: false,
    hidden: false,
    content: '',
  });

  useEffect(() => {
    setHeaderTitle('도움요청');
  }, [setHeaderTitle]);

  const board = useMemo(() => {
    return data?.data;
  }, [data]);

  const commentsList = useMemo(() => {
    // 댓글 목록
    if (comments && comments.data) {
      return comments.data;
    } else {
      return [];
    }
  }, [comments]);

  const applicantList = useMemo(() => {
    return ['지원자1', '지원자2', '지원자3'];
  }, []);

  const userNickname = useMemo(() => {
    return userInfo?.data.body.nick_name;
  }, [userInfo]);

  const isAgency = useMemo(() => {
    if (userInfo?.data.body.manager_name) return true;
    return false;
  }, [userInfo]);

  // 수정 및 삭제 버튼 표시 여부를 결정하는 함수
  const isAuthor = useMemo(() => {
    // 게시글 작성자일 때 true
    return isAgency
      ? board?.userId === userInfo?.data.body.uid
      : board?.userNickname === userNickname;
  }, [board, isAgency, userInfo, userNickname]);

  const handleEditPageChange = () => {
    navigate(`/edit/${real_id}`);
  };

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
        await useDeleteBoardMutation.mutateAsync(real_id, {
          onSuccess: () => {
            messageApi.open({
              type: 'success',
              content: '게시글 삭제 완료',
              duration: 0.5,
              onClose() {
                queryClient.invalidateQueries({
                  queryKey: ['useInfiniteGetSearchBoard'],
                });
                navigate(PATH.HOME);
              },
            });
          },
          onError: (error) => {
            console.log(error);
          },
        });
      },
    });
  }, [useDeleteBoardMutation, queryClient, messageApi, navigate, real_id]);

  // 입력값 변경 핸들러
  const handleInputTextChange = useCallback(
    (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setCommentValue({ ...commentValue, content: e.target.value });
    },
    [commentValue],
  );

  // 댓글 등록
  const handleSubmitComment = useCallback(async () => {
    await createCommentMutation.mutateAsync(commentValue, {
      onSuccess: (data) => {
        console.log();
        queryClient.invalidateQueries({
          queryKey: ['useGetBoard'],
        });
        queryClient.invalidateQueries({
          queryKey: ['useGetComments'],
        });
        setCommentValue({
          adopted: false,
          applied: false,
          hidden: false,
          content: '',
        });
      },
      onError(error) {
        console.log('error');
      },
    });
  }, [commentValue, createCommentMutation, queryClient]);

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
            onError: (error) => {
              console.error('Error reporting post:', error);
            },
          },
        );
      },
    });
  }, [messageApi, useReportMutation, real_id]);

  const onApplicantClick = (applicant: any) => {
    console.log(`Selected applicant: ${applicant}`);
  };

  // const onItemClick = (item: any) => {
  //   setSelectedItem(item);
  // };
  // const isItemSelected = (item: any) => {
  //   return selectedItem === item;
  // };

  // // 지원자 목록 모달 창
  // const onOk2 = () => {
  //   if (selectedItem) {
  //     console.log('선택된 지원자: ', selectedItem);
  //     setIsListModalOpen(false);
  //   }
  // };
  // const onCancel2 = () => {
  //   setIsListModalOpen(false);
  // };

  const handleLike = () => {
    setLike(!like);
  };

  const volunteerInfo = useMemo(() => {
    return (
      <div css={cssVolunteerInfoStyle}>
        <div className="overview">
          <VolunteerBadge /> <div>필요 인원: {board?.volunteerPeople} 명</div>
        </div>
        <ul>
          <li>타임페이와 봉사활동 시간을 지급받을 수 있는 활동입니다.</li>
          <li>
            지급받은 봉사활동 인증서는 마이페이지 - 봉사활동 기록에서 확인할 수
            있습니다.
          </li>
        </ul>
      </div>
    );
  }, [board]);

  return (
    <Layout css={cssPostDetail}>
      {isLoading ? (
        <Spin css={cssSpinStyle} />
      ) : (
        <>
          <div css={cssPostDetailPage}>
            {contextHolder}
            {isAuthor && (
              <div css={cssQnaDeleteStyle}>
                <Button css={cssEditBtnStyle} onClick={handleEditPageChange}>
                  수정
                </Button>
                <Button css={cssDeleteBtnStyle} onClick={handleDelete}>
                  삭제
                </Button>
              </div>
            )}
            {!isAuthor && (
              <div css={cssReportContainer}>
                <Button css={cssReportBtnStyle} onClick={onReport}>
                  게시글 신고하기
                </Button>
              </div>
            )}
            <div css={cssPostDetailThird}>
              <div className="category">
                <div css={cssPostDetailCategory1}>카테고리</div>
                <div css={cssPostDetailCategory2}>{board?.category}</div>
              </div>
              <div css={cssPostDetailPay}>{board?.pay || '0'} TP</div>
            </div>
            <div css={cssPostDetailSecond}>
              <div css={cssPostDetailTitle}>{data?.data.title}</div>
              <div css={cssPostDetailStatus}>
                <PostStatusTag status={data?.data.state} />
              </div>
            </div>
            <div css={cssPostDetailFourth}>
              <div css={cssPostDetailRegion}>
                <FlagFilled style={{ marginRight: 15, color: 'black' }} />
                {data?.data.location}
              </div>
              <div css={cssPostDetailTime}>
                <ClockCircleOutlined
                  style={{ marginRight: 15, color: 'black' }}
                />
                {dayjs(board?.startTime, 'YYYY-MM-DDTHH:mm:ss').format(
                  'MM월 DD일 HH시 mm분',
                )}{' '}
                ~{' '}
                {dayjs(board?.endTime, 'YYYY-MM-DDTHH:mm:ss').format(
                  'HH시 mm분',
                )}
              </div>
            </div>
            {board?.volunteer && volunteerInfo}
            <div css={cssPostDetailFifth}>
              <div className="content">내용</div>
              <div css={cssPostDetailContent2}>
                <span>{data?.data.content}</span>
              </div>
              <div css={cssPostDetailAttachment}>{data?.data.imageUrl}</div>
            </div>
            <div css={cssPostDetailFirst}>
              <div css={cssPostDetailCreatedAt}>
                {data?.data.createdAt.substring(0, 10)}
              </div>
              <div css={cssPostDetailProfile}></div>
              <div css={cssPostDetailUser}>
                {isAgency
                  ? data?.data.organizationName
                  : data?.data.userNickname}
              </div>
              <div css={cssLikeContainer}>
                <p>관심 </p>
                {like === true ? (
                  <button css={cssLike} onClick={handleLike}>
                    <LikeClick style={{ width: 25, height: 25 }} />
                  </button>
                ) : (
                  <button css={cssLike} onClick={handleLike}>
                    <LikeDefault style={{ width: 25, height: 25 }} />
                  </button>
                )}
              </div>
            </div>
            <div css={cssLine4} />
            <h1>댓글</h1>
            {isLoadingComments ? (
              <Spin css={cssSpinCommentStyle} />
            ) : (
              <>
                {isAuthor && (
                  <ApplicantButton
                    applicantList={applicantList}
                    onItemClick={onApplicantClick}
                  />
                )}
                <div css={cssPostDetailSixth}>
                  {commentsList.length > 0 ? (
                    commentsList.map((data) => (
                      <Item
                        c={data}
                        id={data.id}
                        key={data.id}
                        messageApi={messageApi}
                      />
                    ))
                  ) : (
                    <p>
                      아직 댓글이 없어요🥹 <br /> 첫 댓글을 입력해보세요!
                    </p>
                  )}
                </div>
              </>
            )}
          </div>
          <Footer
            css={
              isAuthor
                ? cssAuthorFooter
                : board?.state === 'ACTIVITY_COMPLETE'
                ? cssAuthorFooter
                : cssNonAuthorFooter
            }
          >
            <div css={cssLine2} />
            {isAuthor && (
              <>
                <PostButton />
                <div css={cssLine5} />
              </>
            )}

            {!isAuthor && board?.state === 'ACTIVITY_COMPLETE' && (
              <>
                <PostButton />
                <div css={cssLine5} />
              </>
            )}

            <div css={cssPostFooter2}>
              <InputText
                onChange={handleInputTextChange}
                inputText={commentValue.content}
              />
              <button css={cssPostBtn} onClick={handleSubmitComment}>
                등록
              </button>
            </div>
          </Footer>
        </>
      )}
    </Layout>
  );
};
export default PostPage;
