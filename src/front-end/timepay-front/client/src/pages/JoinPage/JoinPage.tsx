import Join_imageSet from './Join_imageSet';
import './Join.css';
import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import Join_selectTown from './Join_selectTown';
import { PATH } from '../../utils/paths';

const JoinPage = () => {
  //이름, 닉네임, 지역, 전화번호(필)
  //프로필 이미지, 소개글(선)
  const [realName, setRealName] = useState('');
  const [nickName, setNickName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [introduction, setIntroduction] = useState('');

  const [text, setText] = useState<any>();

  const handleTextChange = (e: any) => {
    setText(e.target.value);
  };

  //가입 완료 버튼 누르면, 서버에 사용자 정보 보내고, 홈 화면으로 이동
  const navigate = useNavigate(); //history

  const handlePageMove = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  useEffect(() => {});

  return (
    <div className="join page">
      <div className="titleWrap">회원가입</div>

      <div className="contentWrap">
        <Join_imageSet />
        <div className="elementWrap">
          <div className="inputTitle">이름</div>
          <div style={{ marginLeft: '10px' }} className="errorMessageWrap">
            * 반드시 입력해주세요.
          </div>
        </div>
        <div className="inputWrap">
          <input className="input" />
        </div>

        <div className="elementWrap">
          <div className="inputTitle">닉네임</div>
          <div style={{ marginLeft: '10px' }} className="errorMessageWrap">
            * 반드시 입력해주세요.
          </div>
        </div>
        <div className="inputWrap">
          <input className="input" />
        </div>

        <div className="elementWrap">
          <div className="inputTitle">지역</div>
          <div style={{ marginLeft: '10px' }} className="errorMessageWrap">
            * 반드시 입력해주세요.
          </div>
        </div>
        <Join_selectTown></Join_selectTown>

        <div className="elementWrap">
          <div className="inputTitle">핸드폰 번호</div>
          <div style={{ marginLeft: '10px' }} className="errorMessageWrap">
            * 반드시 입력해주세요.
          </div>
        </div>
        <div className="inputWrap">
          <input className="input" />
        </div>

        <div style={{ marginTop: '30px' }} className="inputTitle">
          자신을 소개해 주세요
        </div>
        <div className="inputWrap">
          <input className="input" />
        </div>
      </div>

      <div>
        <button
          className="bottomButton"
          onClick={() => handlePageMove(PATH.HOME)}
        >
          가입 완료
        </button>
      </div>
    </div> //join
  );
};

export default JoinPage;
