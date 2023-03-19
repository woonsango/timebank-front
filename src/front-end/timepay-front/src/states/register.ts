import { atom } from 'recoil';

// selectedTags와 setSelectedTags 변수 반환
export const selectedTagsState = atom<string[]>({
  key: 'selectedTagsState',
  default: [],
});
