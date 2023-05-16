import { AxiosError, AxiosResponse } from 'axios';
import {
  IGetInquiryDetailResponse,
  IGetInquiryRequest,
  IGetInquiryResponse,
} from '../interfaces/IInquiry';
import { useMutation, useQuery } from 'react-query';
import { API_URL } from '../urls';
import { apiRequest } from '../request';

export const useGetInquiry = (params?: IGetInquiryRequest) => {
  return useQuery<AxiosResponse<IGetInquiryResponse>, AxiosError>({
    queryKey: ['useGetInquiry', params],
    queryFn: () => apiRequest.get(API_URL.INQUIRY, { params }),
    refetchOnWindowFocus: false,
    retry: false,
  });
};

export const useGetInquiryDetail = (params: any) => {
  return useQuery<AxiosResponse<any>, AxiosError>({
    queryKey: ['useGetInquiryDetail', params],
    queryFn: () =>
      apiRequest.get(API_URL.INQUIRY__DETAIL, {
        params: {
          inquiryId: params.inquiryId,
        },
      }),
    refetchOnWindowFocus: false,
    retry: false,
  });
};

export const usePostInquiryAnswer = () => {
  return useMutation<AxiosResponse<any>, AxiosError, any>({
    mutationKey: 'usePostInquiryAnswer',
    mutationFn: (data) =>
      apiRequest.post(API_URL.INQUIRY__ANSWER, {
        ...data,
      }),
  });
};
