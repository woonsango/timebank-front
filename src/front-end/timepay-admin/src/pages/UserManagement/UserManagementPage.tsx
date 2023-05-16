import { Card, Space } from 'antd';

import { topWrapperCSS, rightAlignCSS } from './UserManagement.styles';
import SearchForm from './searchForm';
import MainTable from './mainTable';

const UserManagementPage = () => {
  return (
    <div>
      <Space direction="vertical" css={topWrapperCSS}>
        <Card title="회원 관리" css={{ width: 1630, height: 970 }}>
          <SearchForm></SearchForm>
          <Space css={rightAlignCSS}></Space>
          <MainTable />
        </Card>
      </Space>
    </div>
  );
};

export default UserManagementPage;
