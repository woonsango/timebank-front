import { Button, DatePicker, Form, Input, Modal, Steps } from 'antd';
import { useNavigate, useParams } from 'react-router-dom';
import {
  cssInstantActivityPageStyle,
  cssInstantActivityStepItemStyle,
} from './InstantActivityPage.styles';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import locale from 'antd/es/date-picker/locale/ko_KR';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { headerTitleState } from '../../states/uiState';
import { useGetUserInfo } from '../../api/hooks/user';
import { PATH } from '../../utils/paths';

const InstantActivityPage = () => {
  const navigate = useNavigate();
  const setHeaderTitle = useSetRecoilState(headerTitleState);
  const { data } = useGetUserInfo();
  const { helpPk } = useParams();

  const [timeForm] = Form.useForm();
  const [contentForm] = Form.useForm();
  const [current, setCurrent] = useState(0);
  const [exchangeTimepay, setExchangeTimepay] = useState(60);

  const next = useCallback(() => {
    setCurrent(current + 1);
  }, [current]);

  const prev = useCallback(() => {
    setCurrent(current - 1);
  }, [current]);

  const handleOnChangeStep = useCallback((value: number) => {
    setCurrent(value);
  }, []);

  useEffect(() => {
    setHeaderTitle('바로도움 요청 일지 작성');
  }, [setHeaderTitle]);

  useEffect(() => {
    timeForm.setFieldValue('startTime', dayjs().add(-1, 'hours'));
    timeForm.setFieldValue('endTime', dayjs());
  }, [timeForm]);

  const handleOnChangeTime = useCallback(
    (changedValues: any, values: any) => {
      if (values.startTime && values.endTime) {
        if (values.endTime.diff(values.startTime, 'minutes') <= 0) {
          Modal.error({ content: '끝 시간은 시작 시간보다 커야합니다.' });
          timeForm.setFieldValue(
            'endTime',
            dayjs(values.startTime).add(1, 'hours'),
          );
          setExchangeTimepay(60);
        } else
          setExchangeTimepay(values.endTime.diff(values.startTime, 'minutes'));
      } else setExchangeTimepay(0);
    },
    [timeForm],
  );

  const handleOnSubmit = useCallback(() => {
    const timeFormValues = timeForm.getFieldsValue();
    const contentFormValues = contentForm.getFieldsValue();

    const newActivity = {
      title: timeFormValues.activityDate.format(
        'YYYY년 HH월 mm일 바로 도움 활동',
      ),
      category: '바로도움',
      content: contentFormValues.content,
      startTime: `${timeFormValues.activityDate.format(
        'YYYY-HH-ddT',
      )}${timeFormValues.startTime.format('HH:mm:ss.000Z')}`,
      endTime: `${timeFormValues.activityDate.format(
        'YYYY-HH-ddT',
      )}${timeFormValues.endTime.format('HH:mm:ss.000Z')}`,
      location: '장소',
      auto: false,
      pay: exchangeTimepay,
      id: parseInt(helpPk || ''),
    };
    console.log(newActivity);
    // api 성공 후 활동기록 화면으로 이동 예정
  }, [timeForm, exchangeTimepay, contentForm, helpPk]);

  const steps = useMemo(
    () => [
      { key: '확인', title: '확인' },
      {
        key: '활동일시 선택',
        title: '활동일시 선택',
      },
      {
        key: '활동내용 요약',

        title: '활동내용 요약',
      },
    ],
    [],
  );

  return (
    <div css={cssInstantActivityPageStyle}>
      <Steps
        direction="horizontal"
        current={current}
        items={steps}
        onChange={handleOnChangeStep}
      />
      <div css={cssInstantActivityStepItemStyle(current === 0)}>
        <div>
          <p className="helper-user-nickname">
            안녕하세요 {data?.data.body.nick_name || '-'} 님!
          </p>
          <p className="help-user">
            <span>
              도움을 <b>받은</b> 분의 id는 <b>{helpPk}</b> 번이 맞나요?
            </span>
            <br />
            <span className="guide">
              id는 마이페이지 {'->'} 닉네임 아래 # 뒤에 적혀있습니다.
            </span>
          </p>
        </div>
        <div className="control-box">
          <Button onClick={() => navigate(PATH.HOME)}>취소</Button>
          <Button type="primary" onClick={next}>
            맞습니다
          </Button>
        </div>
      </div>
      <div css={cssInstantActivityStepItemStyle(current === 1)}>
        <Form
          form={timeForm}
          layout="horizontal"
          onValuesChange={handleOnChangeTime}
        >
          <div className="form-info">활동을 언제 했는지 선택해주세요.</div>
          <Form.Item name="activityDate" label="활동일" initialValue={dayjs()}>
            <DatePicker format="YYYY년 MM월 DD일" disabled />
          </Form.Item>
          <Form.Item
            name="startTime"
            label="활동을 시작한 시간"
            initialValue={dayjs().add(-1, 'hours')}
            rules={[{ required: true, message: '필수로 작성해주세요.' }]}
          >
            <DatePicker.TimePicker
              locale={locale}
              format="HH시 mm분"
              placeholder="시간"
              showNow={false}
              // 확인 버튼 누르지 않아도 값이 지정되도록 커스텀
              popupClassName="time-picker-no-footer"
              onSelect={(value) => {
                timeForm.setFieldValue('startTime', value);
                handleOnChangeTime(
                  { startTime: value },
                  timeForm.getFieldsValue(),
                );
              }}
            />
          </Form.Item>
          <Form.Item
            name="endTime"
            label="활동이 끝난 시간"
            initialValue={dayjs()}
            rules={[{ required: true, message: '필수로 작성해주세요.' }]}
          >
            <DatePicker.TimePicker
              locale={locale}
              format="HH시 mm분"
              placeholder="시간"
              showNow={false}
              // 확인 버튼 누르지 않아도 값이 지정되도록 커스텀
              popupClassName="time-picker-no-footer"
              onSelect={(value) => {
                timeForm.setFieldValue('endTime', value);
                handleOnChangeTime(
                  { endTime: value },
                  timeForm.getFieldsValue(),
                );
              }}
            />
          </Form.Item>
          <div className="guide">
            <div>
              교환할 타임페이 양 :{' '}
              <b>{exchangeTimepay ? exchangeTimepay + ' TP' : ''}</b>{' '}
            </div>
            <div>도움을 받은 분의 타임페이가 충분한지 확인해주세요.</div>
          </div>
        </Form>
        <div className="control-box">
          <Button onClick={prev}>이전으로</Button>
          <Button type="primary" onClick={next}>
            선택 완료
          </Button>
        </div>
      </div>
      <div css={cssInstantActivityStepItemStyle(current === 2)}>
        <Form form={contentForm}>
          <div className="form-info">
            어떤 활동을 했는지 간략하게 적어주세요.
          </div>

          <Form.Item
            name="content"
            label="활동 내용"
            rules={[{ required: true, message: '필수로 작성해주세요.' }]}
          >
            <Input.TextArea rows={5} maxLength={100} showCount />
          </Form.Item>
          <div className="guide">
            활동 내용은 100자 내로 작성해주세요 <br />
            활동 내용에는 다음과 같은 내용들을 넣으면 좋아요. <br />
            <ul>
              <li>장소</li>
              <li>어떤 도움에 대한 내용인지</li>
              <li>도움 활동 중 특이사항이 있었는지</li>
              <li>도움 활동 후 소감</li>
            </ul>
          </div>
        </Form>
        <div className="control-box">
          <Button onClick={prev}>이전으로</Button>
          <Button type="primary" onClick={handleOnSubmit}>
            작성 완료
          </Button>
        </div>
      </div>
    </div>
  );
};
export default InstantActivityPage;
