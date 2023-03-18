import { BellOutlined, MenuOutlined } from '@ant-design/icons';
import { Badge, Dropdown, MenuProps } from 'antd';
import { useMemo } from 'react';
import {
  cssHomeHeaderMenuStyle,
  cssHomeHeaderNotificationStyle,
  cssHomeHeaderStyle,
} from './HomeHeader.styles';

const HomeHeader = () => {
  const items: MenuProps['items'] = useMemo(() => {
    return [
      { label: <div>전체</div>, key: '전체' },
      { label: <div>도움요청</div>, key: '도움요청' },
      { label: <div>도움제공</div>, key: '도움제공' },
      { label: <div>자유</div>, key: '자유' },
      { label: <div>후기</div>, key: '후기' },
    ];
  }, []);

  return (
    <div css={cssHomeHeaderStyle}>
      <Dropdown
        css={cssHomeHeaderMenuStyle}
        menu={{ items }}
        placement="bottomLeft"
        arrow
      >
        <div>
          <MenuOutlined /> 전체
        </div>
      </Dropdown>
      <Badge dot css={cssHomeHeaderNotificationStyle}>
        <BellOutlined />
      </Badge>
    </div>
  );
};

export default HomeHeader;
