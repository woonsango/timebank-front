import { atom } from 'recoil';
import { IGetCommentRequest } from '../api/interfaces/IComment';

export const commentSearchState = atom<IGetCommentRequest | undefined>({
  key: 'comment-search',
  default: {
    originBoardId: undefined,
    writerSearchKeyword: 'writerName',
    writerSearchValue: undefined,
  },
});
