import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import { ILoginRequest } from '../interfaces/ILogin';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const usePasswordChange = () => {
  return useMutation<AxiosResponse<any>, AxiosError, any>({
    mutationKey: 'usePasswordChange',
    mutationFn: (data) =>
      apiRequest.post(API_URL.ADMIN_PASSWORD_CHANGE, { ...data }),
  });
};
