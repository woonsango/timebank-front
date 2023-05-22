import { useState, useMemo, useCallback, useEffect } from 'react';
import { Modal } from 'antd';
import { cssPostButton, cssPostButtons } from './PostButton.style';

import {
  usePutBoardStateStart,
  usePutBoardStateFinish,
} from '../../api/hooks/board';
import { useMutation, useQueryClient } from 'react-query';

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

  const [buttonState, setButtonState] = useState<string>('start');
  const [buttonText, setButtonText] = useState<string>('활동시작');
  const [isModalOpen, setIsModalOpen] = useState(false);

  // const buttonText = useMemo(() => {
  //   switch (buttonState) {
  //     case 'start'
  //       return '활동시작';
  //     case 'completed':
  //       return '활동완료';
  //     case 'deleted':
  //       return '활동취소';
  //     case 'theEnd':
  //       return '활동이 종료된 게시글입니다 :)';
  //     default:
  //       return '';
  //   }
  // }, [buttonState]);

  // const onClickButton = (): void => {
  //   switch (buttonState) {
  //     case 'start':
  //       setButtonState('completed');
  //       break;
  //     case 'completed':
  //       setButtonState('theEnd');
  //       break;
  //     case 'deleted':
  //       setButtonState('theEnd');
  //       break;
  //     case 'theEnd':
  //       break;
  //     default:
  //       break;
  //   }
  // };

  const showModal = () => {
    if (buttonState === 'completed') {
      setIsModalOpen(true);
    }
  };

  const handleOk = () => {
    setButtonState('theEnd');
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setButtonState('start');
    setIsModalOpen(false);
  };

  const startActivity = useCallback(async () => {
    try {
      await usePutBoardStateStartMutation.mutateAsync();
      queryClient.invalidateQueries('');
      setButtonState('completed');
      setButtonText('활동완료');
    } catch (error) {
      console.error('start에서 오류가 발생했습니다.', error);
    }
  }, [buttonState, queryClient, buttonText]);

  const finishActivity = useCallback(async () => {
    try {
      await usePutBoardStateFinishMutation.mutateAsync();
      queryClient.invalidateQueries('');
      setButtonState('theEnd');
      setButtonText('활동이 종료된 게시글입니다 :)');
    } catch (error) {
      console.error('finish에서 오류가 발생했습니다.', error);
    }
  }, [buttonState, queryClient, buttonText]);

  return (
    <>
      <div css={cssPostButtons}>
        {buttonState === 'start' ? (
          <button
            css={cssPostButton}
            onClick={() => {
              startActivity();
            }}
            className={`${buttonState}`}
          >
            {buttonText}
          </button>
        ) : buttonState === 'completed' || buttonState === 'deleted' ? (
          <>
            <button
              css={cssPostButton}
              onClick={() => {
                finishActivity();
                showModal();
              }}
              className="completed"
            >
              활동완료
            </button>
            <button
              css={cssPostButton}
              onClick={() => {
                finishActivity();
              }}
              className="deleted"
            >
              활동종료
            </button>
          </>
        ) : (
          <button
            css={cssPostButton}
            onClick={() => {
              showModal();
            }}
            className={`${buttonState}`}
          >
            {buttonText}
          </button>
        )}
      </div>
      <Modal
        className="modal"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        okText="확인"
        cancelText="취소"
      >
        <h3 style={{ fontWeight: 500 }}>
          버튼을 누르시면 이전 상태로 되돌아갈 수 없으니 신중하게 눌러주세요!
        </h3>
        <h4 style={{ fontWeight: 400 }}>
          확인 버튼을 누르시면, 타임페이가 교환됩니다.
        </h4>
      </Modal>
    </>
  );
};

export default PostButton;
