import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import { IAgencyLoginRequest } from '../interfaces/IAgency';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const usePostAgencyLogin = () => {
  return useMutation<AxiosResponse<any>, AxiosError, IAgencyLoginRequest>({
    mutationKey: 'usePostAgencyLogin',
    mutationFn: (data) =>
      apiRequest.post(API_URL.ORGANIZATIONS_LOGIN, { ...data }),
  });
};

export const useAgencyLogout = () => {
  return useMutation<AxiosResponse<any>, AxiosError, any>({
    mutationKey: 'useAgencyLogout',
    mutationFn: () => apiRequest.post(API_URL.ORGANIZATIONS_LOGOUT),
  });
};

export const usePostAgencyRegister = () => {
  return useMutation<AxiosResponse<any>, AxiosError, FormData>({
    mutationKey: 'usePostAgencyRegister',
    mutationFn: (data) =>
      apiRequest.postFormData(API_URL.ORGANIZATIONS_REGISTER, data),
  });
};

export const useDeleteAgency = () => {
  return useMutation<AxiosResponse<any>, AxiosError>({
    mutationKey: 'useDeleteAgency',
    mutationFn: () => apiRequest.delete(API_URL.ORGANIZATIONS_DELETE),
  });
};
