import { Drawer, Pagination, Spin, Tabs, TabsProps } from 'antd';
import { useMemo } from 'react';
import { ICommentActivity } from '../../api/interfaces/IComment';
import { IBoard } from '../../api/interfaces/IPost';
import { IUser } from '../../api/interfaces/IUser';
import { cssTabStyle } from '../../styles/constants/tabStyle';
import ActivityCommentCard from '../ActivityCommentCard';
import ActivityPostCard from '../ActivityPostCard';
import { cssAnotherUserProfileDrawerStyle } from './AnotherUserProfileDrawer.styles';

interface AnotherUserProfileDrawerProps {
  open: boolean;
  onClose: () => void;
  user?: IUser;
  userPosts?: IBoard[];
  userComments?: ICommentActivity[];
}
const AnotherUserProfileDrawer = ({
  open,
  onClose,
  user,
  userPosts,
  userComments,
}: AnotherUserProfileDrawerProps) => {
  // api 합칠 때 user와 user의 활동기록을 api 로 받아오게 처리할 예정

  const ACTIVITY_TAB_KEYS = useMemo(() => {
    return { POST: '게시글', COMMENT: '댓글' } as const;
  }, []);

  const items: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: ACTIVITY_TAB_KEYS.POST,
        label: ACTIVITY_TAB_KEYS.POST,
        children: (
          <div className="activity-list-container">
            <div className="activity-list">
              {userPosts?.map((post) => (
                <ActivityPostCard key={post.d_boardId} post={post} />
              ))}
            </div>
            <Pagination
              pageSize={5}
              total={userPosts?.length}
              defaultCurrent={1}
            />
          </div>
        ),
      },
      {
        key: ACTIVITY_TAB_KEYS.COMMENT,
        label: ACTIVITY_TAB_KEYS.COMMENT,
        children: (
          <div className="activity-list-container">
            <div className="activity-list">
              {userComments?.map((comment) => (
                <ActivityCommentCard
                  key={comment.commentId}
                  comment={comment}
                />
              ))}
            </div>
            <Pagination
              pageSize={5}
              total={userComments?.length}
              defaultCurrent={1}
            />
          </div>
        ),
      },
    ];
  }, [ACTIVITY_TAB_KEYS, userPosts, userComments]);

  return (
    <Drawer
      css={cssAnotherUserProfileDrawerStyle}
      closable
      placement="bottom"
      open={open}
      onClose={onClose}
      height={750}
      title={null}
      contentWrapperStyle={{ borderRadius: '20px 20px 0 0' }}
    >
      {user && userPosts && userComments ? (
        <div className="another-user-profile-container">
          <div className="profile-container">
            <img
              src={
                user.profileImage ||
                'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png'
              }
              alt="프로필 이미지"
              width="70px"
              height="70px"
            />
            <div className="nickname-userPk-container">
              <span className="nickname">{user.nickname}</span>
              <span className="userPk">#{user.userPk}</span>
            </div>
          </div>
          <div className="activity-container">
            <Tabs
              css={cssTabStyle}
              defaultActiveKey={ACTIVITY_TAB_KEYS.POST}
              items={items}
            />
          </div>
        </div>
      ) : (
        <div className="spin-container">
          <Spin size="large" />
        </div>
      )}
    </Drawer>
  );
};
export default AnotherUserProfileDrawer;
