import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { apiRequest } from '../request';
import { API_URL } from '../urls';
import { IGetApplicantResponse } from '../interfaces/IApplicant';

export const useGetApplicant = () => {
  return useQuery<AxiosResponse<IGetApplicantResponse>, AxiosError>({
    queryKey: ['useGetApplicant'],
    queryFn: () => apiRequest.get(API_URL.APPLICANT, {}),
    refetchOnWindowFocus: false,
    retry: false,
    onError: (err: any) => {
      console.log('[ERROR] useGetApplicant:', err);
    },
  });
};
