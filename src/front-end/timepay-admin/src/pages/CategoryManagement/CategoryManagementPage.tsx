import CategoryTable from '../../components/CategoryTable';
import CategoryAdd from '../../components/CategoryAdd/CategoryAdd';
import { cssCategoryManagementTitle } from './CategoryManagementPage.style';
import { Card } from 'antd';

const CategoryManagementPage = () => {
  return (
    <Card title="게시글 관리" css={cssCategoryManagementTitle}>
      <CategoryAdd />
      <CategoryTable />
    </Card>
  );
};

export default CategoryManagementPage;
