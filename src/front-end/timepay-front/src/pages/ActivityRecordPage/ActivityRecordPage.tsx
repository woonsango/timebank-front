import { Form, Select, Tabs, TabsProps } from 'antd';
import { useEffect, useMemo } from 'react';
import { useSetRecoilState } from 'recoil';
import { ICommentActivity } from '../../api/interfaces/IComment';
import { IPost } from '../../api/interfaces/IPost';
import ActivityCommentCard from '../../components/ActivityCommentCard';
import ActivityPostCard from '../../components/ActivityPostCard';
import { headerTitleState } from '../../states/uiState';
import { cssTabStyle } from '../../styles/constants/tabStyle';
import { cssHorizontalForm } from './ActivityRecordPage.styles';

const ActivityRecordPage = () => {
  const [postForm] = Form.useForm();
  const [commentForm] = Form.useForm();
  const ACTIVITY_TAB_KEYS = useMemo(() => {
    return { POST: '게시글', COMMENT: '댓글' } as const;
  }, []);

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  const dummyActivities: {
    [key in typeof ACTIVITY_TAB_KEYS.POST | typeof ACTIVITY_TAB_KEYS.COMMENT]:
      | IPost[]
      | ICommentActivity[];
  } = useMemo(() => {
    return {
      [ACTIVITY_TAB_KEYS.POST]: [
        {
          postId: 1,
          createdAt: '10분 전',
          title: '예시 제목1',
          status: '활동완료',
          category: '이동 도움',
          pay: 120,
          startTime: '2022/02/17 14:00',
          endTime: '2022/02/17 16:00',
          region: '서울시 성북구 정릉3동',
          content: '이것좀 도와줘요',
          type: '도움요청',
          user: {
            userPk: 1,
            name: '하연',
            sex: '여자',
            birthday: '2000/01/15 00:00',
            profileMessage: '안녕',
            nickname: '하연하이',
            region: '서울시 광진구',
            phoneNumber: '01023860370',
            accountEmail: 'iioo3356@gmail.com',
            isAdmin: false,
            createdAt: '2022/02/14: 14:00',
          },
        },
        {
          postId: 2,
          type: '도움주기',
          createdAt: '23시간 59분 전',
          title: '예시 제목2',
          status: '매칭완료',
          category: '이동 도움',
          pay: 120,
          startTime: '2022/02/17 14:00',
          endTime: '2022/02/17 16:00',
          region: '서울시 성북구 정릉3동',
          attachment: 'sss',
          content:
            '이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요',
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
        },
        {
          postId: 3,
          type: '자유',
          createdAt: '2023-03-20 14:00:00',
          title: '예시 제목3',
          status: '활동취소',
          category: '이동 도움',
          pay: 120,
          startTime: '2022/02/17 14:00',
          endTime: '2022/02/17 16:00',
          region: '서울시 성북구 정릉3동',
          attachment: 'sss',
          content:
            '이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요이것좀 도와줘요',
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
        },
      ],
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
          <div style={{ width: '100%' }}>
            <Form form={postForm} css={cssHorizontalForm} layout="horizontal">
              <Form.Item name="type" style={{ width: 120 }} noStyle>
                <Select placeholder="유형 선택">
                  <Select.Option value="전체">전체</Select.Option>
                  <Select.Option value="도움주기">도움주기</Select.Option>
                  <Select.Option value="도움받기">도움받기</Select.Option>
                  <Select.Option value="자유">자유</Select.Option>
                  <Select.Option value="후기">후기</Select.Option>
                </Select>
              </Form.Item>
              <Form.Item name="status" style={{ width: 120 }} noStyle>
                <Select placeholder="상태 선택">
                  <Select.Option value="전체">전체</Select.Option>
                  <Select.Option value="매칭중">매칭중</Select.Option>
                  <Select.Option value="매칭완료">매칭완료</Select.Option>
                  <Select.Option value="활동시작">활동시작</Select.Option>
                  <Select.Option value="활동완료">활동완료</Select.Option>
                  <Select.Option value="활동취소">활동취소</Select.Option>
                  <Select.Option value="활동지연">활동지연</Select.Option>
                </Select>
              </Form.Item>
            </Form>
            {(dummyActivities[ACTIVITY_TAB_KEYS.POST] as IPost[]).map(
              (post) => (
                <ActivityPostCard key={post.postId} post={post} />
              ),
            )}
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
  }, [postForm, commentForm, ACTIVITY_TAB_KEYS, dummyActivities]);

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
