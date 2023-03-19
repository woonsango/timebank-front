import React, { useMemo, useState } from 'react';
import { Form } from 'antd';
import AdminMainTable from '../../components/AdminMainTable/AdminMainTable';
import AdminAdd from '../../components/AdminMainTable/AdminAdd';
import { cssTitle } from './AdminManagementPage.style';

const AdminManagementPage = () => {
  const [form] = Form.useForm();

  return (
    <div>
      <h1 css={cssTitle}>관리자 관리</h1>
      <AdminAdd />
      <AdminMainTable />
    </div>
  );
};

export default AdminManagementPage;
