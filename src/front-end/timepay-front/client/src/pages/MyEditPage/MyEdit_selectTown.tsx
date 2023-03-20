import React, { useState } from 'react';
import hangjungdong from '../../utils/hangjungdong';
import './MyEdit.css';

const MyEdit_selectTown = () => {
  const [selectedSido, setSelectedSido] = useState<String>();
  const [selectedSigungu, setSelectedSigungu] = useState<String>();
  const [selectedEupmyeondong, setSelectedEupmyeondong] = useState<String>();

  const { sido, sigungu, eupmyeondong } = hangjungdong;

  const selectChangeSido = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedSido(event.target.value);
    setSelectedSigungu('');
    setSelectedEupmyeondong('');
    console.log(event.target.value);
  };

  const selectChangeSigungu = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedSigungu(event.target.value);
    setSelectedEupmyeondong('');
    console.log(event.target.value);
  };

  const selectChangeEupmyeondong = (
    event: React.ChangeEvent<HTMLSelectElement>,
  ) => {
    setSelectedEupmyeondong(event.target.value);
    console.log(event.target.value);
  };

  console.log('적용된 시|도 Total:', sido.length);
  console.log('서울시 자치구 Total:', sigungu.length);
  console.log('서울시 행정동 Total:', eupmyeondong.length);

  //select box - 서울시 기준 구, 동 적용함, 서울시 25개 자치구, 426개 행정동 존재
  return (
    <div className="EditselectWrap">
      <select onChange={selectChangeSido} className="EditselectBox">
        <option>유저 시/도</option>
        {sido.map((el: any) => (
          <option key={el.sidoName} value={el.sidoName}>
            {el.sidoName}
          </option>
        ))}
      </select>
      <select
        style={{ marginLeft: '32px' }}
        onChange={selectChangeSigungu}
        className="EditselectBox"
      >
        <option>유저 시/군/구</option>
        {sigungu
          .filter((el: any) => el.sidoName === selectedSido)
          .map((el: any) => (
            <option key={el.sigunguName} value={el.sigunguName}>
              {el.sigunguName}
            </option>
          ))}
      </select>
      <select
        style={{ marginLeft: '32px' }}
        onChange={selectChangeEupmyeondong}
        className="EditselectBox"
      >
        <option>유저 읍/면/동</option>
        {eupmyeondong
          .filter(
            (el: any) =>
              el.sidoName === selectedSido &&
              el.sigunguName === selectedSigungu,
          )
          .map((el: any) => (
            <option key={el.eupmyeondongName} value={el.eupmyeondongName}>
              {el.eupmyeondongName}
            </option>
          ))}
      </select>
    </div>
  );
};

export default MyEdit_selectTown;
