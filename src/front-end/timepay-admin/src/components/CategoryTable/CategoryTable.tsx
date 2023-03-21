import React, { useState } from 'react';
import { Form, Input, InputNumber, Popconfirm, Table, Typography } from 'antd';
import { cssCategoryTable } from './CategoryTable.style';
import { Item } from '../../interfaces/CategoryItem';
import { EditableCell } from '../EditableCell/CategoryEditableCell';

const originData: Item[] = [];
for (let i = 0; i < 100; i++) {
  originData.push({
    key: i + 1,
    name: `카테고리 ${i}`,
    parentCategory: (i === 0 ? '-' : i).toString(),
  });
}

const CategoryTable = () => {
  const [form] = Form.useForm();
  const [data, setData] = useState(originData);
  const [editingKey, setEditingKey] = useState<number>();

  const isEditing = (record: Item) => record.key === editingKey;

  const edit = (record: Partial<Item> & { key: number }) => {
    form.setFieldsValue({
      name: '',
      ...record,
    });
    setEditingKey(record.key);
  };

  const cancel = () => {
    setEditingKey(undefined);
  };

  const save = async (key: number) => {
    try {
      const row = (await form.validateFields()) as Item;

      const newData = [...data];
      const index = newData.findIndex((item) => key === item.key);
      if (index > -1) {
        const item = newData[index];
        newData.splice(index, 1, {
          ...item,
          ...row,
        });
        setData(newData);
        setEditingKey(undefined);
      } else {
        newData.push(row);
        setData(newData);
        setEditingKey(undefined);
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo);
    }
  };

  const handleDelete = (key: number) => {
    const newData = data.filter((item: { key: number }) => item.key !== key);
    setData(newData);
  };

  const columns = [
    {
      title: '카테고리 번호',
      dataIndex: 'key',
      width: '10%',
      editable: false,
    },
    {
      title: '카테고리 이름',
      dataIndex: 'name',
      width: '10%',
      editable: true,
    },
    {
      title: '상위카테고리',
      dataIndex: 'parentCategory',
      width: '10%',
      editable: true,
    },
    {
      title: '수정',
      dataIndex: 'operation',
      width: '10%',
      render: (_: any, record: Item) => {
        const editable = isEditing(record);
        return editable ? (
          <span>
            <Typography.Link
              onClick={() => save(record.key)}
              style={{ marginRight: 8 }}
            >
              Save
            </Typography.Link>
            <Popconfirm title="취소하시겠습니까?" onConfirm={cancel}>
              <a>Cancel</a>
            </Popconfirm>
          </span>
        ) : (
          <Typography.Link
            disabled={editingKey !== undefined}
            onClick={() => edit(record)}
          >
            Edit
          </Typography.Link>
        );
      },
    },
    {
      title: '삭제',
      dataIndex: 'operation',
      width: '10%',
      render: (_: any, record: { key: number }) =>
        originData.length >= 1 ? (
          <Popconfirm
            title="삭제하시겠습니까?"
            onConfirm={() => handleDelete(record.key)}
          >
            <a>Delete</a>
          </Popconfirm>
        ) : null,
    },
  ];

  const mergedColumns = columns.map((col) => {
    if (!col.editable) {
      return col;
    }
    return {
      ...col,
      onCell: (record: Item) => ({
        record,
        inputType: col.dataIndex === 'age' ? 'number' : 'text',
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    };
  });

  return (
    <div css={cssCategoryTable}>
      <Form form={form} component={false}>
        <Table
          components={{
            body: {
              cell: EditableCell,
            },
          }}
          bordered
          dataSource={data}
          // @ts-ignore
          columns={mergedColumns}
          rowClassName="editable-row"
          pagination={{
            onChange: cancel,
          }}
        />
      </Form>
    </div>
  );
};

export default CategoryTable;
