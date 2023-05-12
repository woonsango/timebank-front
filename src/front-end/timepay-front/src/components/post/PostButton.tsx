import { useState, useMemo } from 'react';
import { Modal } from 'antd';
import { cssPostButton, cssPostButtons } from './PostButton.style';

const PostButton = () => {
  const [buttonState, setButtonState] = useState<string>('start');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const buttonText = useMemo(() => {
    switch (buttonState) {
      case 'start':
        return '활동시작';
      case 'completed':
        return '활동완료';
      case 'deleted':
        return '활동취소';
      case 'theEnd':
        return '활동이 종료된 게시글입니다 :)';
      default:
        return '';
    }
  }, [buttonState]);

  const onClickButton = (): void => {
    switch (buttonState) {
      case 'start':
        setButtonState('completed');
        break;
      case 'completed':
        setButtonState('theEnd');
        break;
      case 'deleted':
        setButtonState('theEnd');
        break;
      case 'theEnd':
        break;
      default:
        break;
    }
  };

  const showModal = () => {
    if (buttonState === 'completed') {
      setIsModalOpen(true);
    }
  };

  const handleOk = () => {
    setButtonState('completed');
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setButtonState('start');
    setIsModalOpen(false);
  };

  return (
    <>
      <div css={cssPostButtons}>
        {buttonState === 'start' ? (
          <button
            css={cssPostButton}
            onClick={() => {
              onClickButton();
              // showModal();
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
                setButtonState('theEnd');
                showModal();
              }}
              className="completed"
            >
              활동완료
            </button>
            <button
              css={cssPostButton}
              onClick={() => {
                setButtonState('theEnd');
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
              onClickButton();
              showModal();
            }}
            className={`${buttonState}`}
          >
            {buttonText}
          </button>
        )}
      </div>
      <Modal
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        okText="확인"
        cancelText="취소"
      >
        <h2>
          🔆 버튼을 누르시면 이전 상태로 되돌아갈 수 없으니 신중하게 눌러주세요!
          🔆
        </h2>
        <h3>확인 버튼을 누르시면, 타임페이가 교환됩니다.</h3>
      </Modal>
    </>
  );
};

export default PostButton;
