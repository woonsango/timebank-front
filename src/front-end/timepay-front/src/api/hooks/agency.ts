import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const usePostAgencyRegister = () => {
  return useMutation<AxiosResponse<any>, AxiosError, FormData>({
    mutationKey: 'usePostAgencyRegister',
    mutationFn: (data) =>
      apiRequest.postFormData(API_URL.ORGANIZATIONS_REGISTER, data),
  });
};
