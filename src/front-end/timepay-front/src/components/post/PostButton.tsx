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
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [isModalOpen2, setIsModalOpen2] = useState<boolean>(false);

  useEffect(() => {
    localStorage.setItem(`buttonState_${real_id}`, buttonState);
    localStorage.setItem(`buttonText_${real_id}`, buttonText);
  }, [buttonState, buttonText, real_id]);

  const startActivity = useCallback(async () => {
    try {
      setIsModalOpen(true); // 모달 열기
    } catch (error) {
      console.error('start에서 오류가 발생했습니다.', error);
    }
  }, []);

  const finishActivity = useCallback(async () => {
    try {
      setIsModalOpen2(true); // 모달 열기
    } catch (error) {
      console.error('finish에서 오류가 발생했습니다.', error);
    }
  }, []);

  const handleConfirm = useCallback(async () => {
    try {
      if (buttonState === 'start') {
        await usePutBoardStateStartMutation.mutateAsync();
        setButtonState('completed');
        setButtonText('활동완료');
      } else {
        await usePutBoardStateFinishMutation.mutateAsync();
        setButtonState('theEnd');
        setButtonText('활동이 종료된 게시글입니다 :)');
      }
      queryClient.invalidateQueries('');
      window.location.replace(url);
    } catch (error) {
      console.error('확인에서 오류가 발생했습니다.', error);
    } finally {
      setIsModalOpen(false);
      setIsModalOpen2(false);
    }
  }, [
    buttonState,
    usePutBoardStateStartMutation,
    usePutBoardStateFinishMutation,
    queryClient,
    url,
  ]);

  const handleCancel = useCallback(() => {
    setIsModalOpen(false);
    setIsModalOpen2(false);
  }, []);

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
      <Modal
        title="활동을 시작하시겠습니까?"
        open={isModalOpen}
        onOk={handleConfirm}
        onCancel={handleCancel}
        style={{ fontWeight: 400 }}
      >
        <h3 style={{ fontWeight: 400 }}>
          활동을 시작하면 활동을 취소할 수 없습니다
        </h3>
      </Modal>
      <Modal
        title="활동을 종료하시겠습니까?"
        open={isModalOpen2}
        onOk={handleConfirm}
        onCancel={handleCancel}
        style={{ fontWeight: 400 }}
      >
        <h3 style={{ fontWeight: 400 }}>
          활동이 종료되었다고 간주되며 <br />
          타임페이가 교환됩니다.
        </h3>
      </Modal>
    </>
  );
};

export default PostButton;
