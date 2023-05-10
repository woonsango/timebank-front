import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { IGetReportResponse, IPatchReportRequest } from '../interfaces/IReport';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetReports = (params?: any) => {
  return useQuery<AxiosResponse<IGetReportResponse>, AxiosError>({
    queryKey: ['useGetReports', params],
    queryFn: () =>
      params && params.startTime && params.endTime
        ? apiRequest.get(API_URL.REPORTS__SEARCH, {
            params: {
              ...params,
              searchLabel: null,
              searchValue: null,
              startTime: params.startTime.format('YYYY-MM-DD HH:mm:ss'),
              endTime: params.endTime.format('YYYY-MM-DD HH:mm:ss'),
            },
          })
        : params.reportId || params.reporterName || params.reason
        ? apiRequest.get(API_URL.REPORTS__SEARCH, {
            params: {
              ...params,
              searchLabel: null,
              searchValue: null,
            },
          })
        : apiRequest.get(API_URL.REPORTS, { params }),
    refetchOnWindowFocus: false,
    retry: false,
  });
};

export const usePatchReport = () => {
  return useMutation<AxiosResponse<string>, AxiosError, IPatchReportRequest>({
    mutationKey: 'usePatchReport',
    mutationFn: (data) =>
      apiRequest.patch(API_URL.REPORTS__PENALTY, {
        ...data,
      }),
  });
};
