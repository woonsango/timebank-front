import { useState, useCallback, useEffect } from 'react';
import { Modal } from 'antd';
import { cssPostButton, cssPostButtons } from './PostButton.style';

import {
  usePutBoardStateStart,
  usePutBoardStateFinish,
} from '../../api/hooks/board';
import { useQueryClient } from 'react-query';

const PostButton = () => {
  const queryClient = useQueryClient();
  const url = window.location.pathname;
  const real_id = url.substring(6);
  const usePutBoardStateStartMutation = usePutBoardStateStart(
    parseInt(real_id),
  );
  const usePutBoardStateFinishMutation = usePutBoardStateFinish(
    parseInt(real_id),
  );

  const [buttonState, setButtonState] = useState<string>(() => {
    const storedState = localStorage.getItem(`buttonState_${real_id}`);
    return storedState ? storedState : 'start';
  });
  const [buttonText, setButtonText] = useState<string>(() => {
    const storedText = localStorage.getItem(`buttonText_${real_id}`);
    return storedText ? storedText : '활동시작';
  });
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    localStorage.setItem(`buttonState_${real_id}`, buttonState);
    localStorage.setItem(`buttonText_${real_id}`, buttonText);
  }, [buttonState, buttonText, real_id]);

  const startActivity = useCallback(async () => {
    try {
      await usePutBoardStateStartMutation.mutateAsync();
      queryClient.invalidateQueries('');
      window.location.replace(url);
      setButtonState('completed');
      setButtonText('활동완료');
    } catch (error) {
      console.error('start에서 오류가 발생했습니다.', error);
    }
  }, [queryClient, url]);

  const finishActivity = useCallback(async () => {
    try {
      await usePutBoardStateFinishMutation.mutateAsync();
      queryClient.invalidateQueries('');
      window.location.replace(url);
      setButtonState('theEnd');
      setButtonText('활동이 종료된 게시글입니다 :)');
    } catch (error) {
      console.error('finish에서 오류가 발생했습니다.', error);
    }
  }, [queryClient, url]);

  return (
    <>
      <div css={cssPostButtons}>
        {buttonState === 'start' ? (
          <button
            css={cssPostButton}
            onClick={startActivity}
            className={buttonState}
          >
            {buttonText}
          </button>
        ) : (
          <button
            css={cssPostButton}
            onClick={finishActivity}
            className={buttonState}
          >
            {buttonText}
          </button>
        )}
      </div>
    </>
  );
};

export default PostButton;
