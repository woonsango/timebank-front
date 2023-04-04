import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssAnotherUserProfileDrawerStyle = css`
  border-radius: 20px 20px 0 0;
  .spin-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
  }
  .ant-drawer-header {
    display: none;
  }
  .ant-drawer-body {
    padding: 0;
  }
  .another-user-profile-container {
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  .profile-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 30px;
    padding: 30px 20px 10px 20px;
    img {
      border-radius: 50%;
    }
    .nickname-userPk-container {
      display: flex;
      flex-direction: column;
      gap: 5px;
      .nickname {
        font-style: normal;
        font-weight: 400;
        font-size: 32px;
        color: ${COMMON_COLOR.FONT3};
      }
      .userPk {
        font-style: normal;
        font-weight: 400;
        font-size: 16px;
        color: ${COMMON_COLOR.LIGHT_GRAY};
      }
    }
  }
  .activity-container {
    height: 100%;
    .ant-tabs-content-holder {
      padding-top: 5px;
      .ant-tabs-content {
        height: 100%;
        .ant-tabs-tabpane {
          height: 100%;
        }
      }
    }
    .activity-list-container {
      display: flex;
      flex-direction: column;
      height: 100%;
      align-items: center;
      justify-content: space-between;
      width: 100%;
      .activity-list {
        width: 100%;
      }
    }
  }
`;
