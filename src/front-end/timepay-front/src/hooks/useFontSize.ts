import { useEffect, useMemo, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { fontSizeState } from '../states/uiState';

const useFontSize = () => {
  const fontSizeValue = useRecoilValue(fontSizeState);
  const [scaleValue, setScaleValue] = useState(1);

  const isSmall = useMemo(() => {
    return fontSizeValue === 'small';
  }, [fontSizeValue]);

  const isBig = useMemo(() => {
    return fontSizeValue === 'big';
  }, [fontSizeValue]);

  useEffect(() => {
    if (isSmall) setScaleValue(1);
    if (isBig) setScaleValue(1.5);
  }, [isSmall, isBig]);

  return { scaleValue, isSmall, isBig };
};

export default useFontSize;
