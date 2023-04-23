import {
  CommentOutlined,
  ExclamationOutlined,
  HeartOutlined,
  NotificationOutlined,
} from '@ant-design/icons';
import { Collapse } from 'antd';
import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { INotification, IPush } from '../../api/interfaces/IPush';
import { cssPushCollapseStyle, cssPushPanelStyle } from './PushCollapse.styles';

const PushCollapse = ({
  pushes,
}: {
  pushes: IPush[] | INotification[] | undefined;
}) => {
  const navigate = useNavigate();
  const getIcon = useCallback((push: IPush | INotification) => {
    switch (push.type) {
      case 'bookmark':
        return <HeartOutlined />;
      case 'comment':
        return <CommentOutlined />;
      case 'notice':
        return <NotificationOutlined />;
      case 'activity':
        return <ExclamationOutlined />;
    }
  }, []);

  const getTitle = useCallback((push: IPush | INotification) => {
    if (push.type === 'comment')
      return (
        <>
          <span>{push.title}</span>
          <br />
          <span className="comment-preview">{push.content}</span>
        </>
      );
    else if (push.type === 'notice') {
      return (
        <div className="detail-push">
          <span>{push.title}</span>
          <span className="show-detail">자세히 보기</span>
        </div>
      );
    } else {
      return (
        <div>
          <span>{push.title}</span>
        </div>
      );
    }
  }, []);

  const handleOnClickHeader = useCallback(
    (push: IPush | INotification) => {
      if (push.type !== 'notice' && push.link) {
        navigate(push.link);
      }
    },
    [navigate],
  );

  const getHeader = useCallback(
    (push: IPush | INotification) => {
      return (
        <div className="push-header" onClick={() => handleOnClickHeader(push)}>
          <span className="icon">{getIcon(push)}</span>
          <span className="title">{getTitle(push)}</span>
        </div>
      );
    },
    [getIcon, getTitle, handleOnClickHeader],
  );

  return (
    <Collapse
      css={cssPushCollapseStyle}
      expandIconPosition="end"
      bordered={false}
    >
      {pushes?.map((push, index) => (
        <Collapse.Panel
          className={push.isAlreadyRead ? 'is-already-read' : 'not-read'}
          css={cssPushPanelStyle}
          key={index}
          header={getHeader(push)}
          showArrow={false}
          collapsible={push.type === 'notice' ? 'header' : 'disabled'}
        >
          <div>{push.content}</div>
        </Collapse.Panel>
      ))}
    </Collapse>
  );
};
export default PushCollapse;
