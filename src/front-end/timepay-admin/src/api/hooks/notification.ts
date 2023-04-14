import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { IPostNotificationRequest } from '../interfaces/INotification';
import { apiRequest } from '../request';
import { API_URL } from '../urls';


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

