import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';

import { apiRequest } from '../request';
import { API_URL } from '../urls';
import {
  IGetUserInfoUserIdRequest,
  IGetUserInfoResponse,
} from '../interfaces/IUser';

export const useGetUserInfos = (params?: IGetUserInfoUserIdRequest) => {
  return useQuery<AxiosResponse<IGetUserInfoResponse>, AxiosError>({
    queryKey: ['useGetUserInfos', params],
    queryFn: () =>
      params && params.userId
        ? apiRequest.get(API_URL.USERINFOS__SEARCH, { params })
        : apiRequest.get(API_URL.USERINFOS, { params }),
    refetchOnWindowFocus: false,
  });
};
