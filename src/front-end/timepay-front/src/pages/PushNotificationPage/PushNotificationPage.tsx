import { Tabs, TabsProps } from 'antd';
import { useEffect, useMemo } from 'react';
import { useSetRecoilState } from 'recoil';
import { IPush, PUSH_TYPE } from '../../api/interfaces/IPush';
import PushCollapse from '../../components/PushCollapse';
import { headerTitleState } from '../../states/uiState';
import { cssTabStyle } from '../../styles/constants/tabStyle';

const PushNotificationPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);

  useEffect(() => {
    setHeaderTitle('알림함');
  });

  const dummyPushes: {
    [key in typeof PUSH_TYPE.ACTIVITY | typeof PUSH_TYPE.ALL]: IPush[];
  } = useMemo(() => {
    return {
      [PUSH_TYPE.ALL]: [
        {
          pushId: 1,
          type: 'bookmark',
          title: '"정릉3동 주민센터 봉사자 모집" 글이 활동 완료되었습니다.',
          link: '/post/3',
          isAlreadyRead: false,
        },
        {
          pushId: 2,
          type: 'notice',
          title: '요즘 신고 건수가 많아요',
          content: '다들 건강한 온라인 문화를 지킵시다',
          isAlreadyRead: false,
        },
        {
          pushId: 3,
          type: 'comment',
          title: '"~~" 글에 댓글이 달렸습니다.',
          content:
            '이 장소 주변에 식당 있나요? 국민대학교 주변엔 맛나게 먹을 수 있는 곳이 어디있을까요...?? 학교에서 5분 거리면 좋겠고 웨이팅도 없고 음식이 빨리 나오면 좋을 것 같아요',
          link: '/post/5',
          isAlreadyRead: false,
        },
        {
          pushId: 4,
          type: 'activity',
          title: '"##" 글의 활동 시작 시간입니다.',
          link: '/post/7',
          isAlreadyRead: true,
        },
        {
          pushId: 5,
          type: 'activity',
          title: '"@@" 글에 선정되었습니다.',
          link: '/post/8',
          isAlreadyRead: true,
        },
        {
          pushId: 6,
          type: 'notice',
          title: '서비스 점검 시간 안내',
          content:
            '2023년 3월 29일 03:00 ~ 05:00 점검시간입니다.\n서비스 이용 시 참고해주세요.',
          link: '/post/3',
          isAlreadyRead: true,
        },
        {
          pushId: 7,
          type: 'activity',
          title: '"##" 글의 활동 시작 시간입니다.',
          link: '/post/7',
          isAlreadyRead: true,
        },
        {
          pushId: 8,
          type: 'activity',
          title: '"@@" 글에 선정되었습니다.',
          link: '/post/8',
          isAlreadyRead: true,
        },
        {
          pushId: 9,
          type: 'notice',
          title: '서비스 점검 시간 안내',
          content:
            '2023년 3월 29일 03:00 ~ 05:00 점검시간입니다.\n서비스 이용 시 참고해주세요.',
          link: '/post/3',
          isAlreadyRead: true,
        },
        {
          pushId: 10,
          type: 'activity',
          title: '"##" 글의 활동 시작 시간입니다.',
          link: '/post/7',
          isAlreadyRead: true,
        },
        {
          pushId: 11,
          type: 'activity',
          title: '"@@" 글에 선정되었습니다.',
          link: '/post/8',
          isAlreadyRead: true,
        },
        {
          pushId: 12,
          type: 'notice',
          title: '서비스 점검 시간 안내',
          content:
            '2023년 3월 29일 03:00 ~ 05:00 점검시간입니다.\n서비스 이용 시 참고해주세요.',
          link: '/post/3',
          isAlreadyRead: true,
        },
        {
          pushId: 13,
          type: 'activity',
          title: '"##" 글의 활동 시작 시간입니다.',
          link: '/post/7',
          isAlreadyRead: true,
        },
        {
          pushId: 14,
          type: 'activity',
          title: '"@@" 글에 선정되었습니다.',
          link: '/post/8',
          isAlreadyRead: true,
        },
        {
          pushId: 15,
          type: 'notice',
          title: '서비스 점검 시간 안내',
          content:
            '2023년 3월 29일 03:00 ~ 05:00 점검시간입니다.\n서비스 이용 시 참고해주세요.',
          link: '/post/3',
          isAlreadyRead: true,
        },
      ],
      [PUSH_TYPE.ACTIVITY]: [
        {
          pushId: 4,
          type: 'activity',
          title: '## 게시글 활동 시작 시간입니다.',
          link: '/post/7',
          isAlreadyRead: true,
        },
        {
          pushId: 5,
          type: 'activity',
          title: '@@ 게시글에 선정되었습니다.',
          link: '/post/8',
          isAlreadyRead: true,
        },
      ],
    };
  }, []);

  const items: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: PUSH_TYPE.ALL,
        label: PUSH_TYPE.ALL,
        children: <PushCollapse pushes={dummyPushes[PUSH_TYPE.ALL]} />,
      },
      {
        key: PUSH_TYPE.ACTIVITY,
        label: PUSH_TYPE.ACTIVITY,
        children: <PushCollapse pushes={dummyPushes[PUSH_TYPE.ACTIVITY]} />,
      },
    ];
  }, [dummyPushes]);
  return (
    <Tabs css={cssTabStyle} defaultActiveKey={PUSH_TYPE.ALL} items={items} />
  );
};

export default PushNotificationPage;
