import {
  Form,
  Input,
  Select,
  DatePicker,
  Row,
  Button,
  InputNumber,
} from 'antd';
import { RangePickerProps } from 'antd/es/date-picker';
import { useCallback, useMemo, useState } from 'react';
import { cssReportSearchFormStyle } from './ReportSearchForm.styles';

const ReportSearchForm = () => {
  const [form] = Form.useForm();
  const { Option } = Select;
  const [inputValue, setInputValue] = useState();

  const { RangePicker } = DatePicker;

  const onOk = (value: RangePickerProps['value']) => {
    console.log('onOk1: ', value);
    console.log('onOk2: ', value?.[0]?.format('YYYY-MM-DD HH:mm:ss'));
    console.log('onOk3: ', value?.[1]?.format('YYYY-MM-DD HH:mm:ss'));
  };

  const searchInputValue = useMemo(() => {
    if (inputValue)
      switch (inputValue) {
        case 'reportId':
          return (
            <Form.Item
              name="reportId"
              rules={[
                { required: true, message: '신고글 번호를 입력해주세요' },
              ]}
            >
              <InputNumber
                style={{ width: 250 }}
                placeholder="신고글 번호를 입력해주세요"
              />
            </Form.Item>
          );

        case 'name':
          return (
            <Form.Item
              name="name"
              rules={[
                { required: true, message: '신고자 이름을 입력해주세요' },
              ]}
            >
              <Input placeholder="신고자 이름을 입력해주세요" />
            </Form.Item>
          );

        case 'content':
          return (
            <Form.Item
              name="content"
              rules={[{ required: true, message: '신고 내용을 입력해주세요' }]}
            >
              <Input placeholder="신고 내용을 입력해주세요" />
            </Form.Item>
          );

        case 'time':
          return (
            <Form.Item name="startTime" rules={[{ required: true }]}>
              <RangePicker
                showTime={{ format: 'HH:mm' }}
                format="YYYY-MM-DD HH:mm"
                onOk={onOk}
              />
            </Form.Item>
          );
      }
  }, [RangePicker, inputValue]);

  const onValuesChange = useCallback((changedFields: any) => {
    console.log(changedFields);
    setInputValue(changedFields);
  }, []);

  const onFinish = useCallback((values: any) => {
    //검색시 사용
    console.log(values);
  }, []);
  return (
    <div css={cssReportSearchFormStyle}>
      <Form form={form} layout="horizontal" onFinish={onFinish}>
        <Row>
          <Form.Item name="searchLabel" rules={[{ required: true }]}>
            <Select
              placeholder="검색값을 골라주세요."
              allowClear
              onChange={onValuesChange}
            >
              <Option value="reportId">신고글 번호</Option>
              <Option value="name">신고자 이름</Option>
              <Option value="content">신고내용</Option>
              <Option value="time">작성시간</Option>
            </Select>
          </Form.Item>
          <Form.Item noStyle>{searchInputValue}</Form.Item>

          <Button
            htmlType="submit"
            style={{ marginLeft: 'auto' }}
            type="primary"
          >
            검색
          </Button>
        </Row>
      </Form>
    </div>
  );
};

export default ReportSearchForm;
