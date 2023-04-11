import { Card } from 'antd';
import QnASearchForm from '../../components/QnASearchForm';
import QnATable from '../../components/QnATable';
import { cssQnAManagementPageStyle } from './QnAManagementPage.styles';

const QnAManagementPage = () => {
  return (
    <Card title="문의 관리" css={cssQnAManagementPageStyle}>
      <QnASearchForm />
      <QnATable />
    </Card>
  );
};

export default QnAManagementPage;
