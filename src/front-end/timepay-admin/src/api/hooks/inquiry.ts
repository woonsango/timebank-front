import { AxiosError, AxiosResponse } from 'axios';
import {
  IGetInquiryRequest,
  IGetInquiryResponse,
} from '../interfaces/IInquiry';
import { useQuery } from 'react-query';
import { API_URL } from '../urls';
import { apiRequest } from '../request';

export const useGetInquiries = (params?: IGetInquiryRequest) => {
  return useQuery<AxiosResponse<IGetInquiryResponse>, AxiosError>({
    queryKey: ['useGetInquiries', params],
    queryFn: () => apiRequest.get(API_URL.INQUIRIES, { params }),
    refetchOnWindowFocus: false,
    retry: false,
  });
};
