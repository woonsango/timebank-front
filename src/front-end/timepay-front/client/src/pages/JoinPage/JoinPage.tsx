import Join_imageSet from './Join_imageSet';
import './index.css';
import { useState, useEffect } from 'react';
import Join_selectTown from './Join_selectTown';

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

  useEffect(() => {});

  return (
    <div className="join page">
      <div className="titleWrap">회원가입</div>

      <div className="contentWrap">
        <Join_imageSet />
        <div className="inputTitle">이름</div>
        <div className="errorMessageWrap">반드시 입력해주세요.</div>
        <div className="inputWrap">
          <input className="input" />
        </div>

        <div style={{ marginTop: '20px' }} className="inputTitle">
          닉네임
        </div>
        <div className="errorMessageWrap">반드시 입력해주세요.</div>
        <div className="inputWrap">
          <input className="input" />
        </div>
        <div className="">
          <div style={{ marginTop: '20px' }} className="inputTitle">
            동네
          </div>
          <div className="errorMessageWrap">반드시 입력해주세요.</div>
          <Join_selectTown></Join_selectTown>
        </div>

        <div style={{ marginTop: '20px' }} className="inputTitle">
          핸드폰 번호
        </div>
        <div className="errorMessageWrap">반드시 입력해주세요.</div>
        <div className="inputWrap">
          <input className="input" />
        </div>

        <div style={{ marginTop: '20px' }} className="inputTitle">
          소개
        </div>
        <div className="inputWrap">
          <input className="input" />
        </div>
      </div>

      <div>
        <button className="bottomButton">가입 완료</button>
      </div>
    </div> //join
  );
};

export default JoinPage;
