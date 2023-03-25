import 'dayjs/locale/ko';
import locale from 'antd/es/date-picker/locale/ko_KR';

import {
  Button,
  Collapse,
  DatePicker,
  Form,
  Input,
  message,
  Select,
  Tag,
} from 'antd';
import { cssSearchHeaderStyle } from './SearchHeader.styles';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { useCallback, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DownOutlined, UpOutlined } from '@ant-design/icons';
import dayjs from 'dayjs';
const SearchHeader = () => {
  const dummyCategoryGiveHelp = useMemo(() => {
    return [
      '도움주기-카테고리1',
      '도움주기-카테고리2',
      '도움주기-카테고리3',
      '도움주기-카테고리4',
    ];
  }, []);

  const dummyCategoryReceiveHelp = useMemo(() => {
    return [
      '도움받기-카테고리1',
      '도움받기-카테고리2',
      '도움받기-카테고리3',
      '도움받기-카테고리4',
      '도움받기-카테고리5',
      '도움받기-카테고리6',
    ];
  }, []);

  const dummyCategoryFree = useMemo(() => {
    return [
      '자유-카테고리1',
      '자유-카테고리2',
      '자유-카테고리3',
      '자유-카테고리4',
      '자유-카테고리5',
      '자유-카테고리6',
    ];
  }, []);

  const dummyCategoryReview = useMemo(() => {
    return [
      '후기-카테고리1',
      '후기-카테고리2',
      '후기-카테고리3',
      '후기-카테고리4',
      '후기-카테고리5',
      '후기-카테고리6',
    ];
  }, []);

  const dummyCategory: { [key: string]: string[] } = useMemo(() => {
    return {
      도움주기: dummyCategoryGiveHelp,
      도움받기: dummyCategoryReceiveHelp,
      자유: dummyCategoryFree,
      후기: dummyCategoryReview,
    };
  }, [
    dummyCategoryGiveHelp,
    dummyCategoryReceiveHelp,
    dummyCategoryFree,
    dummyCategoryReview,
  ]);

  const navigate = useNavigate();
  const [titleSearchForm] = Form.useForm();
  const [optionSearchForm] = Form.useForm();
  const [messageApi, contextHolder] = message.useMessage();

  const [panelActiveKey, setPanelActiveKey] = useState<string[]>([]);
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

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

  const getCategoryTagItems: (type: string) => string[] = useCallback(
    (type: string) => {
      if (type === '전체') {
        return ['전체'];
      } else {
        return ['전체', ...dummyCategory[type]];
      }
    },
    [dummyCategory],
  );

  const handleOnCategoryChange = useCallback(
    (clickedCategory: string, checked: boolean) => {
      if (clickedCategory === '전체') {
        const type = optionSearchForm.getFieldValue('type');
        if (type === clickedCategory) {
          // 유형이 전체인 경우 카테고리 전체 선택 해제 불가(전체 카테고리만 있음)
          return setSelectedCategories([clickedCategory]);
        } else {
          // 특정 유형인 경우 전체 선택 시 모든 카테고리 선택, 전체 선택 해제 시 모든 카테고리 해제(카테고리 여러 개)
          if (checked) {
            return setSelectedCategories([
              clickedCategory,
              ...dummyCategory[type],
            ]);
          } else {
            return setSelectedCategories([]);
          }
        }
      } else {
        let nextSelectedCategories: string[] = selectedCategories;
        if (checked) {
          nextSelectedCategories = [...selectedCategories, clickedCategory];
        } else {
          // 전체 카테고리 조건에 맞지 않게 되어서 전체 카테고리 항상 함께 비활성화
          nextSelectedCategories = nextSelectedCategories.filter(
            (category) => category !== clickedCategory && category !== '전체',
          );
        }
        console.log(nextSelectedCategories);
        setSelectedCategories(nextSelectedCategories);
      }
    },
    [optionSearchForm, dummyCategory, selectedCategories],
  );

  const handleOnChangeOptionSearchForm = useCallback(
    (changedValues: any, values: any) => {
      // 옵션 검색 시 값이 바뀔 때마다 바로 api 호출
      if (changedValues.type) {
        // 유형이 바뀔 경우 모든 카테고리 전체 선택 자동으로 해줌
        if (changedValues.type === '전체') setSelectedCategories(['전체']);
        else
          setSelectedCategories(['전체', ...dummyCategory[changedValues.type]]);
      }
      console.log('변경된 내용', changedValues);
      console.log('현재 전체 내용', values);
    },
    [dummyCategory],
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
            <Form.Item name="type" label="유형" initialValue="전체">
              <Select>
                <Select.Option value="전체">전체</Select.Option>
                <Select.Option value="도움주기">도움주기</Select.Option>
                <Select.Option value="도움받기">도움받기</Select.Option>
                <Select.Option value="자유">자유</Select.Option>
                <Select.Option value="후기">후기</Select.Option>
              </Select>
            </Form.Item>
            <Form.Item
              noStyle
              shouldUpdate={(prevValues, current) =>
                prevValues.type !== current.type
              }
            >
              {({ getFieldValue }) => (
                <Form.Item
                  name="category"
                  label="카테고리"
                  help={
                    getFieldValue('type') === '전체'
                      ? '상세 카테고리 지정은 유형을 지정해야만 가능합니다.'
                      : null
                  }
                >
                  <div className="available-category-tag-container">
                    {getCategoryTagItems(getFieldValue('type')).map((tag) => (
                      <Tag.CheckableTag
                        checked={
                          getFieldValue('type') === '전체'
                            ? true
                            : selectedCategories.includes(tag)
                        }
                        key={tag}
                        onChange={(checked) =>
                          handleOnCategoryChange(tag, checked)
                        }
                      >
                        {tag}
                      </Tag.CheckableTag>
                    ))}
                  </div>
                </Form.Item>
              )}
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
                  }}
                />
              </Form.Item>
            </Form.Item>
            <Form.Item name="sort" label="정렬" initialValue="new">
              <Select>
                <Select.Option value="new">최신순</Select.Option>
                <Select.Option value="old">과거순</Select.Option>
              </Select>
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
