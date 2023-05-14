import { useRecoilValue, useSetRecoilState } from 'recoil';
import { IGetUserInfoRequest, IUsers } from '../../api/interfaces/IUser';
import { useGetUserInfos } from '../../api/hooks/userManagement';
import { mainSearchState } from './mainSearchState';
import { useMemo, useState } from 'react';
import Table, { ColumnsType } from 'antd/es/table';
import { customPaginationProps } from '../../utils/pagination';

interface MainTableProps {
  selectedUserInfoIds?: React.Key[];
  setSelectedUserInfoIds: (args?: React.Key[]) => void;
  setSelectedUserInfos: (args?: IUsers[]) => void;
}

const MainTable = ({
  selectedUserInfoIds,
  setSelectedUserInfoIds,
  setSelectedUserInfos,
}: MainTableProps) => {
  const mainSearchValues = useRecoilValue(mainSearchState);
  const setMainSearch = useSetRecoilState(mainSearchState);

  const { data, isLoading } = useGetUserInfos(mainSearchValues);

  //   const [isOpen, setIsOpen] = useState(false);
  //   const [currentPush, setCurrentPush] = useState<INotification>();

  //   const handleOnShowDetailPush = useCallback((push: INotification) => {
  //     setCurrentPush(push);
  //     setIsOpen(true);
  //   }, []);

  //   const handleOnCloseDetailPush = useCallback(() => {
  //     setCurrentPush(undefined);
  //     setIsOpen(false);
  //   }, []);

  const dataSource = useMemo(() => {
    if (mainSearchValues) {
      return data?.data.content || [];
    }
    return [];
  }, [mainSearchValues, data]);

  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: IUsers[]) => {
      setSelectedUserInfoIds(selectedRowKeys);
      setSelectedUserInfos(selectedRows);
    },
  };

  const columns: ColumnsType<IUsers> = [
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
    // {
    //   title: '블랙리스트 여부',
    //   dataIndex: 'blackList',
    //   align: 'center',
    // },

    {
      title: '정보 수정',
      dataIndex: 'edit',
      align: 'center',
    },
  ];

  return (
    <>
      <div>
        {selectedUserInfoIds && selectedUserInfoIds.length > 0
          ? `${selectedUserInfoIds.length} 개 선택 / `
          : ''}
        총 {data?.data.totalElements || 0} 개
      </div>
      <Table
        //css={cssPushTableStyle}
        rowSelection={rowSelection}
        columns={columns}
        scroll={{ x: 1200 }}
        dataSource={dataSource}
        rowKey="UserId"
        loading={isLoading}
        pagination={customPaginationProps<IGetUserInfoRequest>({
          totalElements: data?.data.totalElements,
          currentSearchValues: mainSearchValues,
          setSearchValues: setMainSearch,
        })}
      />
    </>
  );
};

export default MainTable;
