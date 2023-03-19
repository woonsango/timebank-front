import { Button, Card } from 'antd';
import PostSearchForm from '../../components/PostSearchForm';
import PostTable from '../../components/PostTable';
import { cssPostManagementPageStyle } from './PostManagementPage.styles';

const PostManagementPage = () => {
  return (
    <Card title="게시글 관리" css={cssPostManagementPageStyle}>
      <PostSearchForm />
      <div className="control-box">
        <Button type="primary">숨김</Button>
        <Button className="change-status-btn" type="primary">
          상태변경
        </Button>
      </div>
      <PostTable />
    </Card>
  );
};

export default PostManagementPage;
