import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { IGetUserInformationResponse } from '../interfaces/IUser';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetUserInfomation = (userId: number) => {
  return useQuery<AxiosResponse<IGetUserInformationResponse>, AxiosError>({
    queryKey: ['useGetUserInfomation', userId],
    queryFn: () => apiRequest.get(`${API_URL.USERS_INFO}${userId}`),
    enabled: !!userId,
    refetchOnWindowFocus: false,
  });
};
