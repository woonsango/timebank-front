import { useCallback, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';

const PostPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const {
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
  } = location.state;

  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  return (
    <div>
      <div css={cssMainHeaderStyle}>
        <BackArrow onClick={handleClickBack} />
        <span>도움받기</span>
      </div>
      <div>
        <div>{type}</div>
        <div>{title}</div>
        <div>{content}</div>
        <div>{createdAt}</div>
        <div>{category}</div>
        <div>{status}</div>
        <div>{pay}</div>
        <div>{region}</div>
        <div>{startTime}</div>
        <div>{endTime}</div>
        <div>{attachment}</div>
      </div>
    </div>
  );
};
export default PostPage;
