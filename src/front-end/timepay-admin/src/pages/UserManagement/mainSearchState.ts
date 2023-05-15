import { atom } from 'recoil';
import { IGetUserInfoUserIdRequest } from '../../api/interfaces/IUser';

export const mainSearchStateUserId = atom<IGetUserInfoUserIdRequest>({
  key: 'userId',
  default: {
    userId: undefined,
    pagingIndex: 0,
    pagingSize: 20,
  },
});
