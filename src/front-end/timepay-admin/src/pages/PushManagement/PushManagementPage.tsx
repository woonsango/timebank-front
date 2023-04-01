import { Button, Card } from 'antd';
import { useCallback, useState } from 'react';
import { IPush } from '../../api/interfaces/IPush';
import PushAddModal from '../../components/PushAddModal';
import PushDeleteModal from '../../components/PushDeleteModal';
import PushSearchForm from '../../components/PushSearchForm';
import PushTable from '../../components/PushTable';
import { cssPushManagementTitle } from './PushManagementPage.styles';

const PushManagementPage = () => {
  const [isOpenAddModal, setIsOpenAddModal] = useState(false);
  const [isOpenDeletePushes, setIsOpenDeletePushes] = useState(false);

  const handleOnCancelModal = useCallback(() => {
    setIsOpenAddModal(false);
    setIsOpenDeletePushes(false);
  }, []);

  const [selectedPushIds, setSelectedPushIds] = useState<React.Key[]>();
  const [selectedPushes, setSelectedPushes] = useState<IPush[]>();
  return (
    <Card title="PUSH(공지) 관리" css={cssPushManagementTitle}>
      <PushSearchForm />
      <div className="control-box">
        <Button
          className="createBtn"
          type="primary"
          onClick={() => {
            setIsOpenAddModal(true);
          }}
        >
          공지 작성
        </Button>
        <Button
          className="deleteBtn"
          type="primary"
          onClick={() => {
            setIsOpenDeletePushes(true);
          }}
        >
          삭제
        </Button>
      </div>
      <PushTable
        selectedPushIds={selectedPushIds}
        setSelectedPushIds={setSelectedPushIds}
        setSelectedPushes={setSelectedPushes}
      />
      <PushAddModal isOpen={isOpenAddModal} onCancel={handleOnCancelModal} />
      <PushDeleteModal
        pushes={selectedPushes}
        isOpen={isOpenDeletePushes}
        onCancel={handleOnCancelModal}
      />
    </Card>
  );
};
export default PushManagementPage;
