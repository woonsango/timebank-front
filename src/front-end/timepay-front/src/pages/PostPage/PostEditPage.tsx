import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Button, Layout, message } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostEditPage,
  cssPostDetailFirst,
  cssPostDetailUser,
  cssPostDetailTitle,
  cssLine1,
  cssLine2,
  cssLine3,
  cssPostDetailProfile,
  cssPostEditSecond,
  cssPostDetailThird,
  cssPostDetailCategory1,
  cssPostDetailCategory2,
  cssPostDetailPay,
  cssPostDetailFourth,
  cssPostDetailRegion,
  cssPostDetailTime,
  cssPostDetailFifth,
  cssPostDetailContent1,
  cssPostEditContent2,
  cssPostDetailAttachment,
  cssPostDetail,
  cssPostTextarea,
  cssPostEditBtn1,
  cssPostEditBtn2,
  cssPostEditFooter,
  cssPostEditFooter2,
} from './PostEditPage.style';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';
import { useQueryClient } from 'react-query';
import { usePutBoard, useGetBoard } from '../../api/hooks/board';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { endTime, startTime } from '../../states/register';

const Footer = Layout;

const PostEditPage = () => {
  const navigate = useNavigate();
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('게시글 수정');
  }, [setHeaderTitle]);

  const queryClient = useQueryClient();
  const [messageApi, contextHolder] = message.useMessage();
  const url = window.location.pathname;
  const real_id = url.substring(6);
  const { data, isLoading } = useGetBoard(parseInt(real_id));
  const usePutBoardMutation = usePutBoard(parseInt(real_id));

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const id = Number(real_id);

  const handleTitle = (e: any) => {
    setTitle(e.target.value);
  };

  const handleContent = (e: any) => {
    setContent(e.target.value);
  };

  const handleClickCancel = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  const handleClickSave = useCallback(async () => {
    try {
      await usePutBoardMutation.mutateAsync({
        d_boardId: id,
        title: title,
        content: content,
        startTime: startTime,
        endTime: endTime,
      });
      navigate(-1);
    } catch (error) {
      console.error('확인에서 오류가 발생했습니다.', error);
    }
  }, [usePutBoardMutation, navigate, title, content]);

  return (
    <Layout css={cssPostDetail}>
      <div css={cssPostEditPage}>
        <div css={cssPostDetailFirst}>
          <div css={cssPostDetailProfile}></div>
          <div css={cssPostDetailUser}>{data?.data.userNickname}</div>
        </div>
        <div css={cssLine1} />
        <div css={cssPostEditSecond}>
          <div css={cssPostDetailTitle}>
            <TextArea
              defaultValue={data?.data.title}
              css={cssPostTextarea}
              style={{ height: 50, resize: 'none' }}
              onChange={handleTitle}
            />
          </div>
        </div>
        <div css={cssLine3} />
        <div>
          <div css={cssPostDetailThird}>
            <div css={cssPostDetailCategory1}>카테고리</div>
            <div css={cssPostDetailCategory2}>{data?.data.category}</div>
            <div css={cssPostDetailPay}>{data?.data.pay} TP</div>
          </div>
        </div>
        <div css={cssLine3} />
        <div css={cssPostDetailFourth}>
          <div css={cssPostDetailRegion}>
            <FlagFilled style={{ marginRight: 10 }} />
            {data?.data.location}
          </div>
          <div css={cssPostDetailTime}>
            <ClockCircleOutlined style={{ marginRight: 10 }} />
            {data?.data.startTime} ~ {data?.data.endTime}
          </div>
        </div>
        <div css={cssLine1} />
        <div css={cssPostDetailFifth}>
          <div css={cssPostDetailContent1}>내용</div>
          <TextArea
            defaultValue={data?.data.content}
            css={cssPostEditContent2}
            style={{ height: 200, resize: 'none' }}
            onChange={handleContent}
          >
            {data?.data.content}
          </TextArea>
        </div>
      </div>
      <Footer css={cssPostEditFooter}>
        <div css={cssLine2} />
        <div css={cssPostEditFooter2}>
          <Button css={cssPostEditBtn1} onClick={handleClickCancel}>
            취소
          </Button>
          <Button css={cssPostEditBtn2} onClick={handleClickSave}>
            저장
          </Button>
        </div>
      </Footer>
    </Layout>
  );
};

export default PostEditPage;
