import { useMemo, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { fontSizeState } from '../states/uiState';

const useFontSize = () => {
  const fontSizeValue = useRecoilValue(fontSizeState);
  const [scaleValue, setScaleValue] = useState(1);

  const isSmall = useMemo(() => {
    setScaleValue(1);
    return fontSizeValue === 'small';
  }, [fontSizeValue]);

  const isBig = useMemo(() => {
    setScaleValue(1.5);
    return fontSizeValue === 'big';
  }, [fontSizeValue]);

  return { scaleValue, isSmall, isBig };
};

export default useFontSize;
