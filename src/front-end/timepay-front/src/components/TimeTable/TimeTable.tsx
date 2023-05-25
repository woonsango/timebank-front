import { useState } from 'react';

import FullCalendar from '@fullcalendar/react';
import { Calendar, EventInput } from '@fullcalendar/core/index.js';
import timeGridPlugin from '@fullcalendar/timegrid';
import { cssTimeTable } from './TimeTable.style';
import { Typography } from 'antd';
import { useGetUserBoards } from '../../api/hooks/user';
const { Title } = Typography;

//export function Calendar() {
const TimeTable = () => {
  const { data, isLoading } = useGetUserBoards();

  const dataSource =
    data?.data.body.deal_boards.content.map(
      (data: { startTime: any; endTime: any }) => ({
        ...data,
        start: data.startTime,
        end: data.endTime,
      }),
    ) || [];
  // console.log(data?.data.body);

  return (
    <div className="calendarContainer" css={cssTimeTable}>
      <Title className="title">나의 일정</Title>
      <FullCalendar
        plugins={[timeGridPlugin]}
        titleFormat={{ year: 'numeric', month: 'long' }}
        initialView="timeGridWeek"
        locale="ko"
        allDaySlot={false}
        buttonText={{ today: '오늘날짜 보기' }}
        dayHeaderFormat={{
          weekday: 'short',
          day: 'numeric',
        }}
        buttonIcons={{ prev: 'chevron-left' }}
        events={dataSource}
        eventTimeFormat={{
          hour: 'numeric',
          minute: '2-digit',
          meridiem: false,
        }}
      />
    </div>
  );
};

export default TimeTable;
