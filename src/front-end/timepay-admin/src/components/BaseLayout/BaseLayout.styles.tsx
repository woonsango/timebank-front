import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssBaseLayoutStyle = css`
  font-family: unset !important;
  background: white;
  .ant-layout-header {
    background: ${COMMON_COLOR.FONT2};
    padding: 5px;
    .menu-collapse-trigger-btn {
      padding: 0;
      border: 0;
      color: ${COMMON_COLOR.WHITE};
      font-size: 20px;
      cursor: pointer;
      transition: color 0.3s;
    }
    .menu-collapse-trigger-btn:hover {
      color: ${COMMON_COLOR.MAIN2};
    }
  }
  .ant-layout-sider {
    .ant-layout-sider-children {
      text-align: center;
      background: ${COMMON_COLOR.FONT2};
      height: 100vh;
      .logo {
        height: 64px;
        width: auto;
        padding: 10px;
        &.collapsed {
          visibility: hidden;
        }
      }
    }
  }

  .ant-menu {
    background: unset;
    font-family: unset;
    font-size: 15px;
    color: ${COMMON_COLOR.WHITE};
    .ant-menu-item-active {
      color: ${COMMON_COLOR.MAIN2} !important;
    }
  }
`;
