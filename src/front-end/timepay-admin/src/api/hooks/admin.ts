import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useAdminRegister = () => {
  return useMutation<AxiosResponse<any>, AxiosError, any>({
    mutationKey: 'useAdminRegister',
    mutationFn: (data) =>
      apiRequest.post(API_URL.ADMIN_REGISTER, {
        ...data,
      }),
  });
};
