import { atom } from 'recoil';
import { IGetAgencyRequest } from '../api/interfaces/IAgency';

export const agencySearchState = atom<IGetAgencyRequest>({
  key: 'agency-search',
  default: {
    pageIndex: 0,
    pageSize: 999,
    volunteer: 'n',
    volunteerCheck: false,
    query: undefined,
    queryValue: 'business',
  },
});
