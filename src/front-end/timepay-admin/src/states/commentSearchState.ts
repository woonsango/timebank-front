import { atom } from 'recoil';
import { IGetCommentRequest } from '../api/interfaces/IComment';

export const commentSearchState = atom<IGetCommentRequest>({
  key: 'comment-search',
  default: {
    originBoardId: undefined,
    writerSearchKeyword: 'writerName',
    writerSearchValue: undefined,
    pagingIndex: 0,
    pagingSize: 10,
  },
});
