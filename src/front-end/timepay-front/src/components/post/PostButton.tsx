import { useState } from 'react';
import { Modal } from 'antd';
import { cssPostButton, cssPostButtons } from './PostButton.style';

const PostButton = () => {
  const [buttonState, setButtonState] = useState<string>('start');
  const [prevButtonState, setPrevButtonState] = useState<string[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const onClickButton = (): void => {
    setPrevButtonState((prevState: string[]) => [...prevState, buttonState]);

    switch (buttonState) {
      case 'start':
        setButtonState('delayed');
        break;
      case 'delayed':
        setButtonState('completed');
        break;
      case 'completed':
        setButtonState('end');
        break;
      case 'end':
        setButtonState('pause');
        break;
      case 'review':
        setButtonState('theEnd');
        break;
      default:
        break;
    }
  };

  const showModal = () => {
    if (buttonState === 'end') {
      setIsModalOpen(true);
      return;
    }
  };

  const handleOk = () => {
    setButtonState('review');
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setButtonState('end');
    setIsModalOpen(false);
  };

  const onClickPrevButton = (): void => {
    const prevButtonStateCopy: string[] = [...prevButtonState];
    const prevState: string | undefined = prevButtonStateCopy.pop();
    setPrevButtonState(prevButtonStateCopy);
    setButtonState(prevState!);
  };

  return (
    <>
      <div css={cssPostButtons}>
        {prevButtonState.length > 0 &&
          buttonState !== 'review' &&
          buttonState !== 'theEnd' && (
            <button
              className="goBack"
              css={cssPostButton}
              onClick={onClickPrevButton}
            >
              이전
            </button>
          )}

        <button
          css={cssPostButton}
          onClick={() => {
            onClickButton();
            showModal();
          }}
          className={`${buttonState}`}
        >
          {buttonState === 'start' && '활동시작'}
          {buttonState === 'delayed' && '활동지연'}
          {buttonState === 'completed' && '활동완료'}
          {buttonState === 'end' && '종료'}
          {buttonState === 'pause' && '종료'}
          {buttonState === 'review' && '후기'}
          {buttonState === 'theEnd' && '모든 활동이 끝난 게시글입니다 :)'}
        </button>
      </div>
      <Modal
        title="정말 종료로 상태를 변경하시겠습니까?"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        okText="확인"
        cancelText="취소"
      >
        <p>확인 버튼을 누르시면, 타임페이가 교환됩니다.</p>
        <p>
          버튼을 누르시면 이전 상태로 되돌아갈 수 없으니 신중하게 눌러주세요.
        </p>
      </Modal>
    </>
  );
};

export default PostButton;
