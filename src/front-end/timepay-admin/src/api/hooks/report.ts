import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { IGetReportResponse } from '../interfaces/IReport';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetReports = (params?: any) => {
  return useQuery<AxiosResponse<IGetReportResponse>, AxiosError>({
    queryKey: ['useGetReports', params],
    queryFn: () => apiRequest.get(API_URL.REPORTS, { params }),
    refetchOnWindowFocus: false,
  });
};
