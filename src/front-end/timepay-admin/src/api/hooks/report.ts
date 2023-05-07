import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import { IGetReportResponse } from '../interfaces/IReport';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetReports = (params?: any) => {
  return useQuery<AxiosResponse<IGetReportResponse>, AxiosError>({
    queryKey: ['useGetReports', params],
    queryFn: () =>
      params &&
      (params.reportId ||
        params.reporterName ||
        params.reason ||
        (params.startTime && params.endTime))
        ? apiRequest.get(API_URL.REPORTS__SEARCH, {
            params: {
              ...params,
              searchLabel: null,
              searchValue: null,
              startTime: params.startTime.format('YYYY-MM-DD HH:mm:ss'),
              endTime: params.endTime.format('YYYY-MM-DD HH:mm:ss'),
            },
          })
        : apiRequest.get(API_URL.REPORTS, { params }),
    refetchOnWindowFocus: false,
    retry: false,
  });
};
