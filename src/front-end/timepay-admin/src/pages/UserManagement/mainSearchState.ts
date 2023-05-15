import { atom } from 'recoil';
import { IGetUserInfoRequest } from '../../api/interfaces/IUser';

export const mainSearchState = atom<IGetUserInfoRequest>({
  key: 'userId',
  default: {
    userId: undefined,
    pagingIndex: 0,
    pagingSize: 20,
  },
});
