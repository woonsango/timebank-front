import React, { useState } from 'react';
import {
  Form,
  Input,
  InputNumber,
  Popconfirm,
  Switch,
  Table,
  Typography,
} from 'antd';
import { cssCategoryTable } from './CategoryTable.style';
import { ICategory } from '../../api/interfaces/ICategory';
import { CheckOutlined, CloseOutlined } from '@ant-design/icons';
import { useGetCategories } from '../../api/hooks/category';

const originData: ICategory[] = [];
for (let i = 0; i < 100; i++) {
  originData.push({
    categoryId: i + 1,
    boardType: '도움요청',
    categoryName: `카테고리 이름 ${i}`,
    useYn: i % 2 ? 'Y' : 'N',
  });
}

const CategoryTable = () => {
  const [form] = Form.useForm();
  const { data, isLoading } = useGetCategories();

  const dataSource = data?.data.content || [];

  const cancel = () => {};

  const onChangeUseYn = (categoryId: number, useYn: 'Y' | 'N') => {
    console.log(`switch to ${useYn}`);
    console.log(categoryId);
  };

  const handleDelete = (categoryId: number) => {
    console.log(categoryId);
  };

  const columns = [
    {
      title: '카테고리 번호',
      dataIndex: 'categoryId',
      width: '10%',
    },
    {
      title: '카테고리 이름',
      dataIndex: 'categoryName',
      width: '10%',
    },
    {
      title: '카테고리 타입',
      dataIndex: 'boardType',
      width: '10%',
    },
    {
      title: '사용여부',
      dataIndex: 'useYn',
      width: '10%',
      render: (useYn: 'Y' | 'N', record: ICategory) =>
        useYn === 'Y' ? (
          <Switch
            checkedChildren="Y"
            unCheckedChildren="N"
            defaultChecked
            onChange={() => onChangeUseYn(record.categoryId, useYn)}
          />
        ) : (
          <Switch
            checkedChildren="Y"
            unCheckedChildren="N"
            onChange={() => onChangeUseYn(record.categoryId, useYn)}
          />
        ),
    },
    // {
    //   title: '삭제',
    //   dataIndex: 'operation',
    //   width: '10%',
    //   render: (_: any, record: { categoryId: number }) =>
    //     originData.length >= 1 ? (
    //       <Popconfirm
    //         title="삭제하시겠습니까?"
    //         onConfirm={() => handleDelete(record.categoryId)}
    //       >
    //         <a>Delete</a>
    //       </Popconfirm>
    //     ) : null,
    // },
  ];

  return (
    <div css={cssCategoryTable}>
      <Form form={form} component={false}>
        <Table
          bordered
          dataSource={dataSource}
          // @ts-ignore
          columns={columns}
          rowClassName="categoryId"
          // pagination={{
          //   onChange: cancel,
          // }}
        />
      </Form>
    </div>
  );
};

export default CategoryTable;
