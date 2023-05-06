import { atom } from 'recoil';
import { IGetUserInfoRequest } from '../../api/interfaces/IUser';

export const mainSearchState = atom<IGetUserInfoRequest>({
  key: 'main-search',
  default: {
    nickName: undefined,
    pagingIndex: 1,
    pagingSize: 20,
  },
});
