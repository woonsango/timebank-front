import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { IGetNotificationResponse } from '../interfaces/IPush';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetNotifications = () => {
  return useQuery<AxiosResponse<IGetNotificationResponse>, AxiosError>({
    queryKey: ['useGetNotifications'],
    queryFn: () => apiRequest.get(API_URL.NOTIFICATIONS),
    refetchOnWindowFocus: false,
  });
};
