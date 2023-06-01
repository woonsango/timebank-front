import { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import { Calendar, EventInput } from '@fullcalendar/core/index.js';
import timeGridPlugin from '@fullcalendar/timegrid';
import { cssTimeTable } from './TimeTable.style';
import { Typography } from 'antd';
import { useGetUserBoards } from '../../api/hooks/user';
import { COMMON_COLOR } from '../../styles/constants/colors';
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

  return (
    <div className="calendarContainer" css={cssTimeTable}>
      <Title className="title">나의 일정</Title>
      <FullCalendar
        plugins={[timeGridPlugin]}
        titleFormat={{ year: 'numeric', month: 'long' }}
        initialView="timeGridWeek"
        locale="ko"
        allDaySlot={false}
        headerToolbar={{
          center: 'new',
        }}
        customButtons={{
          new: {
            text: 'new',
            click: () => console.log('new event'),
          },
        }}
        buttonText={{ today: '오늘날짜 보기' }}
        dayHeaderFormat={{
          weekday: 'short',
          day: 'numeric',
        }}
        buttonIcons={{ prev: 'chevron-left' }}
        events={dataSource}
        eventColor={COMMON_COLOR.MAIN1}
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
