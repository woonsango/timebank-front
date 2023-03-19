import { useCallback, useEffect, useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';

import './MyEdit.css';
import user from './dummy.json';
import { useNavigate } from 'react-router';
import { PATH } from '../../utils/paths';
import MyEdit_imageSet from './MyEdit_imageSet';

const MyEditPage = () => {
  const userInfo = user.user1[0];
  const [image, setImage]: any = useState();
  const [nickName, setNickName]: any = useState();
  const [realName, setRealName]: any = useState();
  const [gender, setGender]: any = useState();
  const [age, setAge]: any = useState();
  const [town, setTown]: any = useState();
  const [introduction, setIntroduction]: any = useState();
  //const [personalNum, setPersonalNum]: any = useState();

  const navigate = useNavigate(); //history

  const handlePageMove = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  const setHeaderTitle = useSetRecoilState(headerTitleState);
  useEffect(() => {
    setHeaderTitle('내 정보 수정');
    /*
    fetch('http://localhost:5000/user')
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setUserData(data);
      });*/
    setImage(userInfo.img);
    setNickName(userInfo.nickName);
    setRealName(userInfo.realName);
    setGender(userInfo.gender);
    setAge(userInfo.age);
    setTown(userInfo.town);
    setIntroduction(userInfo.introduction);
    //setPersonalNum(userInfo.personalNum);
  }, []);

  return (
    <div className="page">
      <div className="EditContentWrap">
        <div className="EditImageWrap">
          <MyEdit_imageSet />
        </div>

        <div className="EditContentBox">
          <div className="EditLeftBox">
            <div className="EditLeft">
              <div className="EditTitleText">닉네임</div>
            </div>
            <div className="EditLeft">
              <div className="EditTitleText">이름</div>
            </div>
            <div className="EditLeft">
              <div className="EditTitleText">성별</div>
            </div>
            <div className="EditLeft">
              <div className="EditTitleText">나이</div>
            </div>
            <div className="EditLeft">
              <div className="EditTitleText">지역 정보</div>
            </div>
            <div className="EditLeft">
              <div className="EditTitleText">소개</div>
            </div>
          </div>

          <div className="EditRightBox">
            <div className="EditRight">
              <div className="EditInputWrap">
                <input placeholder={nickName} className="EditInput" />
              </div>
            </div>
            <div className="EditRight">
              <div className="EditInputWrap">
                <input placeholder={realName} className="EditInput" />
              </div>
            </div>
            <div className="EditRight">
              <div className="EditInputWrap">
                <input placeholder={gender} className="EditInput" />
              </div>
            </div>
            <div className="EditRight">
              <div className="EditInputWrap">
                <input placeholder={age} className="EditInput" />
              </div>
            </div>
            <div className="EditRight">
              <div className="EditInputWrap">
                <input placeholder={town} className="EditInput" />
              </div>
            </div>
            <div className="EditRight">
              <div className="EditInputWrap">
                <input placeholder={nickName} className="EditInput" />
              </div>
            </div>
          </div>
        </div>

        <div className="EditBtnWrap">
          <button
            className="EditBottomButton"
            onClick={() => handlePageMove(PATH.MY)}
          >
            수정 완료
          </button>
        </div>
      </div>
    </div>
  );
};

export default MyEditPage;
