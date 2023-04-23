import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { useSetRecoilState } from 'recoil';

import { headerTitleState } from '../../states/uiState';
import { PATH } from '../../utils/paths';
import './My.css';
import user from './dummy.json';
import { useGetUserInfomation } from '../../api/hooks/user';

const MyPage = () => {
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  const { data } = useGetUserInfomation(2); //parameter: uid, 추후 uid 쿠키 토큰에 저장해둘 필요 있음, api url 수정해야 함
  useEffect(() => {
    setHeaderTitle('내정보');

    console.log(data);

    // setImage(data?.data.image_url);
    // setNickName(data?.data.nick_name);
    // setTown('업데이트 예정');
    // setIntroduction('업데이트 예정');
    // setPersonalNum(data?.data.id);
    // setTimePay(data?.data.time_pay);

    setImage(
      'https://images.pexels.com/photos/2023384/pexels-photo-2023384.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    );
    setNickName('User1');
    setTown('서울특별시 성북구 정릉2동');
    setIntroduction('안녕하세요.');
    setPersonalNum('17');
    setTimePay('500');
  }, [data]);

  const userInfo = user.user1[0];
  const [image, setImage]: any = useState();
  const [nickName, setNickName]: any = useState();
  const [town, setTown]: any = useState();
  const [introduction, setIntroduction]: any = useState();
  const [personalNum, setPersonalNum]: any = useState();
  const [timePay, setTimePay]: any = useState();

  const navigate = useNavigate(); //history

  const handlePageMove = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  return (
    <div className="MyPage">
      <div className="MyContentWrap">
        <div className="MyEdit">
          <button className="Edit" onClick={() => handlePageMove(PATH.MY_EDIT)}>
            프로필 수정
          </button>
        </div>

        <div className="MyBlock">
          {' '}
          <div className="MyTopBox">
            <div className="MyImageWrap">
              <img src={image} className="MyProfileImage" />
            </div>
            <div className="space"></div>

            <div className="MyNameWrap">
              <div className="MyName">{nickName}</div>
              <div className="MyPersonalNum"> {'#' + personalNum}</div>
            </div>
          </div>
        </div>

        <div className="MyBlock">
          <div className="MyTotalBox">
            <div className="MyLeftBox">
              <div className="MyLeft">
                <div className="MyTitleText">나의 타임페이</div>
              </div>
              <div className="MyLeft">
                <div className="MyTitleText">나의 동네</div>
              </div>
              <div className="MyLeft">
                <div className="MyTitleText">나의 소개</div>
              </div>
            </div>

            <div className="MyRightBox">
              <div className="MyRight">
                <div className="MyContentText">{timePay}</div>
              </div>
              <div className="MyRight">
                <div className="MyContentText">{town}</div>
              </div>
              <div className="MyRight">
                <div className="MyContentText">{introduction}</div>
              </div>
            </div>
          </div>
        </div>

        <div className="MyBlock">
          <div className="MyBlockBox">
            <div className="MyPageMoveBox">
              <button
                className="MyPageText"
                onClick={() => handlePageMove(PATH.MY_ACTIVITY_RECORD)}
              >
                활동 기록
              </button>
            </div>

            <div className="MyPageMoveBox">
              <button
                className="MyPageText"
                onClick={() => handlePageMove(PATH.REPORT)}
              >
                신고 기록
              </button>
            </div>

            <div className="MyPageMoveBox">
              <button
                className="MyPageText"
                onClick={() => handlePageMove(PATH.INQUIRE)}
              >
                문의하기
              </button>
            </div>

            {/* <div className="MyPageMoveBox">
              <button
                className="MyPageText"
                onClick={() => handlePageMove(PATH.BOOKMARK)}
              >
                즐겨찾기
              </button>
            </div> */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
