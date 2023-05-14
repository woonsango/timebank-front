import { Card, Space, Table } from 'antd';

import { topWrapperCSS, rightAlignCSS } from './UserManagement.styles';
import SearchForm from './searchForm';
import BlackListModal from './blackListModal';
import DelUserModal from './delUserModal';

import React from 'react';
import { useState } from 'react';
import type { ColumnsType } from 'antd/es/table';

import ProfileImageModal from './profileImageModal';
import DetailModal from './detailModal';
import EditModal from './editModal';
import { IUser } from '../../api/interfaces/IUser';
import MainTable from './mainTable';

const UserManagementPage = () => {
  // /*Table */
  // const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

  // const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
  //   console.log('selectedRowKeys changed: ', newSelectedRowKeys);
  //   setSelectedRowKeys(newSelectedRowKeys);
  // };

  // const rowSelection = {
  //   selectedRowKeys,
  //   onChange: onSelectChange,
  // };
  // const hasSelected = selectedRowKeys.length > 0;

  const [selectedUserInfoIds, setSelectedUserInfoIds] = useState<React.Key[]>();
  const [selectedUserInfos, setSelectedUserInfos] = useState<IUser[]>();

  return (
    <Card title="회원 관리" css={topWrapperCSS}>
      <SearchForm />
      {/* <Space css={rightAlignCSS}>
        <BlackListModal />
        <DelUserModal />
      </Space> */}

      {/* <Space css={rightAlignCSS}>
        {hasSelected
          ? `${selectedRowKeys.length}/${data.length}개 선택됨`
          : `${selectedRowKeys.length}/${data.length}개 선택됨`}
      </Space>

      <Table rowSelection={rowSelection} columns={columns} dataSource={data} /> */}
      <MainTable
        selectedUserInfoIds={selectedUserInfoIds}
        setSelectedUserInfoIds={setSelectedUserInfoIds}
        setSelectedUserInfos={setSelectedUserInfos}
      />
    </Card>
  );
};

export default UserManagementPage;
