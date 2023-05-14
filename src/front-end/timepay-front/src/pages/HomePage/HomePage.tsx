import { cssCategoryListStyle, cssHomePageStyle } from './HomePage.styles';
import { ReactComponent as Logo } from '../../assets/images/icons/timepay-character-logo.svg';
import { Button, Input, Spin } from 'antd';
import { useGetCategory } from '../../api/hooks/category';
import { useNavigate } from 'react-router-dom';
import { useCallback } from 'react';
import { PATH } from '../../utils/paths';
import useFontSize from '../../hooks/useFontSize';

const HomePage = () => {
  const navigate = useNavigate();

  const { scaleValue } = useFontSize();

  const { data, isLoading } = useGetCategory({
    type: '도움요청',
    useYn: 'Y',
  });

  const handleOnSearch = useCallback(() => {
    // 게시글 검색 state 에 추가
    navigate(PATH.SEARCH);
    window.scrollTo(0, 0);
  }, [navigate]);

  return (
    <div css={cssHomePageStyle(scaleValue)}>
      <div className="title-search-container">
        <Logo />
        <div className="title-search">
          <Input />
          <Button type="ghost" onClick={handleOnSearch}>
            검색
          </Button>
        </div>
      </div>
      <div className="category-search-container">
        {isLoading ? (
          <Spin size="large" />
        ) : (
          <>
            <div>당신의 도움이 필요해요!</div>
            <div css={cssCategoryListStyle(scaleValue)}>
              {data?.data.map((category) => (
                <Button key={category.categoryId} onClick={handleOnSearch}>
                  {category.categoryName}
                </Button>
              ))}
            </div>
          </>
        )}
      </div>
      <div className="info-container">
        타임페이 커뮤니티에서 모든 사용자는 도움을 주는 대가로 타임페이를 받을
        수 있어요. <br />
        받은 타임페이로 필요한 곳에 도움을 요청해보세요!
      </div>
    </div>
  );
};

export default HomePage;
