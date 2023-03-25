import { useNavigate, useLocation } from 'react-router-dom';

const QnaDetailPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { title, content } = location.state;

  return (
    <>
      <div>
        <h2>{title}</h2>
        <p>{content}</p>
      </div>
      <button onClick={() => navigate(-1)}>뒤로 가기</button>
    </>
  );
};

export default QnaDetailPage;
