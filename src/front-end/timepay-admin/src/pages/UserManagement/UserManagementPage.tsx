import {
  Card,
  Space,
  Select,
  Input,
  Button,
  Table,
  Typography,
  Modal,
  Image,
} from 'antd';
import React from 'react';
import { useState } from 'react';
import { css } from '@emotion/react';
import type { ColumnsType } from 'antd/es/table';

/*수직 수평 중앙 정렬*/
const topWrapperCSS = css`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 110vh;
  overflow: scroll;
`;

/* 수평 오른쪽 정렬 */
const rightAlignCSS = css`
  display: flex;
  justify-content: right;
  margin-top: 15px;
`;

const UserManagementPage = () => {
  /*프로필 사진 모달 설정 */
  const [modalProfileImage, setModalProfileImage] = useState(false);

  const showModalProfileImage = () => {
    setModalProfileImage(true);
  };

  const handleOkProfileImage = () => {
    setModalProfileImage(false);
  };

  const handleCancelProfileImage = () => {
    setModalProfileImage(false);
  };

  /*회원 정보 수정 모달 설정 */
  const [modalEdit, setModalEdit] = useState(false);

  const showModalEdit = () => {
    setModalEdit(true);
  };

  const handleOkEdit = () => {
    setModalEdit(false);
  };

  const handleCancelEdit = () => {
    setModalEdit(false);
  };

  /*회원 활동 목록 모달 설정 */
  const [modalDetail, setModalDetail] = useState(false);

  const showModalDetail = () => {
    setModalDetail(true);
  };

  const handleOkDetail = () => {
    setModalDetail(false);
  };

  const handleCancelDetail = () => {
    setModalDetail(false);
  };

  const tabList = [
    {
      key: '게시글',
      tab: '게시글',
    },
    {
      key: '댓글',
      tab: '댓글',
    },
    {
      key: '신고받은내역',
      tab: '신고받은 내역',
    },
    {
      key: '신고한내역',
      tab: '신고한 내역',
    },
  ];

  const contentList: Record<string, React.ReactNode> = {
    게시글: <p>content1</p>,
    댓글: <p>content2</p>,
    신고받은내역: <p>content2</p>,
    신고한내역: <p>content2</p>,
  };

  const [activeTabKey1, setActiveTabKey1] = useState<string>('tab1');

  const onTab1Change = (key: string) => {
    setActiveTabKey1(key);
  };

  /*기본 회원 조회 화면 Table Data 설정 */
  interface DataType {
    key: React.Key;

    nickName: string;
    realName: string;
    town: string;
    birth: string;
    profileImg: any;
    timePay: number;

    detail: any;
    edit: any;
  }

  const columns: ColumnsType<DataType> = [
    {
      title: '이름',
      dataIndex: 'nickName',
      align: 'center',
    },
    {
      title: '닉네임',
      dataIndex: 'realName',
      align: 'center',
    },
    {
      title: '지역',
      dataIndex: 'town',
      align: 'center',
    },
    {
      title: '생년월일',
      dataIndex: 'birth',
      align: 'center',
    },
    {
      title: '프로필 사진',
      dataIndex: 'profileImg',
      align: 'center',
    },
    {
      title: '타임페이 보유량',
      dataIndex: 'timePay',
      align: 'center',
    },
    {
      title: '활동 목록',
      dataIndex: 'detail',
      align: 'center',
    },

    {
      title: '정보 수정',
      dataIndex: 'edit',
      align: 'center',
    },
  ];

  const data: DataType[] = [];
  for (let i = 0; i < 46; i++) {
    data.push({
      key: i,

      nickName: `nickname ${i}`,
      realName: `realname ${i}`,
      town: `서울특별시 ${i}구 ${i}동`,
      birth: `0000-00-00`,
      profileImg: <Button onClick={showModalProfileImage}>사진 보기</Button>,
      timePay: i * 100 + i,

      detail: <Button onClick={showModalDetail}>활동 보기</Button>,
      edit: <Button onClick={showModalEdit}>수정</Button>,
    });
  }

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
  for (let i = 0; i < 5; i++) {
    dataBlackList.push({
      key: i,
      uid: i * 12 + i,
      realName: `realname ${i}`,

      timePay: i * 100 + i,
    });
  }

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
  for (let i = 0; i < 3; i++) {
    dataDelUser.push({
      key: i,
      uid: i * 12 + i,
      realName: `realname ${i}`,

      timePay: i * 100 + i,
    });
  }

  const { Text } = Typography;
  const [filter, setFilter] = useState<string>();
  const [modalBlackList, setModalBlackList] = useState(false);
  const [modalDelUser, setModalDelUser] = useState(false);

  const onChangeFilter = (value: string) => {
    setFilter(value);
  };

  /*블랙리스트 등록 모달 handle 함수*/
  const showModalBlackList = () => {
    setModalBlackList(true);
  };

  const handleOkBlackList = () => {
    setModalBlackList(false);
  };

  const handleCancelBlackList = () => {
    setModalBlackList(false);
  };

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

  /*Table */
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

  const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
    console.log('selectedRowKeys changed: ', newSelectedRowKeys);
    setSelectedRowKeys(newSelectedRowKeys);
  };

  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };
  const hasSelected = selectedRowKeys.length > 0;

  return (
    <div>
      <Space direction="vertical" css={topWrapperCSS}>
        <Card title="회원 관리" style={{ width: 1630, height: 970 }}>
          <Card style={{ textAlign: 'center' }}>
            <Select
              defaultValue="필터"
              style={{ width: 140 }}
              onChange={onChangeFilter}
              options={[
                { value: '이름', label: '이름' },
                { value: '닉네임', label: '닉네임' },
              ]}
            />
            <Input style={{ width: 170, marginLeft: 20 }} />
            <Text strong style={{ marginLeft: 90 }}>
              회원 번호
            </Text>
            <Input style={{ width: 170, marginLeft: 20 }} />
            <Button
              css={css`
                background: gray;
              `}
              style={{ width: 80, marginLeft: 70 }}
              type="primary"
            >
              검색
            </Button>
          </Card>
          <Space css={rightAlignCSS}>
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
              <Table
                //style={{ width: 900 }}
                columns={columnsDelUser}
                dataSource={dataDelUser}
              />
            </Modal>
          </Space>
          <Space css={rightAlignCSS}>
            {hasSelected
              ? `${selectedRowKeys.length}/${data.length}개 선택됨`
              : `${selectedRowKeys.length}/${data.length}개 선택됨`}
          </Space>
          <Space>
            <Table
              style={{ width: 1570 }}
              rowSelection={rowSelection}
              columns={columns}
              dataSource={data}
            />
          </Space>
        </Card>
      </Space>
      <Modal
        title="프로필 사진"
        open={modalProfileImage}
        onOk={handleOkProfileImage}
        onCancel={handleCancelProfileImage}
        okText="확인"
        cancelText="취소"
      >
        <Image
          width={200}
          src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
        />
      </Modal>

      <Modal
        title="활동 목록"
        open={modalDetail}
        onOk={handleOkDetail}
        onCancel={handleCancelDetail}
        okText="확인"
        cancelText="취소"
      >
        <Card
          style={{ width: '100%' }}
          tabList={tabList}
          activeTabKey={activeTabKey1}
          onTabChange={onTab1Change}
        >
          {contentList[activeTabKey1]}
        </Card>
      </Modal>

      <Modal
        title="회원 정보 수정"
        open={modalEdit}
        onOk={handleOkEdit}
        onCancel={handleCancelEdit}
        okText="확인"
        cancelText="취소"
      ></Modal>
    </div>
  );
};

export default UserManagementPage;
