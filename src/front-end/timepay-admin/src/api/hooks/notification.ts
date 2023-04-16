import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import {
  IGetNotificationRequest,
  IGetNotificationResponse,
  IPostNotificationRequest,
} from '../interfaces/INotification';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetNotifications = (params?: IGetNotificationRequest) => {
  return useQuery<AxiosResponse<IGetNotificationResponse>, AxiosError>({
    queryKey: ['useGetNotifications', params],
    queryFn: () =>
      params && params.title
        ? apiRequest.get(API_URL.NOTIFICATIONS__SEARCH, { params })
        : apiRequest.get(API_URL.NOTIFICATIONS, { params }),
    refetchOnWindowFocus: false,
  });
};

export const useCreateNotifications = () => {
  return useMutation<
    AxiosResponse<boolean>,
    AxiosError,
    IPostNotificationRequest
  >({
    mutationKey: 'useCreateNotifications',
    mutationFn: (data) =>
      apiRequest.post(API_URL.NOTIFICATIONS, {
        ...data,
        notice: true,
        imageUrl: null,
      }),
  });
};

export const useDeleteNotifications = () => {
  return useMutation<AxiosResponse<boolean>, AxiosError, number[]>({
    mutationKey: 'useDeleteNotifications',
    mutationFn: (deleteNotificationIds) =>
      apiRequest.delete(API_URL.NOTIFICATIONS, {
        data: { ids: deleteNotificationIds },
      }),
  });
};
