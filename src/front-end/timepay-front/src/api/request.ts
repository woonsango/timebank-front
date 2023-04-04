import axios from 'axios';
import { getTokenFromCookie } from '../utils/token';

const request = axios.create({
  withCredentials: true,
  timeout: 1000000,
  headers: {
    'content-type': 'application/json',
  },
  // 여기에 토큰 넣으면 될 것 같은데 굳이 인터셉터에서 쓰는 이유를 잘 몰겠음
  baseURL: 'http://localhost:8080/',
});

request.interceptors.request.use((config) => {
  const token = getTokenFromCookie();
  // 여기에 token 이 필요한 녀석 아닌 녀석 분기하면 될 듯
  config.headers.set('Authorization', `Baerer ${token}` || undefined);
  return config;
});

request.interceptors.response.use();

export const apiRequest = {
  get: (url: string, params?: object) => request.get(url, { ...params }),
  post: (url: string, data: unknown) => request.post(url, data),
  patch: (url: string, data: unknown) => request.patch(url, data),
  put: (url: string, data: unknown) => request.put(url, data),
  delete: (url: string) => request.delete(url),
};
