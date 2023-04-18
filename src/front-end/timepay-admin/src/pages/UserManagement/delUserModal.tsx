import { Button, Modal, Space, Table } from 'antd';
import { useState } from 'react';
import { rightAlignCSS } from './UserManagement.styles';
import { ColumnsType } from 'antd/es/table';

const DelUserModal = () => {
  const [modalDelUser, setModalDelUser] = useState(false);

  /*회원 삭제 모달 handle 함수*/
  const showModalDelUser = () => {
    setModalDelUser(true);
  };

  const handleOkDelUser = () => {
    setModalDelUser(false);
  };

  const handleCancelDelUser = () => {
    setModalDelUser(false);
  };

  /*회원 삭제 모달 Table Data 설정 */
  interface DataTypeDelUser {
    key: React.Key;
    uid: number;
    realName: string;
    timePay: number;
  }

  const columnsDelUser: ColumnsType<DataTypeDelUser> = [
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

  const dataDelUser: DataTypeDelUser[] = [];

  return (
    <div>
      <Button type="primary" onClick={showModalDelUser}>
        삭제
      </Button>
      <Modal
        title="회원 삭제"
        open={modalDelUser}
        onOk={handleOkDelUser}
        onCancel={handleCancelDelUser}
        okText="삭제"
        cancelText="취소"
      >
        <p>회원 삭제 시...</p>
        <Space css={rightAlignCSS}>총 명</Space>
        <Table columns={columnsDelUser} dataSource={dataDelUser} />
      </Modal>
    </div>
  );
};

export default DelUserModal;
