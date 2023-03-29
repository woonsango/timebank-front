import { Card } from 'antd';
import { useState } from 'react';
import { IPush } from '../../api/interfaces/IPush';
import PushSearchForm from '../../components/PushSearchForm';
import PushTable from '../../components/PushTable';
import { cssPushManagementTitle } from './PushManagementPage.styles';

const PushManagementPage = () => {
  const [selectedPushIds, setSelectedPushIds] = useState<React.Key[]>();
  const [selectedPushs, setSelectedPushs] = useState<IPush[]>();
  return (
    <Card title="푸시 관리" css={cssPushManagementTitle}>
      <PushSearchForm />
      <PushTable
        selectedPushIds={selectedPushIds}
        setSelectedPushIds={setSelectedPushIds}
        setSelectedPushs={setSelectedPushs}
      />
    </Card>
  );
};
export default PushManagementPage;
