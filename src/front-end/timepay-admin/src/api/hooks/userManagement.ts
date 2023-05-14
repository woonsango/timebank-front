import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';

import { apiRequest } from '../request';
import { API_URL } from '../urls';
import { IGetUserInfoRequest, IGetUserInfoResponse } from '../interfaces/IUser';

export const useGetUserInfos = (params?: IGetUserInfoRequest) => {
  return useQuery<AxiosResponse<IGetUserInfoResponse>, AxiosError>({
    queryKey: ['useGetUserInfos', params],
    queryFn: () =>
      params && params.value
        ? apiRequest.get(API_URL.USERINFOS__SEARCH, { params })
        : apiRequest.get(API_URL.USERINFOS, { params }),
    refetchOnWindowFocus: false,
  });
};
