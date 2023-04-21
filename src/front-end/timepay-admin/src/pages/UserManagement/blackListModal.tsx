import { Button, Modal, Space, Table } from 'antd';
import { useState } from 'react';
import { rightAlignCSS } from './UserManagement.styles';
import { ColumnsType } from 'antd/es/table';

const BlackListModal = () => {
  const [modalBlackList, setModalBlackList] = useState(false);
  /*블랙리스트 등록 모달 handle 함수*/
  const showModalBlackList = () => {
    setModalBlackList(true);

    //체크된 회원 데이터 파싱 후, 모달에 띄우기
  };

  const handleOkBlackList = () => {
    setModalBlackList(false);
  };

  const handleCancelBlackList = () => {
    setModalBlackList(false);
  };

  /*블랙리스트 등록 모달 Table Data 설정 */
  interface DataTypeBlackList {
    key: React.Key;
    uid: number;
    realName: string;
    timePay: number;
  }

  const columnsBlackList: ColumnsType<DataTypeBlackList> = [
    {
      title: '회원 번호',
      dataIndex: 'uid',
      align: 'center',
    },
    {
      title: '이름',
      dataIndex: 'realName',
      align: 'center',
    },

    {
      title: '타임페이 보유량',
      dataIndex: 'timePay',
      align: 'center',
    },
  ];

  const dataBlackList: DataTypeBlackList[] = [];
  /*
  for (let i = 0; i < 5; i++) {
    dataBlackList.push({
      key: i,
      uid: i * 12 + i,
      realName: `realname ${i}`,

      timePay: i * 100 + i,
    });
  }*/

  return (
    <div>
      <Button type="primary" onClick={showModalBlackList}>
        블랙리스트 등록
      </Button>
      <Modal
        title="블랙리스트 등록"
        open={modalBlackList}
        onOk={handleOkBlackList}
        onCancel={handleCancelBlackList}
        okText="등록"
        cancelText="취소"
      >
        <p>블랙리스트로 등록된 회원은 ...</p>
        <Space css={rightAlignCSS}>총 명</Space>
        <Table
          //style={{ width: 900 }}
          columns={columnsBlackList}
          dataSource={dataBlackList}
        />
      </Modal>
    </div>
  );
};

export default BlackListModal;
