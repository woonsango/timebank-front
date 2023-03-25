import { List } from 'antd';
import { IQna } from '../../api/interfaces/IQna';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { cssLineStyle, cssQnaListStyle } from './QnaList.style';

interface QnaListProps {
  qna?: IQna;
}

const QnaList = ({ qna }: QnaListProps) => {
  const color =
    qna?.status === '답변완료'
      ? `${COMMON_COLOR.MAIN3}`
      : `${COMMON_COLOR.FONT3}`;

  const handlePageChange = () => {};

  return (
    <>
      <List.Item css={cssQnaListStyle} onClick={handlePageChange}>
        <div className="qnaStatus" style={{ color }}>
          {qna?.status}
        </div>
        <div className="qnaCategory">{qna?.category}</div>
        <div className="qnaTitle">{qna?.title}</div>
      </List.Item>
      <List.Item.Meta description={<hr css={cssLineStyle} />} />
    </>
  );
};

export default QnaList;
