import { useState } from 'react';

import 'dayjs/locale/ko';
import { Dayjs } from 'dayjs';
import dayjsGenerateConfig from 'rc-picker/lib/generate/dayjs';
import generatePicker from 'antd/es/date-picker/generatePicker';
import locale from 'antd/es/date-picker/locale/ko_KR';
import { useSetRecoilState } from 'recoil';

const DatePicker = generatePicker<Dayjs>(dayjsGenerateConfig);
const { RangePicker } = DatePicker;

export type DateRange = [Date | null, Date | null];

interface Props {
  value: DateRange;
  onChange: (value: DateRange) => void;
}

export function KoDatePicker(props: Props) {
  const { value, onChange } = props;

  const handleDateRangeChange = (dates: any, dateStrings: [string, string]) => {
    if (dateStrings[0] && dateStrings[1]) {
      onChange([new Date(dateStrings[0]), new Date(dateStrings[1])]);
    }
  };

  return (
    <div>
      <RangePicker
        locale={locale}
        size={'large'}
        style={{ marginLeft: '20px' }}
        onChange={handleDateRangeChange}
      />
    </div>
  );
}
