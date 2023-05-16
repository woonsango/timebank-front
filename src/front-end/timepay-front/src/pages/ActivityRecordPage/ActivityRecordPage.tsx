import { Form, Pagination, Select, Spin, Tabs, TabsProps } from 'antd';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { useGetUserBoards } from '../../api/hooks/user';
import { ICommentActivity } from '../../api/interfaces/IComment';
import { IGetUserBoardRequest } from '../../api/interfaces/IUser';
import ActivityCommentCard from '../../components/ActivityCommentCard';
import ActivityPostCard from '../../components/ActivityPostCard';
import { headerTitleState } from '../../states/uiState';
import { cssTabStyle } from '../../styles/constants/tabStyle';
import {
  cssActivityRecordPageStyle,
  cssHorizontalForm,
  cssNothingStyle,
  cssSpinStyle,
} from './ActivityRecordPage.styles';

const ActivityRecordPage = () => {
  const [boardSearchValue, setBoardSearchValue] =
    useState<IGetUserBoardRequest>({
      pagingIndex: 0,
      pagingSize: 5,
    });

  const { data: boardData, isLoading } = useGetUserBoards(boardSearchValue);

  const [postForm] = Form.useForm();
  const [commentForm] = Form.useForm();
  const ACTIVITY_TAB_KEYS = useMemo(() => {
    return { POST: '게시글', COMMENT: '댓글' } as const;
  }, []);

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  const boards = useMemo(() => {
    return boardData?.data.deal_boards.content;
  }, [boardData]);

  const handleOnChangeBoardForm = useCallback(
    (changedValues: { [key: string]: any }) => {
      // 옵션 검색 시 값이 바뀔 때마다 바로 api 호출, 페이지 초기화
      setBoardSearchValue({
        ...boardSearchValue,
        ...changedValues,
        pagingIndex: 0,
      });
    },
    [setBoardSearchValue, boardSearchValue],
  );

  const handleOnChangePageBoard = useCallback(
    (page: number, pageSize: number) => {
      // 옵션 검색 시 값이 바뀔 때마다 바로 api 호출
      setBoardSearchValue({
        ...boardSearchValue,
        pagingIndex: page - 1,
      });
    },
    [setBoardSearchValue, boardSearchValue],
  );

  //@ts-ignore
  const dummyActivities: {
    [key in typeof ACTIVITY_TAB_KEYS.COMMENT]: ICommentActivity[];
  } = useMemo(() => {
    return {
      [ACTIVITY_TAB_KEYS.COMMENT]: [
        {
          postId: 1,
          postTitle: '예시 제목1',
          commentId: 2,
          user: {
            userPk: 1,
            name: '하연',
            sex: '여자',
            birthday: '2000/01/15 00:00:00',
            profileMessage: '안녕',
            nickname: '하연하이',
            region: '서울시 광진구',
            phoneNumber: '01023860370',
            accountEmail: 'iioo3356@gmail.com',
            isAdmin: false,
            createdAt: '2022/02/14: 14:00:00',
          },
          parentCommentId: null,
          isApply: true,
          isSelected: true,
          isAuthorOfPost: false,
          isHidden: false,
          createdAt: '2023/04/02 00:00:00',
          updatedAt: undefined,
          content: '저 여기 근처 살아요 지원하겠습니다!',
        },
        {
          postId: 2,
          postTitle: '예시 제목2',
          commentId: 1,
          user: {
            userPk: 1,
            name: '하연',
            sex: '여자',
            birthday: '2000/01/15 00:00:00',
            profileMessage: '안녕',
            nickname: '하연하이',
            region: '서울시 광진구',
            phoneNumber: '01023860370',
            accountEmail: 'iioo3356@gmail.com',
            isAdmin: false,
            createdAt: '2022/02/14: 14:00:00',
          },
          parentCommentId: null,
          isApply: false,
          isSelected: false,
          isAuthorOfPost: true,
          isHidden: false,
          createdAt: '2023/04/02 00:00:00',
          updatedAt: undefined,
          content: '넵 여기로 오세요',
        },
      ],
    };
  }, [ACTIVITY_TAB_KEYS]);

  const items: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: ACTIVITY_TAB_KEYS.POST,
        label: ACTIVITY_TAB_KEYS.POST,
        children: (
          <div css={cssActivityRecordPageStyle} style={{ width: '100%' }}>
            <Form
              form={postForm}
              css={cssHorizontalForm}
              layout="horizontal"
              onValuesChange={handleOnChangeBoardForm}
            >
              <div>
                <Form.Item name="type" style={{ width: 120 }} noStyle>
                  <Select placeholder="유형 선택">
                    <Select.Option value="전체">전체</Select.Option>
                    <Select.Option value="help">도움요청</Select.Option>
                    <Select.Option value="helper">같이하기</Select.Option>
                    <Select.Option value="event">이벤트</Select.Option>
                  </Select>
                </Form.Item>
                <Form.Item name="boardStatus" style={{ width: 120 }} noStyle>
                  <Select placeholder="상태 선택">
                    <Select.Option value="ALL">전체</Select.Option>
                    <Select.Option value="MATCHING_IN_PROGRESS">
                      매칭중
                    </Select.Option>
                    <Select.Option value="MATCHING_COMPLETE">
                      매칭완료
                    </Select.Option>
                    <Select.Option value="ACTIVITY_IN_PROGRESS">
                      활동중
                    </Select.Option>
                    <Select.Option value="ACTIVITY_COMPLETE">
                      활동완료
                    </Select.Option>
                    <Select.Option value="ACTIVITY_CANCEL">
                      활동취소
                    </Select.Option>
                    <Select.Option value="ACTIVITY_DELAY">
                      활동지연
                    </Select.Option>
                  </Select>
                </Form.Item>
              </div>
              <div> 총 {boardData?.data.deal_boards.numberOfElements} 개</div>
            </Form>
            <div>
              {!isLoading && boards ? (
                boards.length > 0 ? (
                  <>
                    {boards?.map((post) => (
                      <ActivityPostCard key={post.d_boardId} post={post} />
                    ))}
                    <Pagination
                      current={(boardSearchValue.pagingIndex || 0) + 1}
                      pageSize={5}
                      total={boardData?.data.deal_boards.numberOfElements}
                      onChange={handleOnChangePageBoard}
                    />
                  </>
                ) : (
                  <div css={cssNothingStyle}>
                    <span className="emoji">😅</span>
                    <span>해당하는 게시글이 없습니다.</span>
                  </div>
                )
              ) : (
                <Spin css={cssSpinStyle} size="large" />
              )}
            </div>
          </div>
        ),
      },
      {
        key: ACTIVITY_TAB_KEYS.COMMENT,
        label: ACTIVITY_TAB_KEYS.COMMENT,
        children: (
          <div style={{ width: '100%' }}>
            <Form
              form={commentForm}
              css={cssHorizontalForm}
              layout="horizontal"
            >
              <Form.Item name="type" style={{ width: 120 }} noStyle>
                <Select placeholder="유형 선택">
                  <Select.Option value="전체">전체</Select.Option>
                  <Select.Option value="지원">지원</Select.Option>
                  <Select.Option value="선정">선정</Select.Option>
                </Select>
              </Form.Item>
            </Form>
            {(
              dummyActivities[ACTIVITY_TAB_KEYS.COMMENT] as ICommentActivity[]
            ).map((comment) => (
              <ActivityCommentCard key={comment.commentId} comment={comment} />
            ))}
          </div>
        ),
      },
    ];
  }, [
    boardSearchValue,
    boardData,
    isLoading,
    boards,
    postForm,
    commentForm,
    ACTIVITY_TAB_KEYS,
    handleOnChangeBoardForm,
    handleOnChangePageBoard,
    dummyActivities,
  ]);

  useEffect(() => {
    setHeaderTitle('활동기록');
  });

  return (
    <Tabs
      css={cssTabStyle}
      defaultActiveKey={ACTIVITY_TAB_KEYS.POST}
      items={items}
    />
  );
};

export default ActivityRecordPage;
