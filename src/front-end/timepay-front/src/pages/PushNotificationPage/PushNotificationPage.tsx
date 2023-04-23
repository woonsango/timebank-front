import { Spin, Tabs, TabsProps } from 'antd';
import { useEffect, useMemo } from 'react';
import { useSetRecoilState } from 'recoil';
import { useGetNotifications } from '../../api/hooks/push';
import { INotification, IPush, PUSH_TYPE } from '../../api/interfaces/IPush';
import PushCollapse from '../../components/PushCollapse';
import { headerTitleState } from '../../states/uiState';
import { cssTabStyle } from '../../styles/constants/tabStyle';

const PushNotificationPage = () => {
  const { data, isLoading } = useGetNotifications();

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  useEffect(() => {
    setHeaderTitle('알림함');
  });

  const notifications = useMemo(() => {
    return data?.data.content.map((notice) => ({
      ...notice,
      isAlreadyRead: true,
      type: 'notice',
    }));
  }, [data]);

  const dummyPushes: {
    [key in typeof PUSH_TYPE.ACTIVITY | typeof PUSH_TYPE.ALL]:
      | IPush[]
      | INotification[]
      | undefined;
  } = useMemo(() => {
    return {
      [PUSH_TYPE.ALL]: notifications,
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
  }, [notifications]);

  const items: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: PUSH_TYPE.ALL,
        label: PUSH_TYPE.ALL,
        children: isLoading ? (
          <Spin size="large" />
        ) : (
          <PushCollapse pushes={dummyPushes[PUSH_TYPE.ALL]} />
        ),
      },
      {
        key: PUSH_TYPE.ACTIVITY,
        label: PUSH_TYPE.ACTIVITY,
        children: <PushCollapse pushes={dummyPushes[PUSH_TYPE.ACTIVITY]} />,
      },
    ];
  }, [isLoading, dummyPushes]);
  return (
    <Tabs css={cssTabStyle} defaultActiveKey={PUSH_TYPE.ALL} items={items} />
  );
};

export default PushNotificationPage;
