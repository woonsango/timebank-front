import { useEffect, useMemo } from 'react';
import { useInfiniteGetSearchBoard } from '../../api/hooks/post';
import SimplePostCard from '../../components/SimplePostCard';
import { useInView } from 'react-intersection-observer';

const SearchPage = () => {
  const { data, fetchNextPage, hasNextPage } = useInfiniteGetSearchBoard({
    perPage: 10,
    curPage: 0,
  });
  const [ref, inView] = useInView({ threshold: 0.3 });

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, fetchNextPage, hasNextPage]);

  const boardsList = useMemo(() => {
    if (data && data.pages && data.pages.length > 0) {
      return data.pages.map((page) => page.data.content).flat(1);
    }
  }, [data]);
  return (
    <div>
      {boardsList
        ? boardsList.map((board, index) => (
            <SimplePostCard
              key={board ? board.d_boardId : index}
              post={board}
            />
          ))
        : null}
      <div ref={ref} />
    </div>
  );
};

export default SearchPage;
