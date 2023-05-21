import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { apiRequest } from '../request';
import { API_URL } from '../urls';
import { IGetAgentResponse } from '../interfaces/IAgent';

export const useGetAgent = () => {
  return useQuery<AxiosResponse<IGetAgentResponse>, AxiosError>({
    queryKey: ['useGetAgent'],
    queryFn: () => apiRequest.get(API_URL.AGENT, {}),
    refetchOnWindowFocus: false,
    retry: false,
    onError: (err: any) => {
      console.log('[ERROR] useGetAgent:', err);
    },
  });
};
