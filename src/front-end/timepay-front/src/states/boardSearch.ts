import { atom } from 'recoil';
import { IGetSearchBoardRequest } from '../api/interfaces/IPost';

export const initialBoardSearchState = {
  pagingIndex: 0,
  pagingSize: 10,
  title: undefined,
  type: undefined,
  category: undefined,
  sortType: undefined,
  startTime: undefined,
  endTime: undefined,
  volunteer: undefined,
};

export const boardSearchState = atom<IGetSearchBoardRequest>({
  key: 'board-search',
  default: initialBoardSearchState,
});
