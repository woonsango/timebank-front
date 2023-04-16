import { atom } from 'recoil';
import { IGetNotificationRequest } from '../api/interfaces/INotification';

export const pushSearchState = atom<IGetNotificationRequest | undefined>({
  key: 'push-search',
  default: { title: undefined },
});
