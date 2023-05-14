import { atom } from 'recoil';

export const headerTitleState = atom<string | null>({
  key: 'header-title',
  default: null,
});

export const fontSizeState = atom<'big' | 'small'>({
  key: 'font-size',
  default: 'small',
});
