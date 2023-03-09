import MyPage_profileImage from './Elements/MyPage_profileImage';
import MyPage_nickname from './Elements/MyPage_nickname';
import MyPage_privateNumber from './Elements/MyPage_privateNumber';
import MyPage_town from './Elements/MyPage_town';
import MyPage_timepaySaved from './Elements/MyPage_timepaySaved';
import MyPage_introduction from './Elements/MyPage_introduction';
import MyPage_toolList from './Elements/MyPage_toolList';
import MyPage_editBtn from './Elements/MyPage_editBtn';

import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';

const MyPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('내정보');
  });

  return (
    <div>
      <MyPage_profileImage />
      <MyPage_nickname />
      <MyPage_privateNumber />
      <MyPage_town />
      <MyPage_timepaySaved />
      <MyPage_introduction />
      <MyPage_toolList />
      <MyPage_editBtn />
    </div>
  );
};

export default MyPage;
