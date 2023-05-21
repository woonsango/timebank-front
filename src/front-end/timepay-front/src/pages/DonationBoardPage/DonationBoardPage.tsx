import { Button, InputNumber, Modal, Progress, Spin } from 'antd';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { useGetDonationBoardWithId } from '../../api/hooks/donation';
import PostTypeTag from '../../components/PostTypeTag';
import { headerTitleState } from '../../states/uiState';
import { cssDonationBoardPageStyle } from './DonationBoardPage.style';

const DonationBoardPage = () => {
  const { boardId } = useParams();

  const { data, isLoading } = useGetDonationBoardWithId(
    parseInt(boardId || '-1'),
  );

  const setHeaderTitle = useSetRecoilState(headerTitleState);

  useEffect(() => {
    setHeaderTitle('기부하기');
  });

  const [isOpen, setIsOpen] = useState(false);
  const [donateAmount, setDonateAmount] = useState(10);

  const handleOnCancelOpen = useCallback(() => {
    setIsOpen(false);
  }, []);

  const handleOnClickDonate = useCallback(() => {
    setIsOpen(true);
  }, []);

  const footer = useMemo(() => {
    return (
      <>
        <Button onClick={handleOnCancelOpen}>취소</Button>
        <Button type="primary">확인</Button>
      </>
    );
  }, [handleOnCancelOpen]);

  return (
    <div css={cssDonationBoardPageStyle}>
      {isLoading ? (
        <Spin />
      ) : (
        <>
          <div className="donation-board-container">
            <div className="board-header">
              <PostTypeTag type={data?.data?.type} />
              <Progress
                style={{ width: 150 }}
                percent={
                  data?.data.donateTimePay !== undefined &&
                  data.data.targetTimePay !== undefined
                    ? (data?.data.donateTimePay / data?.data.targetTimePay) *
                      100
                    : 0
                }
                size="small"
              />
            </div>
            <div className="title-container">{data?.data.title}</div>
            <div className="donation-info-container">
              <div className="target-time-pay">
                목표 : {data?.data.targetTimePay} TP
              </div>
              <div className="donateTimePay-time-pay">
                현재 : {data?.data.donateTimePay} TP
              </div>
            </div>
            <div className="organization-info-container">
              <img
                alt="기관"
                src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
              />
              <div className="name">기관 이름</div>
            </div>
            <div className="content-container">{data?.data.content}</div>
            <Button type="primary" onClick={handleOnClickDonate}>
              기부하기
            </Button>
          </div>
          <Modal
            open={isOpen}
            onCancel={handleOnCancelOpen}
            title="기부하기"
            footer={footer}
            className="donate-modal"
          >
            <div className="">
              기관 이름에게 얼마를 기부할까요?
              <div className="guide">
                기부하는 즉시 타임페이가 소모되니 주의해주세요.
              </div>
              <InputNumber
                value={donateAmount}
                onChange={(value) => setDonateAmount(value || 0)}
                step={10}
                addonAfter="TP"
              />
            </div>
          </Modal>
        </>
      )}
    </div>
  );
};

export default DonationBoardPage;
