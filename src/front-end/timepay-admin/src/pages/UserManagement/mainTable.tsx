import { Space, Table } from 'antd';
import React from 'react';
import { useState } from 'react';
import type { ColumnsType } from 'antd/es/table';

import { rightAlignCSS } from './UserManagement.styles';

import ProfileImageModal from './profileImageModal';
import DetailModal from './detailModal';
import EditModal from './editModal';

const MainTable = () => {
  /*기본 회원 조회 화면 Table Data 설정 */
  interface DataType {
    key: React.Key;

    nickName: string;
    realName: string;
    town: string;
    birth: string;
    profileImg: any;
    timePay: number;

    blackList: string;
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
      title: '블랙리스트 여부',
      dataIndex: 'blackList',
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
      profileImg: <ProfileImageModal />,

      timePay: i * 100 + i,

      blackList: `X`,
      detail: <DetailModal />,
      edit: <EditModal />,
    });
  }
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

  /*메인에서 체크된 row들 데이터 파싱해서 블랙리스트로 넘기기 */
  return (
    <div>
      <Space css={rightAlignCSS}>
        {hasSelected
          ? `${selectedRowKeys.length}/${data.length}개 선택됨`
          : `${selectedRowKeys.length}/${data.length}개 선택됨`}
      </Space>
      <Space>
        <Table
          css={{ width: 1570 }}
          rowSelection={rowSelection}
          columns={columns}
          dataSource={data}
        />
      </Space>
    </div>
  );
};

export default MainTable;
