import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssTimeTable = css`
  padding: 10px;
  .ant-typography {
    margin-top: 10px;
    margin-bottom: 3px;
  }
  .fc-header-toolbar {
    margin-bottom: 5px !important;
  }
  .fc-toolbar-title {
    font-size: 20px;
  }
  .fc-view-harness {
    border-radius: 20px 0px 0px 0px;
  }
  .fc-scrollgrid {
    border-radius: 20px 0px 0px 0px;
  }
  .fc-col-header-cell-cushion {
    color: ${COMMON_COLOR.FONT2};
  }
  .fc-event-main {
    overflow: 'hidden';
  }
`;
