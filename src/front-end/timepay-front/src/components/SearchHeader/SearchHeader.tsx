import 'dayjs/locale/ko';
import locale from 'antd/es/date-picker/locale/ko_KR';

import {
  Button,
  Checkbox,
  Collapse,
  DatePicker,
  Form,
  Input,
  message,
  Radio,
} from 'antd';
import { cssSearchHeaderStyle } from './SearchHeader.styles';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DownOutlined, UpOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
const SearchHeader = () => {
  const navigate = useNavigate();

  const [titleSearchForm] = Form.useForm();
  const [optionSearchForm] = Form.useForm();
  const [messageApi, contextHolder] = message.useMessage();

  const [panelActiveKey, setPanelActiveKey] = useState<string[]>([]);

  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  const handleOnShowSearchOptionCollapse = useCallback(() => {
    if (panelActiveKey && panelActiveKey.length === 0)
      setPanelActiveKey(['search-option-collapse']);
  }, [panelActiveKey]);

  const handleOnHideSearchOptionCollapse = useCallback(() => {
    setPanelActiveKey([]);
  }, []);

  const handleOnChangeOptionSearchForm = useCallback(
    (changedValues: any, values: any) => {
      // 옵션 검색 시 값이 바뀔 때마다 바로 api 호출
      console.log('변경된 내용', changedValues);
      console.log('현재 전체 내용', values);
    },
    [],
  );

  const handleOnSearchTitle = useCallback((values: { title: string }) => {
    console.log('검색 성공', values);
  }, []);

  const handleOnFailSearchTitle = useCallback(
    (errorInfo: any) => {
      console.log('검색 실패', errorInfo);
      messageApi.open({
        type: 'error',
        content: '검색 내용을 다시 확인해주세요',
        duration: 1,
      });
    },
    [messageApi],
  );

  return (
    <div css={cssSearchHeaderStyle}>
      {contextHolder}
      <div className="search-title-container">
        <BackArrow className="back-arrow" onClick={handleClickBack} />
        <Form
          form={titleSearchForm}
          className="title-form"
          onFinish={handleOnSearchTitle}
          onFinishFailed={handleOnFailSearchTitle}
        >
          <Form.Item
            name="title"
            rules={[
              {
                required: true,
                message: '',
              },
            ]}
          >
            <Input placeholder="검색할 게시글의 제목을 입력해주세요" />
          </Form.Item>
          <Button htmlType="submit" type="primary">
            검색
          </Button>
        </Form>
      </div>
      <Collapse
        className="search-option-container"
        expandIconPosition="end"
        bordered={false}
        activeKey={panelActiveKey}
      >
        <Collapse.Panel
          key="search-option-collapse"
          forceRender
          showArrow={false}
          header={
            <div onClick={handleOnShowSearchOptionCollapse}>
              {panelActiveKey && panelActiveKey.length === 0 ? (
                <span>
                  검색 옵션 보이기 <DownOutlined />
                </span>
              ) : (
                <span />
              )}
            </div>
          }
        >
          <Form
            form={optionSearchForm}
            className="option-form"
            layout="horizontal"
            colon={false}
            onValuesChange={handleOnChangeOptionSearchForm}
          >
            <Form.Item name="type" initialValue="도움요청">
              <Radio.Group
                buttonStyle="solid"
                style={{
                  width: '100%',
                  textAlign: 'center',
                  marginBottom: '10px',
                }}
              >
                <Radio.Button value="도움요청">도와줘요</Radio.Button>
                <Radio.Button value="모집">같이해요</Radio.Button>
                <Radio.Button value="기부">기부해요</Radio.Button>
              </Radio.Group>
            </Form.Item>

            {/* RangePicker 로 넣은 경우 모바일 브라우저보다 크게 나와서 시작 일시, 끝 일시를 따로 받음*/}
            <Form.Item className="date-time-form" label="활동시작 시간">
              <Form.Item name="startDate">
                <DatePicker
                  locale={locale}
                  placement="bottomRight"
                  placeholder="날짜"
                />
              </Form.Item>
              <Form.Item name="startTime">
                <DatePicker.TimePicker
                  locale={locale}
                  format="HH:mm"
                  placeholder="시간"
                  minuteStep={30}
                  showNow={false}
                  // 확인 버튼 누르지 않아도 값이 지정되도록 커스텀
                  popupClassName="time-picker-no-footer"
                  onSelect={(value) => {
                    const timeString = dayjs(value).format('HH:mm');
                    optionSearchForm.setFieldValue(
                      'startTime',
                      dayjs(timeString, 'HH:mm'),
                    );
                    // setFieldValue 는 onValuesChange를 호출하지 못해서 직접 호출
                    handleOnChangeOptionSearchForm(
                      { startTime: dayjs(timeString, 'HH:mm') },
                      optionSearchForm.getFieldsValue(),
                    );
                  }}
                />
              </Form.Item>
            </Form.Item>
            <Form.Item className="date-time-form" label="활동 끝 시간">
              <Form.Item name="endDate">
                <DatePicker
                  locale={locale}
                  placement="bottomRight"
                  placeholder="날짜"
                />
              </Form.Item>
              <Form.Item name="endTime">
                <DatePicker.TimePicker
                  locale={locale}
                  format="HH:mm"
                  placeholder="시간"
                  showNow={false}
                  minuteStep={30}
                  // 확인 버튼 누르지 않아도 값이 지정되도록 커스텀
                  popupClassName="time-picker-no-footer"
                  onSelect={(value) => {
                    const timeString = dayjs(value).format('HH:mm');
                    optionSearchForm.setFieldValue(
                      'endTime',
                      dayjs(timeString, 'HH:mm'),
                    );
                    // setFieldValue 는 onValuesChange를 호출하지 못해서 직접 호출
                    handleOnChangeOptionSearchForm(
                      { endTime: dayjs(timeString, 'HH:mm') },
                      optionSearchForm.getFieldsValue(),
                    );
                  }}
                />
              </Form.Item>
            </Form.Item>
            <Form.Item name="isVolunteer" valuePropName="checked">
              <Checkbox>봉사활동 게시글만 보기</Checkbox>
            </Form.Item>
          </Form>
          <div onClick={handleOnHideSearchOptionCollapse}>
            검색 옵션 닫기 <UpOutlined />
          </div>
        </Collapse.Panel>
      </Collapse>
    </div>
  );
};

export default SearchHeader;
