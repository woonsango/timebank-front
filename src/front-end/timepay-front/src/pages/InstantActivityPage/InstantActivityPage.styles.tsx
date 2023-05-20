import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssInstantActivityPageStyle = css`
  padding: 20px 30px;

  .ant-steps {
    display: flex;
    flex-direction: row;
    .ant-steps-item-tail {
      display: none !important;
    }
  }
`;

export const cssInstantActivityStepItemStyle = (isCurrent: boolean) => css`
  height: 500px;
  display: ${isCurrent ? 'flex' : 'none'};
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  .helper-user-nickname {
    font-size: 16px;
    font-weight: 700;
    padding-bottom: 20px;
    padding-top: 150px;
  }
  .help-user {
    font-size: 18px;
  }
  .guide {
    font-size: 14px;
    color: ${COMMON_COLOR.MAIN2};
  }

  .control-box {
    display: flex;
    flex-direction: row;
    width: 100%;
    justify-content: flex-end;
    gap: 10px;
  }
  .ant-form {
    .form-info {
      font-size: 18px;
      font-weight: 700;
      padding-top: 30px;
      padding-bottom: 10px;
    }
  }
  ul {
    padding: 0 20px;
  }
`;
